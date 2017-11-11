package net.coatli.java.microservice.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.jsoniter.output.JsonStream;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import net.coatli.java.microservice.domain.Person;
import net.coatli.java.microservice.event.RequestAllPersonsEvent;
import net.coatli.java.microservice.event.ResponseAllPersonsEvent;
import net.coatli.java.microservice.mapper.PersonMapper;

public class PersonsGetHandler implements HttpHandler {

  private static final int CORE_POOL_SIZE          = 200;
  private static final int MAXIMUM_POOL_SIZE       = 400;
  private static final int KEEP_ALIVE_TIME         = 200;
  private static final int BLOCKING_QUEUE_CAPACITY = 400;

  private static ExecutorService EXECUTOR = new ThreadPoolExecutor(
                                              CORE_POOL_SIZE,
                                              MAXIMUM_POOL_SIZE,
                                              KEEP_ALIVE_TIME,
                                              TimeUnit.MILLISECONDS,
                                              new LinkedBlockingQueue<Runnable>(BLOCKING_QUEUE_CAPACITY),
                                              new ThreadPoolExecutor.CallerRunsPolicy());

  private static final String DEFAULT_ENVIRONMENT = "production";
  private static final String HIKARI_PROPERTIES   = "/conf/hikari.properties";

  private static final SqlSessionFactory SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(configuration());

  public PersonsGetHandler() { }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleRequest(final HttpServerExchange exchange) throws Exception {

    if (exchange.isInIoThread()) {
      exchange.dispatch(this);
      return ;
    }

    exchange.dispatch(EXECUTOR, () -> {
      final SqlSession sqlSession = SQL_SESSION_FACTORY.openSession(true);

      try {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(
            JsonStream.serialize(
                new ResponseAllPersonsEvent().setPersons(
                    sqlSession.getMapper(PersonMapper.class).findAll())));
      } finally {
        sqlSession.close();
      }
    });
  }

  /**
   *
   * @return
   */
  private static Configuration configuration() {

    final Configuration configuration = new Configuration(
        new Environment(DEFAULT_ENVIRONMENT,
                        new JdbcTransactionFactory(),
                        new HikariDataSource(new HikariConfig(HIKARI_PROPERTIES))));

    // Aliases
    configuration.getTypeAliasRegistry().registerAlias(Person.class.getSimpleName(), Person.class);
    configuration.getTypeAliasRegistry().registerAlias(RequestAllPersonsEvent.class.getSimpleName(),
                                                       RequestAllPersonsEvent.class);
    // Mappers
    configuration.addMapper(PersonMapper.class);

    return configuration;
  }

}

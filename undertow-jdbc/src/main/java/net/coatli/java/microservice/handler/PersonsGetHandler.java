package net.coatli.java.microservice.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import net.coatli.java.microservice.domain.Person;
import net.coatli.java.microservice.event.ResponseAllPersonsEvent;

public class PersonsGetHandler implements HttpHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonsGetHandler.class);

  private static final DataSource DATA_SOURCE = new HikariDataSource(new HikariConfig("/datasource.properties"));

  private static final int CORE_POOL_SIZE          = 600;
  private static final int MAXIMUM_POOL_SIZE       = 1200;
  private static final int KEEP_ALIVE_TIME         = 200;
  private static final int BLOCKING_QUEUE_CAPACITY = 600;

  private static ExecutorService EXECUTOR = new ThreadPoolExecutor(
                                              CORE_POOL_SIZE,
                                              MAXIMUM_POOL_SIZE,
                                              KEEP_ALIVE_TIME,
                                              TimeUnit.MILLISECONDS,
                                              new LinkedBlockingQueue<Runnable>(BLOCKING_QUEUE_CAPACITY),
                                              new ThreadPoolExecutor.CallerRunsPolicy());

  @Override
  public void handleRequest(final HttpServerExchange exchange) throws Exception {

    if (exchange.isInIoThread()) {
      exchange.dispatch(this);
      return ;
    }

    exchange.dispatch(EXECUTOR, () -> {

      final ResponseAllPersonsEvent event = new ResponseAllPersonsEvent().setPersons(new ArrayList<>());

      try (Connection connection = DATA_SOURCE.getConnection()) {
        try (PreparedStatement statement = connection.prepareStatement(
                                     "SELECT * FROM person", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)) {
          try (final ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
              event.getPersons().add(
                  new Person()
                    .setKey(resultSet.getString("id"))
                    .setName(resultSet.getString("name"))
                    .setAge(resultSet.getInt("age")));
            }

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug("Data to send: {}", event);
            }

            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send(event.toString());

          }
        }
      } catch (final SQLException exc) {
        LOGGER.error(exc.getMessage(), exc);
        throw new RuntimeException(exc);
      }

    });
  }

}

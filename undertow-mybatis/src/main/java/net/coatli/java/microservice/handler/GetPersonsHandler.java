package net.coatli.java.microservice.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;

import com.jsoniter.output.JsonStream;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import net.coatli.java.microservice.config.MyBatis;
import net.coatli.java.microservice.event.FindByPersonsEvent;
import net.coatli.java.microservice.persistence.PersonMapper;

/**
 *
 */
public class GetPersonsHandler implements HttpHandler {

  private static final int CORE_POOL_SIZE          = 32;
  private static final int MAXIMUM_POOL_SIZE       = 64;
  private static final int KEEP_ALIVE_TIME         = 200;
  private static final int BLOCKING_QUEUE_CAPACITY = 64;

  private static ExecutorService EXECUTOR = new ThreadPoolExecutor(
                                              CORE_POOL_SIZE,
                                              MAXIMUM_POOL_SIZE,
                                              KEEP_ALIVE_TIME,
                                              TimeUnit.MILLISECONDS,
                                              new LinkedBlockingQueue<Runnable>(BLOCKING_QUEUE_CAPACITY),
                                              new ThreadPoolExecutor.CallerRunsPolicy());

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleRequest(final HttpServerExchange exchange) throws Exception {

    exchange.dispatch(EXECUTOR, () -> {

      String pageNumber = exchange.getRequestHeaders().getLast("X-Page-Number");
      if (pageNumber == null || pageNumber.trim().isEmpty() || !pageNumber.matches("^\\d+$")) {
        pageNumber = "1";
      }

      String pageSize = exchange.getRequestHeaders().getLast("X-Page-Size");
      if (pageSize == null || pageSize.trim().isEmpty() || !pageSize.matches("^\\d+$")) {
        pageSize = "8";
      }

      try (final SqlSession sqlSession = MyBatis.sqlSessionFactory().openSession(true)) {

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json;charset=utf-8");
        exchange.getResponseSender().send(
            JsonStream.serialize(
                sqlSession.getMapper(PersonMapper.class).findBy(
                    new FindByPersonsEvent())));

      }
    });
  }

}

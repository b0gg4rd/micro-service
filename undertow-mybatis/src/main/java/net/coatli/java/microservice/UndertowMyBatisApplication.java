package net.coatli.java.microservice;

import static io.undertow.util.Methods.GET;
import static io.undertow.util.Methods.POST;

import java.io.InputStream;
import java.util.Properties;

import org.xnio.Options;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import net.coatli.java.microservice.config.MyBatis;
import net.coatli.java.microservice.handler.GetPersonsHandler;
import net.coatli.java.microservice.handler.GetPersonsKeyHandler;
import net.coatli.java.microservice.handler.PostPersonsHandler;

/**
 * Main class for the self-package application
 */
public class UndertowMyBatisApplication {

  public static final Properties APPLICATION = new Properties();

  private static final String APPLICATION_PROPERTIES = "/conf/application.properties";

  private static final String HOST           = "host";
  private static final String PORT           = "port";
  private static final int    IO_THREADS     = Runtime.getRuntime().availableProcessors() * 4;
  private static final int    BUFFER_SIZE    = 1024 * 64;
  private static final int    BACKLOG        = 10000;
  private static final int    WORKER_THREADS = IO_THREADS * 2;

  public static void main(final String[] args) throws Exception {

    try (InputStream inputStream = UndertowMyBatisApplication.class.getResourceAsStream(APPLICATION_PROPERTIES)) {

      APPLICATION.load(inputStream);

      MyBatis.sqlSessionFactory();

      Undertow.builder()
          .addHttpListener(Integer.parseInt((String )APPLICATION.get(PORT)),
                           (String )APPLICATION.get(HOST))
          .setBufferSize(BUFFER_SIZE)
          .setIoThreads(IO_THREADS)
          .setWorkerThreads(WORKER_THREADS)
          .setSocketOption(Options.BACKLOG, BACKLOG)
          .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false)
          .setServerOption(UndertowOptions.ALWAYS_SET_DATE, true)
          .setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, false)
          .setHandler(routes())
        .build()
        .start();
    }

  }

  /**
   * Definition of the routes to dispatch.
   *
   * @return An instance of {@link HttpHandler} from {@link Handlers} {@code routing} with the routing.
   */
  private static HttpHandler routes() {
    return Handlers.routing()
        .add(POST, "/api/v1/persons/",     new PostPersonsHandler())
        .add(GET, "/api/v1/persons/{key}", new GetPersonsKeyHandler())
        .add(GET, "/api/v1/persons/",      new GetPersonsHandler());
  }

}

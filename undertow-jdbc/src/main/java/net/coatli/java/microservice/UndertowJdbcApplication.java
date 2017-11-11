package net.coatli.java.microservice;

import java.io.InputStream;
import java.util.Properties;

import org.xnio.Options;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import net.coatli.java.microservice.handler.PersonsGetHandler;

public class UndertowJdbcApplication {

  private static final String APPLICATION_PROPERTIES = "/conf/application.properties";

  private static final String HOST  = "host";
  private static final String PORT  = "port";
  private static final int    IO_THREADS     = Runtime.getRuntime().availableProcessors() * 4;
  private static final int    BUFFER_SIZE    = 1024 * 64;
  private static final int    BACKLOG        = 10000;
  private static final int    WORKER_THREADS = 200;

  public static void main(final String[] args)
      throws Exception {

    try (InputStream inputStream = UndertowJdbcApplication.class.getResourceAsStream(APPLICATION_PROPERTIES)) {

      final Properties applicationProperties = new Properties();
      applicationProperties.load(inputStream);

      Undertow.builder()
          .addHttpListener(
              Integer.parseInt((String )applicationProperties.get(PORT)),
              (String )applicationProperties.get(HOST))
          .setBufferSize(BUFFER_SIZE)
          .setIoThreads(IO_THREADS)
          .setWorkerThreads(WORKER_THREADS)
          .setSocketOption(Options.BACKLOG, BACKLOG)
          .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false)
          .setServerOption(UndertowOptions.ALWAYS_SET_DATE, true)
          .setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, false)
          .setHandler(Handlers.header(api(), Headers.SERVER_STRING, "L"))
        .build()
        .start();
    }

  }

  /**
   * Definition of the operations for the API.
   *
   * @return An instance of {@link HttpHandler} with the operations for the API.
   */
  private static HttpHandler api() {
    return Handlers.routing()
      .add(Methods.GET, "/api/v1/persons/", new PersonsGetHandler());
  }

}

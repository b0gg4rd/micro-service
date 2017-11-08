package net.coatli.java.microservice;

import java.io.IOException;
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

  private static final String UNDERTOW_HOST  = "undertow.host";
  private static final String UNDERTOW_PORT  = "undertow.port";
  private static final int    IO_THREADS     = Runtime.getRuntime().availableProcessors() * 4;
  private static final int    BUFFER_SIZE    = 1024 * 64;
  private static final int    BACKLOG        = 10000;
  private static final int    WORKER_THREADS = 200;

  public static void main(final String[] args) throws IOException {
    final Properties applicationProperties = new Properties();

    try (InputStream inputStream = UndertowJdbcApplication.class.getResourceAsStream(APPLICATION_PROPERTIES)) {
      if (inputStream == null) {
        throw new RuntimeException("Can not find application.properties");
      }
      applicationProperties.load(inputStream);
    }

    Undertow.builder()
        .addHttpListener(
            Integer.parseInt((String )applicationProperties.get(UNDERTOW_PORT)),
            (String )applicationProperties.get(UNDERTOW_HOST))
        .setBufferSize(BUFFER_SIZE)
        .setIoThreads(IO_THREADS)
        .setSocketOption(Options.BACKLOG, BACKLOG)
        .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, false)
        .setServerOption(UndertowOptions.ALWAYS_SET_DATE, true)
        .setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, false)
        .setHandler(Handlers.header(api(), Headers.SERVER_STRING, "L"))
        .setWorkerThreads(WORKER_THREADS)
      .build()
      .start();

  }

  private static HttpHandler api() {
    return Handlers.routing()
      .add(Methods.GET, "/api/v1/persons/", new PersonsGetHandler());
  }

}

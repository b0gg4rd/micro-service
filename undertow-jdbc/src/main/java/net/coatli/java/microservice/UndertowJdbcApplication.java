package net.coatli.java.microservice;

import org.xnio.Options;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.util.Headers;
import io.undertow.util.Methods;
import net.coatli.java.microservice.handler.PersonsGetHandler;

public class UndertowJdbcApplication {

  private static final String HOST = "0.0.0.0";
  private static final int    PORT = 8080;

  private static final int BACKLOG = 10000;

  private static final int WORKER_THREADS = 200;

  public static void main(final String[] args) {

    Undertow.builder()
        .addHttpListener(PORT, HOST)
        .setBufferSize(1024 * 16)
        .setIoThreads(Runtime.getRuntime().availableProcessors() * 2)
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

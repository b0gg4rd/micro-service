
package net.coatli.java.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.config.Config;
import com.networknt.service.SingletonServiceFactory;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import net.coatli.java.Executor;
import net.coatli.java.model.Person;
import net.coatli.java.model.ResponseAllPersonsEvent;

public class PersonsGetHandler implements HttpHandler {

  private static final DataSource DATA_SOURCE = SingletonServiceFactory.getBean(DataSource.class);

  private static final ObjectMapper JACKSON_MAPPER = Config.getInstance().getMapper();

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonsGetHandler.class);

  @Override
  public void handleRequest(final HttpServerExchange exchange) throws Exception {

    LOGGER.info("Handling the URL: {}", exchange.getRequestURL());

    if (exchange.isInIoThread()) {
      exchange.dispatch(this);
      return ;
    }

    exchange.dispatch(Executor.EXECUTOR, () -> {

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

            LOGGER.info("The thread {} dispatch: {} ", Thread.currentThread().getName(), event);

            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
            exchange.getResponseSender().send(JACKSON_MAPPER.writeValueAsString(event));

          } catch (final JsonProcessingException exc) {
            LOGGER.error(exc.getMessage(), exc);
            throw new RuntimeException(exc);
          }
        }
      } catch (final SQLException exc) {
        LOGGER.error(exc.getMessage(), exc);
        throw new RuntimeException(exc);
      }

    });

  }
}

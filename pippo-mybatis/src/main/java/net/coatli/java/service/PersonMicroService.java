package net.coatli.java.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.mapper.PersonMapper;
import ro.pippo.core.Application;
import ro.pippo.core.HttpConstants;
import ro.pippo.core.PippoRuntimeException;
import ro.pippo.fastjson.FastjsonEngine;

public class PersonMicroService extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  private PersonMapper personMapper;

  public PersonMicroService setPersonMapper(final PersonMapper personMapper) {
    this.personMapper = personMapper;

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onInit() {

    registerContentTypeEngine(FastjsonEngine.class);

    create();

    retrieve();

    update();

    delete();

    findAll();

    findBy();

  }

  /**
   *
   */
  private void create() {
    POST("/persons/", (routeContext) -> {
      Person person = null;

      try {
       if ((person = routeContext.createEntityFromBody(Person.class)) == null) {
         throw new PippoRuntimeException("Empty entity");
       }
      } catch (final PippoRuntimeException exc) {
        LOGGER.error("", exc);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      personMapper.create(person.setKey(UUID.randomUUID().toString()));
      routeContext.status(HttpConstants.StatusCode.CREATED).json().send(person);
    });
  }

  /**
   *
   */
  private void retrieve() {
    GET("/persons/{key}", (routeContext) -> {

      final String key = routeContext.getParameter("key").toString();

      if (StringUtils.isBlank(key)) {
        LOGGER.error("The key must not be empty '{}'", key);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final Person person = personMapper.retrieve(key);

      if (person == null) {
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        routeContext.status(HttpConstants.StatusCode.OK).json().send(person);
      }
    });
  }

  /**
   *
   */
  private void update() {
    PUT("/persons/{key}", (routeContext) -> {

      Person person = null;

      try {
        person = routeContext
            .createEntityFromBody(Person.class)
            .setKey(routeContext.getParameter("key").toString());

        if (StringUtils.isBlank(person.getKey())) {
          LOGGER.error("Incorrect key '{}'", person.getKey());
          routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
          return ;
        }
      } catch (final PippoRuntimeException exc) {
        LOGGER.error("", exc);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      personMapper.update(person);
      routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
    });
  }

  /**
   *
   */
  private void delete() {
    DELETE("/persons/{key}", (routeContext) -> {
      final String key = routeContext.getParameter("key").toString();

      if (StringUtils.isBlank(key)) {
        LOGGER.error("Incorrect key '{}'", key);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      personMapper.delete(key);
      routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
    });
  }

  /**
   *
   */
  private void findAll() {
    GET("/persons/", (routeContext) -> {

      final List<Person> persons = personMapper.findAll();

      if (persons.isEmpty()) {
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        routeContext.status(HttpConstants.StatusCode.OK).json().send(persons);
      }
    });
  }

  /**
   *
   */
  private void findBy() {
    GET("/persons", (routeContext) -> {
      RequestAllPersonsEvent filters = null;

      try {
        filters = routeContext.createEntityFromParameters(RequestAllPersonsEvent.class);
      } catch(final PippoRuntimeException exc) {
        LOGGER.error("Incorrect filters", exc);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final List<Person> persons = personMapper.findBy(filters);

      if (persons.isEmpty()) {
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        routeContext.status(HttpConstants.StatusCode.OK).json().send(persons);
      }
    });
  }

}

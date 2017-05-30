package net.coatli.java.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import net.coatli.java.domain.Person;
import net.coatli.java.mapper.PersonMapper;
import ro.pippo.core.Application;
import ro.pippo.core.HttpConstants;
import ro.pippo.core.PippoRuntimeException;
import ro.pippo.fastjson.FastjsonEngine;

public class PersonMicroService extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  private DBCollection dbCollection;

  public PersonMicroService setDbCollection(final DBCollection dbCollection) {
    this.dbCollection = dbCollection;

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onInit() {

    registerContentTypeEngine(FastjsonEngine.class);

    create();
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

      final DBObject document = PersonMapper.mapToDocument(person);
      dbCollection.insert(document);

      if (document.get("_id") != null) {
        routeContext.status(HttpConstants.StatusCode.CREATED).json().send(person);
      } else {
        throw new RuntimeException("Inserting document {} do not generate id");
      }
    });
  }

}

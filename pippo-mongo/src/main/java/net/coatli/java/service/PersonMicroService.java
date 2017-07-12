package net.coatli.java.service;

import static com.mongodb.client.model.Filters.eq;
import static net.coatli.java.mapper.PersonMapper.mapFromDocument;
import static net.coatli.java.mapper.PersonMapper.mapToDocument;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;

import net.coatli.java.domain.Person;
import ro.pippo.core.Application;
import ro.pippo.core.HttpConstants;
import ro.pippo.core.PippoRuntimeException;
import ro.pippo.fastjson.FastjsonEngine;

public class PersonMicroService extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  private MongoCollection<DBObject> collection;

  public PersonMicroService setCollection(final MongoCollection<DBObject> collection) {
    this.collection = collection;

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

      final DBObject document = mapToDocument(person);
      collection.insertOne(document);

      if (document.get("_id") != null) {
        routeContext.status(HttpConstants.StatusCode.CREATED).json().send(person);
      } else {
        throw new RuntimeException("Inserting document {} do not generate id");
      }
    });
  }

  private void retrieve() {
    GET("/persons/{key}", (routeContext) -> {

      final String key = routeContext.getParameter("key").toString();

      if (StringUtils.isBlank(key)) {
        LOGGER.error("The key must not be empty '{}'", key);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final Person person = mapFromDocument((BasicDBObject )collection.find(eq("_id", key)).first());

      if (person == null) {
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        routeContext.status(HttpConstants.StatusCode.OK).json().send(person);
      }
    });
  }

  private void update() {
    PUT("/persons/{key}", (routeContext) -> {


    });
  }

  private void delete() {
    DELETE("/persons/{key}", (routeContext) -> {

    });
  }

}

package net.coatli.java.config;

import java.net.UnknownHostException;

import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import net.coatli.java.service.PersonMicroService;
import ro.pippo.core.Application;
import ro.pippo.core.Initializer;
import ro.pippo.core.PippoSettings;

@MetaInfServices
public class MongoInitializer implements Initializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoInitializer.class);

  private static final String MONGO_HOSTNAME   = "mongo.hostname";
  private static final String MONGO_PORT       = "mongo.port";
  private static final String MONGO_DB         = "mongo.db";
  private static final String MONGO_COLLECTION = "mongo.collection";
  private static final String MONGO_USERNAME   = "mongo.username";
  private static final String MONGO_PASSWORD   = "mongo.password";

  @Override
  public void init(final Application application) {
    try {
      ((PersonMicroService )application).setDbCollection(dbCollection(application.getPippoSettings()));
    } catch (final UnknownHostException exc) {
      LOGGER.error("{}",exc);
    }
  }

  @Override
  public void destroy(final Application application) {
  }

  private DBCollection dbCollection(final PippoSettings pippoSettings) throws UnknownHostException {

    return db(pippoSettings).getCollection(pippoSettings.getString(MONGO_COLLECTION, null));

  }

  private DB db(final PippoSettings pippoSettings) throws UnknownHostException {
    final DB db = mongoClient(pippoSettings).getDB(pippoSettings.getString(MONGO_DB, null));

    /*
    db.authenticate(pippoSettings.getString(MONGO_USERNAME, null),
        pippoSettings.getString(MONGO_PASSWORD, null).toCharArray());
    */

    return db;
  }

  private MongoClient mongoClient(final PippoSettings pippoSettings) throws UnknownHostException {
    return new MongoClient(pippoSettings.getString(MONGO_HOSTNAME, null), pippoSettings.getInteger(MONGO_PORT, 0));
  }

}

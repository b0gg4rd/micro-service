package net.coatli.java.config;

import java.net.UnknownHostException;

import org.kohsuke.MetaInfServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import net.coatli.java.service.PersonMicroService;
import ro.pippo.core.Application;
import ro.pippo.core.Initializer;
import ro.pippo.core.PippoSettings;

@MetaInfServices
public class MongoInitializer implements Initializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(MongoInitializer.class);

  private static final String MONGO_URL        = "mongo.url";
  private static final String MONGO_DATABASE   = "mongo.database";
  private static final String MONGO_COLLECTION = "mongo.collection";
  private static final String MONGO_USERNAME   = "mongo.username";
  private static final String MONGO_PASSWORD   = "mongo.password";

  @Override
  public void init(final Application application) {
    try {
      ((PersonMicroService )application).setCollection(collection(application.getPippoSettings()));
    } catch (final UnknownHostException exc) {
      LOGGER.error("{}",exc);
    }
  }

  @Override
  public void destroy(final Application application) {
  }

  private MongoCollection<DBObject> collection(final PippoSettings pippoSettings) throws UnknownHostException {

    return database(pippoSettings).getCollection(pippoSettings.getString(MONGO_COLLECTION, null), DBObject.class);

  }

  private MongoDatabase database(final PippoSettings pippoSettings) throws UnknownHostException {

    return mongoClient(pippoSettings).getDatabase(pippoSettings.getString(MONGO_DATABASE, null));

  }

  private MongoClient mongoClient(final PippoSettings pippoSettings) throws UnknownHostException {

    return new MongoClient(new MongoClientURI(pippoSettings.getString(MONGO_URL, null)));

  }

}

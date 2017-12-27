package net.coatli.java.microservice.config;

import static net.coatli.java.microservice.UndertowMongoApplication.APPLICATION;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;

/**
 *
 */
public final class MongoDb {

  /**
  *
  */
  private static volatile MongoCollection<Document> COLLECTION = null;

  /**
  * Private explicit default constructor for {@literal Singleton} pattern.
  */
  private MongoDb() {
  }

  /**
   *
   * @return
   */
  public static MongoCollection<Document> collection() {

    if (COLLECTION == null) {
      COLLECTION = new MongoClient(new MongoClientURI(APPLICATION.getProperty("mongo.url")))
          .getDatabase(APPLICATION.getProperty("mongo.database"))
          .getCollection(APPLICATION.getProperty("mongo.collection"));
    }

    return COLLECTION;
  }

}

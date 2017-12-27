package net.coatli.java.microservice.persistence;

import java.time.ZoneId;

import org.bson.Document;

import net.coatli.java.microservice.domain.Person;

/**
 *
 */
public final class PersonMapper {

  /**
   *
   * @param document
   * @return
   */
  public static final Person fromDocument(final Document document) {

    return new Person()
        .setKey(document.getObjectId("_id").toString())
        .setName(document.getString("name"))
        .setAge(document.getInteger("age"))
        .setBirthDay(document.getDate("birthday") != null
            ? document.getDate("birthday").toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null);
  }

}

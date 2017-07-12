package net.coatli.java.mapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import net.coatli.java.domain.Person;

public final class PersonMapper {

  public static final DBObject mapToDocument(final Person person) {
    return new BasicDBObject()
        .append("name", person.getName())
        .append("birthDay", person.getBirthDay())
        .append("age", person.getAge());
  }

  public static final Person mapFromDocument(final BasicDBObject document) {

    return new Person()
        .setKey(document.getString("_id"))
        .setName(document.getString("name"))
        .setBirthDay(document.getDate("birthDay"))
        .setAge(document.getInt("age"));
  }
}

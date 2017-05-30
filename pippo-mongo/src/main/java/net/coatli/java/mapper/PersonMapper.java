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
}

package net.coatli.java.microservice.persistence;

import java.util.List;

import net.coatli.java.microservice.domain.Person;
import net.coatli.java.microservice.event.FindByPersonsEvent;

/**
 *
 */
public interface PersonMapper {

  public int create(Person person);

  public Person retrieve(String key);

  public List<Person> findBy(FindByPersonsEvent findByPersonsEvent);

}

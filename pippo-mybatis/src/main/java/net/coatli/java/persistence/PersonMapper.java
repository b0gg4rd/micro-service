package net.coatli.java.persistence;

import java.util.List;

import net.coatli.java.domain.Person;
import net.coatli.java.events.RequestAllPersonsEvent;

public interface PersonMapper {

  public int create(Person person);

  public Person retrieve(Long key);

  public int update(Person person);

  public int delete(Long key);

  public List<Person> findAll();

  public List<Person> findBy(RequestAllPersonsEvent requestAllPersonsEvent);

}

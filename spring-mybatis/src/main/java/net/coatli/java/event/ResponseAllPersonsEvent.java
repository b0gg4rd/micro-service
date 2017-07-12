package net.coatli.java.event;

import java.util.List;

import net.coatli.java.domain.Person;

public class ResponseAllPersonsEvent {

  private List<Person> persons;

  public ResponseAllPersonsEvent() {
  }

  public List<Person> getPersons() {
    return persons;
  }

  public ResponseAllPersonsEvent setPersons(final List<Person> persons) {
    this.persons = persons;

    return this;
  }

}

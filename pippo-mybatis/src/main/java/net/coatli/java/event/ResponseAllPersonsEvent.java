package net.coatli.java.event;

import java.util.List;

import net.coatli.java.domain.Person;

public class ResponseAllPersonsEvent {

  private boolean      empty;
  private List<Person> persons;

  public ResponseAllPersonsEvent() {
  }

  public boolean isEmpty() {
    return empty;
  }

  public ResponseAllPersonsEvent setEmpty(final boolean empty) {
    this.empty = empty;

    return this;
  }

  public List<Person> getPersons() {
    return persons;
  }

  public ResponseAllPersonsEvent setPersons(final List<Person> persons) {
    this.persons = persons;

    return this;
  }

}

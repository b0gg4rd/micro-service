package net.coatli.java.event;

import net.coatli.java.domain.Person;

public class PersonCreatedEvent {

  private Person person;

  public PersonCreatedEvent() {
  }

  public Person getPerson() {
    return person;
  }

  public PersonCreatedEvent setPerson(final Person person) {
    this.person = person;

    return this;
  }


}

package net.coatli.java.event;

import net.coatli.java.domain.Person;

public class CreatePersonEvent {

  private Person person;

  public CreatePersonEvent() {
  }

  public Person getPerson() {
    return person;
  }

  public CreatePersonEvent setPerson(final Person person) {
    this.person = person;

    return this;
  }

}

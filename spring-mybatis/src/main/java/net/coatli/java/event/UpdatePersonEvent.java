package net.coatli.java.event;

import net.coatli.java.domain.Person;

public class UpdatePersonEvent {

  private Person person;

  public UpdatePersonEvent() {
  }

  public Person getPerson() {
    return person;
  }

  public UpdatePersonEvent setPerson(final Person person) {
    this.person = person;

    return this;
  }

}

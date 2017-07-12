package net.coatli.java.event;

import net.coatli.java.domain.Person;

public class ResponsePersonEvent {

  private Person person;

  public ResponsePersonEvent() {
  }

  public Person getPerson() {
    return person;
  }

  public ResponsePersonEvent setPerson(final Person person) {
    this.person = person;

    return this;
  }

}

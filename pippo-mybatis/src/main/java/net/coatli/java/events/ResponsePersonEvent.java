package net.coatli.java.events;

import net.coatli.java.domain.Person;

public class ResponsePersonEvent {

  private boolean domainFound;
  private Person  person;

  public ResponsePersonEvent() {
  }

  public boolean isDomainFound() {
    return domainFound;
  }

  public ResponsePersonEvent setDomainFound(final boolean domainFound) {
    this.domainFound = domainFound;

    return this;
  }

  public Person getPerson() {
    return person;
  }

  public ResponsePersonEvent setPerson(final Person person) {
    this.person = person;

    return this;
  }

}

package net.coatli.java.events;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import net.coatli.java.domain.Person;

public class ResponseAllPersonsEvent {

  private boolean      domainFound;
  private List<Person> persons;

  public ResponseAllPersonsEvent() {
  }

  public boolean isDomainFound() {
    return domainFound;
  }

  public ResponseAllPersonsEvent setDomainFound(final boolean domainFound) {
    this.domainFound = domainFound;

    return this;
  }

  public List<Person> getPersons() {
    return persons;
  }

  public ResponseAllPersonsEvent setPersons(final List<Person> persons) {
    this.persons = persons;

    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString())
        .append("domainFound", domainFound).append("persons", persons).toString();
  }

  @Override
  public boolean equals(final Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof ResponseAllPersonsEvent)) {
      return false;
    }
    final ResponseAllPersonsEvent castOther = (ResponseAllPersonsEvent) other;
    return new EqualsBuilder().append(domainFound, castOther.domainFound).append(persons, castOther.persons).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(domainFound).append(persons).toHashCode();
  }

}

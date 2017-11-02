
package net.coatli.java.model;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseAllPersonsEvent {

  private List<Person> persons;

  public ResponseAllPersonsEvent() {
  }

  @JsonProperty("persons")
  public List<Person> getPersons() {
    return persons;
  }

  public ResponseAllPersonsEvent setPersons(final List<Person> persons) {
    this.persons = persons;

    return this;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final ResponseAllPersonsEvent ResponseAllPersonsEvent = (ResponseAllPersonsEvent) o;

    return Objects.equals(persons, ResponseAllPersonsEvent.persons);
  }

  @Override
  public int hashCode() {
    return Objects.hash(persons);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("persons", persons)
        .toString();
  }

}

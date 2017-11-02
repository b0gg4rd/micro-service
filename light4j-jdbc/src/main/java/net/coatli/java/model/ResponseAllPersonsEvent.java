
package net.coatli.java.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseAllPersonsEvent {

  private java.util.List<Person> persons;

  public ResponseAllPersonsEvent() {
  }

  @JsonProperty("persons")
  public java.util.List<Person> getPersons() {
    return persons;
  }

  public ResponseAllPersonsEvent setPersons(final java.util.List<Person> persons) {
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
    final StringBuilder sb = new StringBuilder();
    sb.append("class ResponseAllPersonsEvent {\n");

    sb.append("    persons: ").append(toIndentedString(persons)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(final Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

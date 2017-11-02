
package net.coatli.java.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

  private String              name;

  private java.time.LocalDate birthday;

  private String              key;

  private Integer             age;

  public Person() {
  }

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public Person setName(final String name) {
    this.name = name;

    return this;
  }

  @JsonProperty("birthday")
  public java.time.LocalDate getBirthday() {
    return birthday;
  }

  public Person setBirthday(final java.time.LocalDate birthday) {
    this.birthday = birthday;

    return this;
  }

  @JsonProperty("key")
  public String getKey() {
    return key;
  }

  public Person setKey(final String key) {
    this.key = key;

    return this;
  }

  @JsonProperty("age")
  public Integer getAge() {
    return age;
  }

  public Person setAge(final Integer age) {
    this.age = age;

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
    final Person Person = (Person) o;

    return Objects.equals(name, Person.name) && Objects.equals(birthday, Person.birthday)
        && Objects.equals(key, Person.key) &&

        Objects.equals(age, Person.age);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, birthday, key, age);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");

    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    birthday: ").append(toIndentedString(birthday)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    age: ").append(toIndentedString(age)).append("\n");
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

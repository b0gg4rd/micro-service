
package net.coatli.java.model;

import java.time.LocalDate;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

  private String    name;
  private LocalDate birthday;
  private String    key;
  private Integer   age;

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
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("name", name)
        .append("birthday", birthday).append("key", key).append("age", age).toString();
  }

}

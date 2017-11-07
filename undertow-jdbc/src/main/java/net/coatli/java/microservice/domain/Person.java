package net.coatli.java.microservice.domain;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person {

  private String    name;
  private LocalDate birthday;
  private String    key;
  private Integer   age;

  public Person() {
  }

  public String getName() {
    return name;
  }

  public Person setName(final String name) {
    this.name = name;

    return this;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public Person setBirthday(final LocalDate birthday) {
    this.birthday = birthday;

    return this;
  }

  public String getKey() {
    return key;
  }

  public Person setKey(final String key) {
    this.key = key;

    return this;
  }

  public Integer getAge() {
    return age;
  }

  public Person setAge(final Integer age) {
    this.age = age;

    return this;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("name", name)
        .append("birthday", birthday).append("key", key).append("age", age).toString();
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof Person)) {
      return false;
    }
    final Person castOther = (Person) other;
    return new EqualsBuilder().append(name, castOther.name).append(birthday, castOther.birthday)
        .append(key, castOther.key).append(age, castOther.age).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(name).append(birthday).append(key).append(age).toHashCode();
  }

}

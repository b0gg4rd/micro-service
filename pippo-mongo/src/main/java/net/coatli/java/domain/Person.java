package net.coatli.java.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Person {

  private String  key;
  private String  name;
  private Date    birthDay;
  private Integer age;

  public Person() {
  }

  public String getKey() {
    return key;
  }

  public Person setKey(final String key) {
    this.key = key;

    return this;
  }

  public String getName() {
    return name;
  }

  public Person setName(final String name) {
    this.name = name;

    return this;
  }

  public Date getBirthDay() {
    return birthDay;
  }

  public Person setBirthDay(final Date birthDay) {
    this.birthDay = birthDay;

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
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE).appendSuper(super.toString()).append("key", key)
        .append("name", name).append("birthDay", birthDay).append("age", age).toString();
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof Person)) {
      return false;
    }
    final Person castOther = (Person) other;
    return new EqualsBuilder().append(key, castOther.key).append(name, castOther.name)
        .append(birthDay, castOther.birthDay).append(age, castOther.age).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(key).append(name).append(birthDay).append(age).toHashCode();
  }

}

package net.coatli.java.microservice.domain;

import java.time.LocalDate;

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

}

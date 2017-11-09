package net.coatli.java.microservice.event;

public class RequestAllPersonsEvent {

  private String  name;
  private Integer age;

  public RequestAllPersonsEvent() {
  }

  public String getName() {
    return name;
  }

  public RequestAllPersonsEvent setName(final String name) {
    this.name = name;

    return this;
  }

  public Integer getAge() {
    return age;
  }

  public RequestAllPersonsEvent setAge(final Integer age) {
    this.age = age;

    return this;
  }

}

package net.coatli.java.microservice.event;

import com.jsoniter.output.JsonStream;

public class FindByPersonsEvent {

  private Integer pageNumber;
  private Integer pageSize;
  private String  name;
  private Integer age;

  /**
   * Explicit default constructor
   */
  public FindByPersonsEvent() {
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public FindByPersonsEvent setPageNumber(final Integer pageNumber) {
    this.pageNumber = pageNumber;

    return this;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public FindByPersonsEvent setPageSize(final Integer pageSize) {
    this.pageSize = pageSize;

    return this;
  }

  public String getName() {
    return name;
  }

  public FindByPersonsEvent setName(final String name) {
    this.name = name;

    return this;
  }

  public Integer getAge() {
    return age;
  }

  public FindByPersonsEvent setAge(final Integer age) {
    this.age = age;

    return this;
  }

  @Override
  public String toString() {
    return JsonStream.serialize(this);
  }

}

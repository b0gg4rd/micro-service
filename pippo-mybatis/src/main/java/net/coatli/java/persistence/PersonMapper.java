package net.coatli.java.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import net.coatli.java.domain.Person;
import net.coatli.java.events.ResponseAllPersonsEvent;

public interface PersonMapper {

  @Select("SELECT * FROM person")
  public List<Person> findAll(ResponseAllPersonsEvent requestAllPersonsEvent);

}

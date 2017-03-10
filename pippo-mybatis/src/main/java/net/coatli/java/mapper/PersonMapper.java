package net.coatli.java.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;

@Mapper
public interface PersonMapper {

  public int create(Person person);

  public Person retrieve(Long key);

  public int update(Person person);

  public int delete(Long key);

  public List<Person> findAll();

  public List<Person> findBy(RequestAllPersonsEvent requestAllPersonsEvent);

}

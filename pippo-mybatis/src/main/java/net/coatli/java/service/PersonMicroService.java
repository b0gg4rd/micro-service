package net.coatli.java.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import net.coatli.java.config.MyBatisConfig;
import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.event.ResponseAllPersonsEvent;
import net.coatli.java.mapper.PersonMapper;
import ro.pippo.core.Application;

public class PersonMicroService extends Application {

  private final SqlSessionFactory sqlSessionFactory;

  public PersonMicroService() {
    sqlSessionFactory = MyBatisConfig.getInstance().sqlSessionFactory();
  }

  @Override
  public void onInit() {

    // create
    POST("/persons/", (routeContext) -> {
      try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
        final Person person = routeContext.createEntityFromBody(Person.class);

        sqlSession.getMapper(PersonMapper.class).create(person);
        sqlSession.commit();

        routeContext.getResponse().created().json().send(person);
      }
    });

    // retrieve
    GET("/persons/{key}", (routeContext) -> {
      try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
        final Person person = sqlSession.getMapper(PersonMapper.class).retrieve(
            routeContext.getParameter("key").toLong());

        routeContext.json().send(person);
      }
    });

    // findAll
    GET("/persons/", (routeContext) -> {
      try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
        final List<Person> persons = sqlSession.getMapper(PersonMapper.class).findAll();

        routeContext.json().send(
          new ResponseAllPersonsEvent()
            .setEmpty(persons.isEmpty())
            .setPersons(persons));
      }
    });

    // findBy
    GET("/persons", (routeContext) -> {
      try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
        final List<Person> persons = sqlSession.getMapper(PersonMapper.class).findBy(
            routeContext.createEntityFromParameters(RequestAllPersonsEvent.class));

        routeContext.json().send(
          new ResponseAllPersonsEvent()
            .setEmpty(persons.isEmpty())
            .setPersons(persons));
      }
    });
  }

}

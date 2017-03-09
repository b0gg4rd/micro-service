package net.coatli.java.service;

import static net.coatli.java.config.MyBatisConfig.sqlSessionFactory;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import net.coatli.java.domain.Person;
import net.coatli.java.events.ResponseAllPersonsEvent;
import net.coatli.java.events.ResponsePersonEvent;
import net.coatli.java.persistence.PersonMapper;
import ro.pippo.core.route.RouteGroup;

public class PersonMicroService extends RouteGroup {

  public PersonMicroService() {

    super("/persons");

    POST("/", (routeContext) -> {
      try (SqlSession sqlSession = sqlSessionFactory().openSession()) {
        sqlSession.getMapper(PersonMapper.class).create(routeContext.createEntityFromBody(Person.class));
        sqlSession.commit();
        routeContext.getResponse().created();
      }
    });

    GET("/{key}", (routeContext) -> {
      try (SqlSession sqlSession = sqlSessionFactory().openSession()) {
        final Person person = sqlSession.getMapper(PersonMapper.class).retrieve(
            routeContext.getParameter("key").toLong());
        routeContext.json().send(
          new ResponsePersonEvent()
            .setDomainFound(person != null)
            .setPerson(person));
      }
    });

    GET("/", (routeContext) -> {
      try (SqlSession sqlSession = sqlSessionFactory().openSession()) {
        final List<Person> persons = sqlSession.getMapper(PersonMapper.class).findAll();
        routeContext.json().send(
          new ResponseAllPersonsEvent()
            .setDomainFound(!persons.isEmpty())
            .setPersons(persons));
      }
    });

  }

}

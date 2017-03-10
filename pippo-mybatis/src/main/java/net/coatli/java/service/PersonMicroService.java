package net.coatli.java.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import net.coatli.java.PippoMyBatisApplication;
import net.coatli.java.domain.Person;
import net.coatli.java.events.ResponseAllPersonsEvent;
import net.coatli.java.events.ResponsePersonEvent;
import net.coatli.java.mapper.PersonMapper;
import ro.pippo.core.route.RouteGroup;

public class PersonMicroService extends RouteGroup {

  public PersonMicroService() {

    super("/persons");

    POST("/", (routeContext) -> {

      final PippoMyBatisApplication application = routeContext.getApplication();

      try (SqlSession sqlSession = application.getSqlSessionFactory().openSession()) {
        sqlSession.getMapper(PersonMapper.class).create(routeContext.createEntityFromBody(Person.class));
        sqlSession.commit();
        routeContext.getResponse().created();
      }
    });

    GET("/{key}", (routeContext) -> {

      final PippoMyBatisApplication application = routeContext.getApplication();

      try (SqlSession sqlSession = application.getSqlSessionFactory().openSession()) {
        final Person person = sqlSession.getMapper(PersonMapper.class).retrieve(
            routeContext.getParameter("key").toLong());
        routeContext.json().send(
          new ResponsePersonEvent()
            .setDomainFound(person != null)
            .setPerson(person));
      }
    });

    GET("/", (routeContext) -> {

      final PippoMyBatisApplication application = routeContext.getApplication();

      try (SqlSession sqlSession = application.getSqlSessionFactory().openSession()) {
        final List<Person> persons = sqlSession.getMapper(PersonMapper.class).findAll();
        routeContext.json().send(
          new ResponseAllPersonsEvent()
            .setDomainFound(!persons.isEmpty())
            .setPersons(persons));
      }
    });

  }

}

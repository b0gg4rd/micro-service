package net.coatli.java.service;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import net.coatli.java.config.MyBatisConfig;
import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.mapper.PersonMapper;
import ro.pippo.core.Application;
import ro.pippo.core.HttpConstants;

public class PersonMicroService extends Application {

  private static final int NO_CONTENT = 204;

  private static final String SQL_SESSION = "sqlSession";

  private static final String PERSON_MAPPER = "personMapper";

  @Override
  protected void onInit() {

    final SqlSessionFactory sqlSessionFactory = new MyBatisConfig(getPippoSettings()).sqlSessionFactory();

    // open sqlsession
    ALL("/.*", (routeContext) -> {
      final SqlSession sqlSession = sqlSessionFactory.openSession();

      routeContext.setLocal(SQL_SESSION, sqlSession);
      routeContext.setLocal(PERSON_MAPPER, sqlSession.getMapper(PersonMapper.class));

      routeContext.next();
    });

    // create
    POST("/persons/", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      final PersonMapper personMapper = routeContext.getLocal(PERSON_MAPPER);

      final Person person = routeContext.createEntityFromBody(Person.class).setKey(UUID.randomUUID().toString());

      personMapper.create(person);
      sqlSession.commit();

      routeContext.status(HttpConstants.StatusCode.CREATED).json().send(person);
    });

    // retrieve
    GET("/persons/{key}", (routeContext) -> {
      final PersonMapper personMapper = routeContext.getLocal(PERSON_MAPPER);
      final Person person = personMapper.retrieve(routeContext.getParameter("key").toString());

      if (person == null) {
        routeContext.status(NO_CONTENT);
      } else {
        routeContext.json().send(person);
      }
    });

    // update
    PUT("/persons/{key}", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      final PersonMapper personMapper = routeContext.getLocal(PERSON_MAPPER);

      personMapper.update(
          routeContext.createEntityFromBody(Person.class).setKey(routeContext.getParameter("key").toString()));
      sqlSession.commit();

      routeContext.status(NO_CONTENT);
    });

    // delete
    DELETE("/persons/{key}", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      final PersonMapper personMapper = routeContext.getLocal(PERSON_MAPPER);

      personMapper.delete(routeContext.getParameter("key").toString());
      sqlSession.commit();

      routeContext.status(NO_CONTENT);
    });

    // findAll
    GET("/persons/", (routeContext) -> {
      final PersonMapper personMapper = routeContext.getLocal(PERSON_MAPPER);

      final List<Person> persons = personMapper.findAll();

      if (persons.isEmpty()) {
        routeContext.status(NO_CONTENT);
      } else {
        routeContext.json().send(persons);
      }
    });

    // findBy
    GET("/persons", (routeContext) -> {
      final PersonMapper personMapper = routeContext.getLocal(PERSON_MAPPER);

      final List<Person> persons = personMapper.findBy(
          routeContext.createEntityFromParameters(RequestAllPersonsEvent.class));

      if (persons.isEmpty()) {
        routeContext.status(NO_CONTENT);
      } else {
        routeContext.json().send(persons);
      }
    });

    // close sqlsession
    ALL("/.*", (routeContext) -> {
      final SqlSession sqlSession = routeContext.removeLocal(SQL_SESSION);
      sqlSession.close();
    }).runAsFinally();

  }

}

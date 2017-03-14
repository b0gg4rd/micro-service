package net.coatli.java.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coatli.java.config.MyBatisConfig;
import net.coatli.java.domain.Person;
import net.coatli.java.persistence.PersonMapper;
import ro.pippo.core.Application;

public class PersonMicroService extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  private static final String SQL_SESSION = "sqlSession";

  private SqlSessionFactory sqlSessionFactory;

  @Override
  protected void onInit() {

    this.sqlSessionFactory = new MyBatisConfig(getPippoSettings()).sqlSessionFactory();

    // open sqlsession
    ALL("/.*", (routeContext) -> {
      final PersonMicroService personMicroService = routeContext.getApplication();
      routeContext.setLocal(SQL_SESSION, personMicroService.getSqlSessionFactory().openSession());
      routeContext.next();
    });

    // create
    POST("/persons/", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      sqlSession.getMapper(PersonMapper.class).create(routeContext.createEntityFromBody(Person.class));
      sqlSession.commit();
      routeContext.getResponse().created();
    });

    // retrieve
    GET("/persons/{key}", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      final Person person = sqlSession.getMapper(PersonMapper.class).retrieve(
          routeContext.getParameter("key").toLong());
      if (person == null) {
        routeContext.status(204);
      } else {
        routeContext.json().send(person);
      }
    });

    // update
    PUT("/persons/{key}", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      sqlSession.getMapper(PersonMapper.class).update(
          routeContext.createEntityFromBody(Person.class).setKey(routeContext.getParameter("key").toLong()));
      sqlSession.commit();
      routeContext.status(204);
    });

    // delete
    DELETE("/persons/{key}", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      sqlSession.getMapper(PersonMapper.class).delete(routeContext.getParameter("key").toLong());
      sqlSession.commit();
      routeContext.status(204);
    });

    // findAll
    GET("/persons/", (routeContext) -> {
      final SqlSession sqlSession = routeContext.getLocal(SQL_SESSION);
      final List<Person> persons = sqlSession.getMapper(PersonMapper.class).findAll();
      if (persons.isEmpty()) {
        routeContext.status(204);
      } else {
        routeContext.json().send(persons);
      }
    });

    // close sqlsession
    ALL("/.*", (routeContext) -> {
      final SqlSession sqlSession = routeContext.removeLocal(SQL_SESSION);
      sqlSession.close();
    }).runAsFinally();;

  }

  public SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }

}

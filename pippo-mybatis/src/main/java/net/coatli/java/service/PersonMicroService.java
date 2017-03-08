package net.coatli.java.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coatli.java.config.MyBatisConfig;
import net.coatli.java.domain.Person;
import net.coatli.java.events.ResponseAllPersonsEvent;
import net.coatli.java.persistence.PersonMapper;
import ro.pippo.core.Application;

public class PersonMicroService extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  private static final int CREATE_SUCCESS = 1;

  @Override
  protected void onInit() {

    final SqlSessionFactory sqlSessionFactory = new MyBatisConfig().sqlSessionFactory();

    POST("/persons/", (routeContext) -> {

      LOGGER.info("Creating person");

      try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

        if (CREATE_SUCCESS == sqlSession.getMapper(PersonMapper.class).create(new Person())) {
          routeContext.getResponse().created();
        } else {
          sqlSession.rollback();
          routeContext.getResponse().internalError();
        }
      }

    });

    GET("/persons/", (routeContext) -> {

      LOGGER.info("Finding all persons.");

      try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

        final List<Person> persons = sqlSession.getMapper(PersonMapper.class).findAll();

        routeContext.json().send(
          new ResponseAllPersonsEvent()
            .setDomainFound(!persons.isEmpty())
            .setPersons(persons));
      }

    });

  }

}

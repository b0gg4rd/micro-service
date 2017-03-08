package net.coatli.java.service;

import static net.coatli.java.config.MyBatisConfig.sqlSessionFactory;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coatli.java.domain.Person;
import net.coatli.java.events.ResponseAllPersonsEvent;
import net.coatli.java.persistence.PersonMapper;
import ro.pippo.core.Application;

public class PersonMicroService extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  @Override
  protected void onInit() {

    POST("/persons/", (routeContext) -> {

      LOGGER.info("Creating person");

      try (SqlSession sqlSession = sqlSessionFactory().openSession()) {

        sqlSession.getMapper(PersonMapper.class).create(routeContext.createEntityFromBody(Person.class));
        sqlSession.commit();
        routeContext.getResponse().created();

      }

    });

    GET("/persons/", (routeContext) -> {

      LOGGER.info("Finding all persons.");

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

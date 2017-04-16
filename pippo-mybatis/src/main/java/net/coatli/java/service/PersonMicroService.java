package net.coatli.java.service;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coatli.java.config.MyBatisConfig;
import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.mapper.PersonMapper;
import ro.pippo.core.Application;
import ro.pippo.core.HttpConstants;
import ro.pippo.core.PippoRuntimeException;

public class PersonMicroService extends Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(PersonMicroService.class);

  private static final int SUCCESS_CREATE = 1;
  private static final int SUCCESS_UPDATE = 1;
  private static final int SUCCESS_DELETE = 1;

  private SqlSessionFactory sqlSessionFactory;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onInit() {
    sqlSessionFactory = new MyBatisConfig(getPippoSettings()).sqlSessionFactory();
    create();
    retrieve();
    update();
    delete();
    findAll();
    findBy();
  }

  /**
   *
   */
  private void create() {
    POST("/persons/", (routeContext) -> {
      Person person = null;

      try {
       if ((person = routeContext.createEntityFromBody(Person.class)) == null) {
         throw new PippoRuntimeException("Entity empty");
       }
      } catch (final PippoRuntimeException exc) {
        LOGGER.error("Incorrect resource", exc);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final SqlSession sqlSession = sqlSessionFactory.openSession();
      final PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

      if (SUCCESS_CREATE == personMapper.create(person.setKey(UUID.randomUUID().toString()))) {
        sqlSession.commit();
        routeContext.status(HttpConstants.StatusCode.CREATED).json().send(person);
      } else {
        sqlSession.rollback();
        routeContext.status(HttpConstants.StatusCode.INTERNAL_ERROR);
      }
    });
  }

  /**
   *
   */
  private void retrieve() {
    GET("/persons/{key}", (routeContext) -> {

      final String key = routeContext.getParameter("key").toString();

      if (StringUtils.isBlank(key)) {
        LOGGER.error("Incorrect key '{}'", key);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final SqlSession sqlSession = sqlSessionFactory.openSession();
      final PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

      final Person person = personMapper.retrieve(key);

      if (person == null) {
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        routeContext.status(HttpConstants.StatusCode.OK).json().send(person);
      }
    });
  }

  private void update() {
    PUT("/persons/{key}", (routeContext) -> {

      Person person = null;

      try {
        person = routeContext
            .createEntityFromBody(Person.class)
            .setKey(routeContext.getParameter("key").toString());

        if (StringUtils.isBlank(person.getKey())) {
          LOGGER.error("Incorrect key '{}'", person.getKey());
          routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
          return ;
        }
      } catch (final PippoRuntimeException exc) {
        LOGGER.error("", exc);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final SqlSession sqlSession = sqlSessionFactory.openSession();
      final PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

      if (SUCCESS_UPDATE == personMapper.update(person)) {
        sqlSession.commit();
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        sqlSession.rollback();
        routeContext.status(HttpConstants.StatusCode.INTERNAL_ERROR);
      }
    });
  }

  private void delete() {
    DELETE("/persons/{key}", (routeContext) -> {
      final String key = routeContext.getParameter("key").toString();

      if (StringUtils.isBlank(key)) {
        LOGGER.error("Incorrect key '{}'", key);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final SqlSession sqlSession = sqlSessionFactory.openSession();
      final PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

      if (SUCCESS_DELETE == personMapper.delete(key)) {
        sqlSession.commit();
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        sqlSession.rollback();
        routeContext.status(HttpConstants.StatusCode.INTERNAL_ERROR);
      }
    });
  }

  private void findAll() {
    GET("/persons/", (routeContext) -> {

      final SqlSession sqlSession = sqlSessionFactory.openSession();
      final PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

      final List<Person> persons = personMapper.findAll();

      if (persons.isEmpty()) {
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        routeContext.status(HttpConstants.StatusCode.OK).json().send(persons);
      }
    });
  }

  /**
   *
   */
  private void findBy() {
    GET("/persons", (routeContext) -> {
      RequestAllPersonsEvent filters = null;

      try {
        filters = routeContext.createEntityFromParameters(RequestAllPersonsEvent.class);
      } catch(final PippoRuntimeException exc) {
        LOGGER.error("Incorrect filters", exc);
        routeContext.status(HttpConstants.StatusCode.BAD_REQUEST);
        return ;
      }

      final SqlSession sqlSession = sqlSessionFactory.openSession();
      final PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);

      final List<Person> persons = personMapper.findBy(filters);

      if (persons.isEmpty()) {
        routeContext.status(HttpConstants.StatusCode.NO_RESPONSE);
      } else {
        routeContext.status(HttpConstants.StatusCode.OK).json().send(persons);
      }
    });
  }

}

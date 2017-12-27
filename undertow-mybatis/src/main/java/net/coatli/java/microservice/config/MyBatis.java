package net.coatli.java.microservice.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.coatli.java.microservice.domain.Person;
import net.coatli.java.microservice.event.FindByPersonsEvent;
import net.coatli.java.microservice.persistence.PersonMapper;

/**
 *
 */
public final class MyBatis {

  /**
   *
   */
  private static final String HIKARI_PROPERTIES = "/conf/hikari.properties";

  /**
   *
   */
  private static volatile SqlSessionFactory SQL_SESSION_FACTORY = null;

  /**
   * Private explicit default constructor for {@literal Singleton} pattern.
   */
  private MyBatis() {
  }

  /**
   *
   * @return
   */
  public static SqlSessionFactory sqlSessionFactory() {

    if (SQL_SESSION_FACTORY == null) {
      SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(configuration());
    }

    return SQL_SESSION_FACTORY;
  }

  /**
   *
   * @return
   */
  private static Configuration configuration() {

    try (InputStream inputStream = MyBatis.class.getResourceAsStream(HIKARI_PROPERTIES)) {

      final Properties hikariConfig = new Properties();
      hikariConfig.load(inputStream);

      final Configuration configuration = new Configuration(
          new Environment("production",
                          new JdbcTransactionFactory(),
                          new HikariDataSource(new HikariConfig(hikariConfig))));

      // Aliases
      configuration.getTypeAliasRegistry().registerAlias(
          Person.class.getSimpleName(), Person.class);
      configuration.getTypeAliasRegistry().registerAlias(
          FindByPersonsEvent.class.getSimpleName(), FindByPersonsEvent.class);

      // Mappers
      configuration.addMapper(PersonMapper.class);

      return configuration;
    } catch (final Exception exc) {
      throw new RuntimeException("Can not create SQL Session Factory", exc);
    }

  }

}

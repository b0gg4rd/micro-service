package net.coatli.java.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import net.coatli.java.domain.Person;
import net.coatli.java.persistence.PersonMapper;

public class MyBatisConfig {

  private static final String DEFAULT_ENVIRONMENT = "production";

  private static SqlSessionFactory sqlSessionFactory;

  static {
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration());
  }

  public static SqlSessionFactory sqlSessionFactory() {
    return sqlSessionFactory;
  }

  private static DataSource dataSource() {
    final BasicDataSource dataSource = new BasicDataSource();

    final Properties prop = new Properties();

    try {
      prop.load(MyBatisConfig.class.getResourceAsStream("/conf/application.properties"));
    } catch (final IOException exc) {
      throw new RuntimeException("Error reading application.properties", exc);
    }

    dataSource.setDriverClassName(prop.getProperty("datasource.driver"));
    dataSource.setUrl(prop.getProperty("datasource.url"));
    dataSource.setUsername(prop.getProperty("datasource.username"));
    dataSource.setPassword(prop.getProperty("datasource.password"));

    return dataSource;
  }

  private static TransactionFactory transactionFactory() {
    return new JdbcTransactionFactory();
  }

  private static Environment environment() {
    return new Environment(DEFAULT_ENVIRONMENT, transactionFactory(), dataSource());
  }

  private static Configuration configuration() {
    final Configuration configuration = new Configuration(environment());

    configuration.getTypeAliasRegistry().registerAlias(Person.class);
    configuration.addMapper(PersonMapper.class);

    return configuration;
  }

}

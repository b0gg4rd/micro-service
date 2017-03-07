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

import net.coatli.java.persistence.PersonMapper;

public class MyBatisConfig {

  private static final String DEFAULT_ENVIRONMENT = "production";

  public DataSource dataSource() {
    final BasicDataSource dataSource = new BasicDataSource();

    final Properties prop = new Properties();

    try {
      prop.load(getClass().getResourceAsStream("/conf/application.properties"));
    } catch (final IOException exc) {
      throw new RuntimeException("Error reading application.properties", exc);
    }

    dataSource.setDriverClassName(prop.getProperty("datasource.driver"));
    dataSource.setUrl(prop.getProperty("datasource.url"));
    dataSource.setUsername(prop.getProperty("datasource.username"));
    dataSource.setPassword(prop.getProperty("datasource.password"));

    return dataSource;
  }

  public TransactionFactory transactionFactory() {
    return new JdbcTransactionFactory();
  }

  public Environment environment() {
    return new Environment(DEFAULT_ENVIRONMENT, transactionFactory(), dataSource());
  }

  public Configuration configuration() {
    final Configuration configuration = new Configuration(environment());

    configuration.addMapper(PersonMapper.class);

    return configuration;
  }

  public SqlSessionFactory sqlSessionFactory() {
    return new SqlSessionFactoryBuilder().build(configuration());
  }

}

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

public final class MyBatisConfig {

  private static final MyBatisConfig INSTANCE = new MyBatisConfig();

  private static final String DATASOURCE_DRIVER   = "datasource.driver";
  private static final String DATASOURCE_URL      = "datasource.url";
  private static final String DATASOURCE_USERNAME = "datasource.username";
  private static final String DATASOURCE_PASSWORD = "datasource.password";

  private static final String DEFAULT_ENVIRONMENT = "production";

  private MyBatisConfig(){
  }

  public static MyBatisConfig getInstance() {
    return INSTANCE;
  }

  public SqlSessionFactory sqlSessionFactory() {
    return new SqlSessionFactoryBuilder().build(configuration());
  }

  private DataSource dataSource() {
    final BasicDataSource dataSource = new BasicDataSource();

    final Properties prop = new Properties();

    try {
      prop.load(MyBatisConfig.class.getResourceAsStream("/conf/application.properties"));
    } catch (final IOException exc) {
      throw new RuntimeException("Error reading application.properties", exc);
    }

    dataSource.setDriverClassName(prop.getProperty(DATASOURCE_DRIVER));
    dataSource.setUrl(prop.getProperty(DATASOURCE_URL));
    dataSource.setUsername(prop.getProperty(DATASOURCE_USERNAME));
    dataSource.setPassword(prop.getProperty(DATASOURCE_PASSWORD));

    return dataSource;
  }

  private TransactionFactory transactionFactory() {
    return new JdbcTransactionFactory();
  }

  private Environment environment() {
    return new Environment(DEFAULT_ENVIRONMENT, transactionFactory(), dataSource());
  }

  private Configuration configuration() {
    final Configuration configuration = new Configuration(environment());

    configuration.getTypeAliasRegistry().registerAlias(Person.class);
    configuration.addMapper(PersonMapper.class);

    return configuration;
  }

}

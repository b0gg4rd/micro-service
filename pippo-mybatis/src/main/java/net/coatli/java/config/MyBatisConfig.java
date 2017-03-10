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
import org.apache.ibatis.type.TypeAliasRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.mapper.PersonMapper;

public final class MyBatisConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisConfig.class);

  private static final MyBatisConfig INSTANCE = new MyBatisConfig();

  private static final String APPLICATION_PROPERTIES = "/conf/application.properties";

  private static final String DATASOURCE_DRIVER   = "datasource.driver";
  private static final String DATASOURCE_URL      = "datasource.url";
  private static final String DATASOURCE_USERNAME = "datasource.username";
  private static final String DATASOURCE_PASSWORD = "datasource.password";

  private static final String DEFAULT_ENVIRONMENT = "production";

  private final Properties properties;

  private MyBatisConfig(){
    properties = new Properties();

    try {
      properties.load(MyBatisConfig.class.getResourceAsStream(APPLICATION_PROPERTIES));
    } catch (final IOException exc) {
      throw new RuntimeException("Error reading application.properties", exc);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Configuring MyBatis with: {}", properties);
    }
  }

  public static MyBatisConfig getInstance() {
    return INSTANCE;
  }

  public SqlSessionFactory sqlSessionFactory() {
    return new SqlSessionFactoryBuilder().build(configuration());
  }

  private Configuration configuration() {
    final Configuration configuration = new Configuration(environment());

    registerAliases(configuration.getTypeAliasRegistry());

    addMappers(configuration);

    return configuration;
  }

  private void registerAliases(final TypeAliasRegistry typeAliasRegistry) {

    typeAliasRegistry.registerAlias("Person", Person.class);
    typeAliasRegistry.registerAlias("RequestAllPersonsEvent", RequestAllPersonsEvent.class);

  }

  private void addMappers(final Configuration configuration) {

    configuration.addMapper(PersonMapper.class);
  }

  private Environment environment() {
    return new Environment(DEFAULT_ENVIRONMENT, transactionFactory(), dataSource());
  }

  private TransactionFactory transactionFactory() {
    return new JdbcTransactionFactory();
  }

  private DataSource dataSource() {
    final BasicDataSource dataSource = new BasicDataSource();

    dataSource.setDriverClassName(properties.getProperty(DATASOURCE_DRIVER));
    dataSource.setUrl(properties.getProperty(DATASOURCE_URL));
    dataSource.setUsername(properties.getProperty(DATASOURCE_USERNAME));
    dataSource.setPassword(properties.getProperty(DATASOURCE_PASSWORD));

    return dataSource;
  }

}

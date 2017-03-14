package net.coatli.java.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;

import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.mapper.PersonMapper;
import ro.pippo.core.PippoSettings;

public class MyBatisConfig {

  private static final String DEFAULT_ENVIRONMENT = "production";

  private static final String DATASOURCE_DRIVER   = "datasource.driver";
  private static final String DATASOURCE_URL      = "datasource.url";
  private static final String DATASOURCE_USERNAME = "datasource.username";
  private static final String DATASOURCE_PASSWORD = "datasource.password";

  private final PippoSettings pippoSettings;

  public MyBatisConfig(final PippoSettings pippoSettings) {
    this.pippoSettings = pippoSettings;
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

    dataSource.setDriverClassName(pippoSettings.getString(DATASOURCE_DRIVER, null));
    dataSource.setUrl(pippoSettings.getString(DATASOURCE_URL, null));
    dataSource.setUsername(pippoSettings.getString(DATASOURCE_USERNAME, null));
    dataSource.setPassword(pippoSettings.getString(DATASOURCE_PASSWORD, null));

    return dataSource;
  }

}

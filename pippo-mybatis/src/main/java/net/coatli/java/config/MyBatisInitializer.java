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
import org.kohsuke.MetaInfServices;

import net.coatli.java.domain.Person;
import net.coatli.java.event.RequestAllPersonsEvent;
import net.coatli.java.mapper.PersonMapper;
import net.coatli.java.service.PersonMicroService;
import ro.pippo.core.Application;
import ro.pippo.core.Initializer;
import ro.pippo.core.PippoSettings;

@MetaInfServices
public class MyBatisInitializer implements Initializer {

  private static final String DEFAULT_ENVIRONMENT = "production";

  private static final String DATASOURCE_DRIVER   = "datasource.driver";
  private static final String DATASOURCE_URL      = "datasource.url";
  private static final String DATASOURCE_USERNAME = "datasource.username";
  private static final String DATASOURCE_PASSWORD = "datasource.password";

  @Override
  public void init(final Application application) {


    ((PersonMicroService )application).setPersonMapper(
        sqlSessionFactory(application.getPippoSettings()).openSession().getMapper(PersonMapper.class));
  }

  @Override
  public void destroy(final Application application) {
  }

  private SqlSessionFactory sqlSessionFactory(final PippoSettings pippoSettings) {
    return new SqlSessionFactoryBuilder().build(configuration(pippoSettings));
  }

  private Configuration configuration(final PippoSettings pippoSettings) {
    final Configuration configuration = new Configuration(environment(pippoSettings));

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

  private Environment environment(final PippoSettings pippoSettings) {
    return new Environment(DEFAULT_ENVIRONMENT, transactionFactory(), dataSource(pippoSettings));
  }

  private TransactionFactory transactionFactory() {
    return new JdbcTransactionFactory();
  }

  private DataSource dataSource(final PippoSettings pippoSettings) {
    final BasicDataSource dataSource = new BasicDataSource();

    dataSource.setDriverClassName(pippoSettings.getString(DATASOURCE_DRIVER, null));
    dataSource.setUrl(pippoSettings.getString(DATASOURCE_URL, null));
    dataSource.setUsername(pippoSettings.getString(DATASOURCE_USERNAME, null));
    dataSource.setPassword(pippoSettings.getString(DATASOURCE_PASSWORD, null));

    return dataSource;
  }

}

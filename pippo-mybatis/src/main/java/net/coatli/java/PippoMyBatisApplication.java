package net.coatli.java;

import org.apache.ibatis.session.SqlSessionFactory;

import net.coatli.java.config.MyBatisConfig;
import net.coatli.java.service.PersonMicroService;
import ro.pippo.core.Application;
import ro.pippo.core.Pippo;

public class PippoMyBatisApplication extends Application {

  private SqlSessionFactory sqlSessionFactory;

  @Override
  public void onInit() {
    sqlSessionFactory = MyBatisConfig.getInstance().sqlSessionFactory();
    addRouteGroup(new PersonMicroService());
  }

  public SqlSessionFactory getSqlSessionFactory() {
    return sqlSessionFactory;
  }

  public static void main(final String[] args) {
    new Pippo(new PippoMyBatisApplication()).start();
  }

}

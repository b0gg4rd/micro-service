package net.coatli.java;

import net.coatli.java.service.PersonMicroService;
import ro.pippo.core.Application;
import ro.pippo.core.Pippo;

public class PippoMyBatisApplication extends Application {

  public static void main(final String[] args) {

    new Pippo(new Application() {
      @Override
      public void onInit() {
        addRouteGroup(new PersonMicroService());
      }
    }).start();

  }
}


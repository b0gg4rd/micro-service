package net.coatli.java;

import net.coatli.java.service.PersonMicroService;
import ro.pippo.core.Pippo;

public class PippoMyBatisApplication {

  public static void main(final String[] args) {

    new Pippo(new PersonMicroService()).start();

  }
}


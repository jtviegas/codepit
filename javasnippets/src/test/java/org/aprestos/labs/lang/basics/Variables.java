package org.aprestos.labs.lang.basics;

import org.junit.Assert;
import org.junit.Test;

public class Variables {

  @Test
  public void test_00() {

    Dummy o = new Dummy();

    Assert.assertTrue('\u0000' == o.c);

  }

  class Dummy {
    char c;
  }
  
  

}

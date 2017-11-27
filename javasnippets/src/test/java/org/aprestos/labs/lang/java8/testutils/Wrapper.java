package org.aprestos.labs.lang.java8.testutils;

public class Wrapper {
  String id;

  long number;

  public Wrapper(String id, long number) {
    this.id = id;
    this.number = number;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getNumber() {
    return number;
  }

  public void setNumber(long number) {
    this.number = number;
  }
}

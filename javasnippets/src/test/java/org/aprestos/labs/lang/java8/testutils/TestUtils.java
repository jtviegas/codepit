package org.aprestos.labs.lang.java8.testutils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class TestUtils {

  private static final int DEFAULT_STRING_LENGTH = 16;

  public static List<Wrapper> getWrappers(int n) {
    List<Wrapper> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      result.add(new Wrapper(UUID.randomUUID().toString(), (long) (100 * Math.random())));
    }
    return result;
  }

  public static List<String> getRandomStringList(int n) {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < n; i++)
      result.add(RandomStringUtils.random(DEFAULT_STRING_LENGTH));

    return result;
  }

}

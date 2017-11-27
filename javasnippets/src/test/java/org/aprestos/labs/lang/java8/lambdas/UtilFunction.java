package org.aprestos.labs.lang.java8.lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.aprestos.labs.lang.java8.testutils.TestUtils;
import org.junit.Test;

public class UtilFunction {

  public static final Predicate<String> BIGGER_THAN_5 = s -> s.length() > 5;

  public static final Predicate<String> SMALLER_THAN_20 = s -> s.length() < 20;

  @Test
  public void predicates() {

    List<String> names = TestUtils.getRandomStringList(12);
    Optional<String> first = names.stream().filter(s -> (s.length() == 17)).findFirst();
    System.out.println(first.orElse("none"));

    System.out.println(
        first.orElse(String.format("1 nothing there in %s", names.stream().collect(Collectors.joining(", ")))));
    System.out.println(first
        .orElseGet(() -> String.format("2 nothing there in %s", names.stream().collect(Collectors.joining(", ")))));

    first = names.stream().filter(s -> (s.length() == 16)).findFirst();
    System.out.println(first.orElse("none"));

    System.out.println(
        first.orElse(String.format("3 nothing there in %s", names.stream().collect(Collectors.joining(", ")))));
    System.out.println(first
        .orElseGet(() -> String.format("4 nothing there in %s", names.stream().collect(Collectors.joining(", ")))));

    System.out.println(first.orElse("none"));

    System.out.println(getNamesSatisfyingCondition(s -> s.length() > 3, names.toArray(new String[] {})));

    System.out.println(getNamesSatisfyingCondition(BIGGER_THAN_5.and(SMALLER_THAN_20), names.toArray(new String[] {})));

  }

  public String getNamesSatisfyingCondition(Predicate<String> condition, String... names) {

    return Arrays.stream(names).filter(condition).collect(Collectors.joining(", "));

  }

  @Test
  public void functions() {

  }

}

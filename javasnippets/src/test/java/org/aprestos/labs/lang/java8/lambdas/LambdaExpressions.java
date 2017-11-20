package org.aprestos.labs.lang.java8.lambdas;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class LambdaExpressions {

  @Test
  public void test() {
    assorted();
  }

  void assorted() {

    Runnable r = () -> System.out.println("hola");
    new Thread(r).start();

    File dir = new File("src/main/java/org/aprestos/labs/lang");
    String[] names = dir.list((d, n) -> n.endsWith(".java"));
    System.out.println(Arrays.asList(names));

    Stream.of(4, 34, 5, 78, 9, 5, 33, 61).forEach(System.out::println);

    Stream.generate(Math::random).limit(10).forEach(System.out::println);

    Stream.of("a", "b", "cv", "df").sorted((s1, s2) -> s1.compareTo(s2)).collect(Collectors.toList())
        .forEach(System.out::println);

    Stream.of("abaner", "bivnr", "cv", "fudf").map(String::length).forEach(System.out::println);

  }

}

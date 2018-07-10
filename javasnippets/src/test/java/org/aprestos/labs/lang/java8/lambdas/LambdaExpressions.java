package org.aprestos.labs.lang.java8.lambdas;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.aprestos.labs.lang.java8.testutils.Person;
import org.aprestos.labs.lang.java8.testutils.TestUtils;
import org.aprestos.labs.lang.java8.testutils.Wrapper;
import org.junit.Assert;
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
    if (null != names)
      System.out.println(Arrays.asList(names));

    Stream.of(4, 34, 5, 78, 9, 5, 33, 61).forEach(System.out::println);

    Stream.generate(Math::random).limit(10).forEach(System.out::println);

    Stream.of("a", "b", "cv", "df").sorted((s1, s2) -> s1.compareTo(s2)).collect(Collectors.toList())
        .forEach(System.out::println);

    Stream.of("abaner", "bivnr", "cv", "fudf").map(String::length).forEach(System.out::println);

    TestUtils.getWrappers(12).stream().map(Wrapper::getId).collect(Collectors.toList()).forEach(System.out::println);
    Stream.of("abaner", "bivnr", "cv", "fudf").map(Person::new).forEach(System.out::println);

    Person[] ps = Stream.of("abaner", "bivnr", "cv", "fudf").map(Person::new).toArray(Person[]::new);

    List<String> o = Stream.of(4, 34, 5, 78, 9, 5, 33, 61).map(new MyTransf()).collect(Collectors.toList());
    Assert.assertEquals(8, o.size());
    o = Stream.of(4, 34, 5, 78, 9, 5, 33, 61).flatMap(new MyTransfStream()).collect(Collectors.toList());
    Assert.assertEquals(8, o.size());

  }

}

class MyTransfStream implements Function<Integer, Stream<String>> {

  @Override
  public Stream<String> apply(Integer t) {
    return Stream.of(Integer.toString(t));
  }

}

class MyTransf implements Function<Integer, String> {

  @Override
  public String apply(Integer value) {
    return Integer.toString(value);
  }
}

package org.aprestos.labs.concurrency.pragmatic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.tuple.Pair;
import org.aprestos.labs.concurrency.pragmatic.assorted.BalancedListRandomPartitioning;
import org.aprestos.labs.concurrency.pragmatic.assorted.Exchange;
import org.aprestos.labs.concurrency.pragmatic.assorted.Primes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Tests {

  List<Pair<String, String>> currencies = new ArrayList<Pair<String, String>>();

  List<String> sites = new ArrayList<String>();

  final Exchange exchange = new Exchange();

  final Primes primes = new Primes();

  @Before
  public void setup() {
    currencies.add(Pair.of("GBP", "EUR"));
    currencies.add(Pair.of("USD", "EUR"));
    currencies.add(Pair.of("GBP", "USD"));
    currencies.add(Pair.of("GBP", "INR"));
    currencies.add(Pair.of("GBP", "AUD"));

    sites.add("https://news.google.com");
    sites.add("https://www.amazon.co.uk/");
    sites.add("https://www.theguardian.com/international");
    sites.add("https://www.thetimes.co.uk/");
    sites.add("https://www.ft.com");
    sites.add("https://www1.folha.uol.com.br/internacional/en/");
    sites.add("https://elpais.com/");
    sites.add("http://www.lavanguardia.com");
    sites.add("http://www.elmundo.es/");
    sites.add("https://www.lequipe.fr/");

  }

  @SuppressWarnings("unused")
  private double computeExchangeRates() {

    Exchange t = new Exchange();

    final long start = System.nanoTime();
    double total = new Double(0);
    for (Pair<String, String> p : currencies)
      total += t.getExchangeRate(p.getLeft(), p.getRight());

    final long end = System.nanoTime();
    System.out.println(String.format("total currencies exchange value %f", total));
    System.out.println("Time (seconds) taken " + (end - start) / 1.0e9);
    return total;
  }

  private double computeSitesLength() {

    final long start = System.nanoTime();
    double total = new Double(0);
    for (String s : sites)
      total += exchange.getPageLength(s);

    final long end = System.nanoTime();
    System.out.println(String.format("total pages length %f", total));
    System.out.println("Time (seconds) taken " + (end - start) / 1.0e9);
    return total;
  }

  @Test
  public void test_synchronous() throws Exception {
    System.out.println("\n============================test_synchronous=============================\n");
    computeSitesLength();
    Assert.assertTrue(true);
  }

  @Test
  public void test_asynchronous() throws Exception {
    System.out.println("\n===========================test_asynchronous==============================\n");
    final int numberOfCores = Runtime.getRuntime().availableProcessors();
    final double blockingCoefficient = 0.9;
    final int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
    System.out.println("Number of Cores available is " + numberOfCores);
    System.out.println("Pool size is " + poolSize);
    final List<Callable<Double>> lenghts = new ArrayList<Callable<Double>>();

    for (final String s : sites) {
      lenghts.add(new Callable<Double>() {
        @Override
        public Double call() throws Exception {
          return exchange.getPageLength(s);
        }
      });
    }

    final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
    final long start = System.nanoTime();
    final List<Future<Double>> lenghtFutures = executorPool.invokeAll(lenghts, 10000, TimeUnit.SECONDS);
    double total = 0.0;
    for (final Future<Double> l : lenghtFutures)
      total += l.get();
    final long end = System.nanoTime();
    System.out.println(String.format("total pages length %f", total));
    System.out.println("Time (seconds) taken " + (end - start) / 1.0e9);

    executorPool.shutdown();

    Assert.assertTrue(true);
  }

  @Test
  public void test_synchronous_primes() throws Exception {
    System.out.println("\n============================test_synchronous_primes=============================\n");
    primes.timeAndCompute(100000000);
    Assert.assertTrue(true);
  }

  @Test
  public void test_asynchronous_primes() throws Exception {
    System.out.println("\n============================test_asynchronous_primes=============================\n");

    final int poolSize = 2;
    final int numberOfParts = 2;

    primes.timeAndCompute(100000000);
    Assert.assertTrue(true);
  }

  @Test
  public void test_sss() throws Exception {

    BalancedListRandomPartitioning<Integer> p = new BalancedListRandomPartitioning<>(3);
    System.out.print(p.apply(Arrays.asList(2, 3, 1, 7, 8, 9, 0, 4, 5, 6, 7)));

    Assert.assertTrue(true);
  }

}

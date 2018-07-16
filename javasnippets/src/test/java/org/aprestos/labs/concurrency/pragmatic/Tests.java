package org.aprestos.labs.concurrency.pragmatic;

import org.aprestos.labs.concurrency.pragmatic.executions.DedicatedForkPoolExecution;
import org.aprestos.labs.concurrency.pragmatic.executions.Execution;
import org.aprestos.labs.concurrency.pragmatic.executions.ThreadPoolExecution;
import org.aprestos.labs.concurrency.pragmatic.works.computing.primescounting.PrimesCounting;
import org.aprestos.labs.concurrency.pragmatic.works.io.webpagelength.PagesLength;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tests {

  private static final Logger logger = LoggerFactory.getLogger(Tests.class);

  @Test
  public void test_AA_inertia_breaker() throws Exception {
    Assert.assertTrue(true);
  }

  @Test
  public void test_100pagesLength_ThreadPoolExecution() throws Exception {

    Execution execution = new ThreadPoolExecution(1.0, new PagesLength(100));
    logger.info("outcome: {}", execution.execute());

    Assert.assertTrue(true);
  }

  @Test
  public void test_countPrimesfrom10000With10Partitions_ThreadPoolExecution() throws Exception {

    Execution execution = new ThreadPoolExecution(0.0, new PrimesCounting(10000, 6));
    logger.info("outcome: {}", execution.execute());

    Assert.assertTrue(true);
  }

  @Test
  public void test_100pagesLength_DedicatedForkPoolExecution() throws Exception {

    Execution execution = new DedicatedForkPoolExecution(1.0, new PagesLength(100));
    logger.info("outcome: {}", execution.execute());

    Assert.assertTrue(true);
  }

  @Test
  public void test_countPrimesfrom10000With10Partitions_DedicatedForkPoolExecution() throws Exception {

    Execution execution = new DedicatedForkPoolExecution(0.0, new PrimesCounting(10000, 6));
    logger.info("outcome: {}", execution.execute());

    Assert.assertTrue(true);
  }

}

package org.aprestos.labs.experiment.jms;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;

public class Producer implements Runnable {

  private static final Logger logger = LoggerFactory.getLogger(JmsqueueprodApplication.class);

  private static final AtomicLong counter = new AtomicLong(0);

  private final JmsTemplate template;

  private final String queueName;

  private final ScheduledExecutorService executor;

  private static long count = 0;

  public Producer(JmsTemplate jmsTemplate, final String queue) {
    this.template = jmsTemplate;
    this.queueName = queue;
    executor = Executors.newScheduledThreadPool(1);
    executor.scheduleAtFixedRate(this, 0, 10, TimeUnit.SECONDS);

    Runtime.getRuntime().addShutdownHook(new Thread() {

      @Override
      public void run() {
        executor.shutdown();
        try {
          if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            logger.warn("Executor did not terminate in the specified time.");
            List<Runnable> droppedTasks = executor.shutdownNow();
            logger.warn("Executor was abruptly shut down. " + droppedTasks.size() + " tasks will not be executed.");
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

  }

  @Override
  public void run() {
    logger.info("sending message...");

    Message m = new Message(counter.getAndIncrement(), "someone",
        String.format("olha ai, pa! sao: %s", new Date().toString()));
    try {
      template.convertAndSend(queueName, m);
    } catch (JmsException e) {
      logger.error("ooopppsss", e);
    }
    logger.info("...[total: {}]...sent message: {}", ++count, m.getId());

  }

}

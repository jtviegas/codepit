package org.aprestos.labs.experiment.jms;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationTests.class);

  @Value("${app.queue.test}")
  private String queueName;

  @Autowired
  private JmsTemplate jmsTemplate;

  @Test
  public void testIt() {
    logger.info("sending message...");
    Message m = new Message("someone", String.format("olha ai,pa! sao: %s", new Date().toString()));
    jmsTemplate.convertAndSend(queueName, m);
    logger.info("...sent message: {}", m);
  }

}

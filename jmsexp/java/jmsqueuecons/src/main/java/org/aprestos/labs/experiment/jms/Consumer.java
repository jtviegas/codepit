package org.aprestos.labs.experiment.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.jms.support.converter.MessageConverter;

public class Consumer implements SessionAwareMessageListener<TextMessage> {

  private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

  private static long count = 0;

  private final MessageConverter converter;

  public Consumer(MessageConverter converter) {
    this.converter = converter;
  }

  @Override
  public void onMessage(TextMessage message, Session session) throws JMSException {
    try {
      Message m = (Message) converter.fromMessage(message);
      logger.info("[total: {}] received message: {}", ++count, m.getId());
      session.commit();
    } catch (Exception e) {
      logger.error("ooopppsss", e);
      session.rollback();
    }
  }

}

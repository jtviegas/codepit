package org.aprestos.labs.ibmmqjmsclient;

import javax.jms.BytesMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@SpringBootApplication
public class IbmmqjmsclientApplication implements CommandLineRunner {

  private static Logger LOG = LoggerFactory.getLogger(IbmmqjmsclientApplication.class);

  @Autowired
  private JmsTemplate jmsTemplate;

  public static void main(String[] args) {
    LOG.info("STARTING THE APPLICATION");
    SpringApplication.run(IbmmqjmsclientApplication.class, args);
    LOG.info("APPLICATION FINISHED");
  }

  @Override
  public void run(String... args) throws Exception {
    LOG.info("EXECUTING : command line runner");

    try {

      while (true) {
        
        BytesMessage message = (BytesMessage) jmsTemplate.receive("DEV.QUEUE.1");      
        byte[] bytes = new byte[(int)message.getBodyLength()];
        message.readBytes(bytes);
        LOG.info(new String(bytes));

        Thread.sleep(5*1000);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    finally {
      LOG.info("LEAVING : command line runner");
    }

  }

}

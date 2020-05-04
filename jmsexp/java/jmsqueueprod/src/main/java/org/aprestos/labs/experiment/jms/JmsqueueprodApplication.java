package org.aprestos.labs.experiment.jms;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication
public class JmsqueueprodApplication {

  @Value("${spring.activemq.broker-url}")
  String brokerUrl;

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(JmsqueueprodApplication.class, args);
    // new Producer(context.getBean(JmsTemplate.class), context.getEnvironment().getProperty("app.queue.test"));
    new Producer(context.getBean(JmsTemplate.class), System.getenv("queue"));

  }

  @Bean
  public ConnectionFactory connectionFactory() {
    return new ActiveMQConnectionFactory(brokerUrl);
  }

  @Bean
  public MessageConverter jacksonJmsMessageConverter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setTargetType(MessageType.TEXT);
    converter.setTypeIdPropertyName("_type");
    return converter;
  }

}

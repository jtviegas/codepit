package org.aprestos.labs.experiment.jms;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication

public class JmsqueueconsApplication implements JmsListenerConfigurer {

  @Value("${spring.activemq.broker-url}")
  String brokerUrl;

  public static void main(String[] args) {
    SpringApplication.run(JmsqueueconsApplication.class, args);
  }

  @Bean
  public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
      DefaultJmsListenerContainerFactoryConfigurer configurer) {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

    factory.setSessionTransacted(true);
    factory.setConcurrency("3-10");

    configurer.configure(factory, connectionFactory);

    return factory;
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

  @Override
  public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {

    SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
    endpoint.setId("jmsqueuecons");
    endpoint.setDestination(System.getenv("queue"));
    endpoint.setMessageListener(new MessageListenerAdapter(new Consumer(jacksonJmsMessageConverter())));
    registrar.registerEndpoint(endpoint);

  }

}

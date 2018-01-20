package org.aprestos.labs.data.jparelationships;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(Literals.class)
@PropertySource("classpath:literals.yml")
public class TestConfiguration {
}

package org.aprestos.labs.data.jparelationships;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("org.aprestos.labs.data.jparelationships.repository")
@EntityScan("org.aprestos.labs.data.jparelationships.model")
@SpringBootApplication
@EnableConfigurationProperties(Literals.class)
@PropertySource("classpath:literals.yml")
public class AppConfiguration {

}

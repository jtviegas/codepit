package org.aprestos.labs.data.jpahibernateorm;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("org.aprestos.labs.data.jpahibernateorm.fk.one.repository")
@EntityScan("org.aprestos.labs.data.jpahibernateorm.fk.one.model")
@SpringBootApplication
public class AppConfiguration {

}

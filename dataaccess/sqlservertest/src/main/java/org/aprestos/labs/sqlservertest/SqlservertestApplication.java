package org.aprestos.labs.sqlservertest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("org.aprestos.labs.sqlservertest.repository")
@EntityScan("org.aprestos.labs.sqlservertest.model")
@SpringBootApplication
public class SqlservertestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SqlservertestApplication.class, args);
	}

}


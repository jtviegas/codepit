package org.aprestos.labs.tests.caching.engines.neo4j;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.aprestos.labs.tests.caching.engines.neo4j.conf.LocalNeo4jConfig;
import org.aprestos.labs.tests.caching.engines.neo4j.repositories.Neo4jStateRepository;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ConfigTest {

	@Test
	public void bootstrapAppFromJavaConfig() {

		ApplicationContext context = new AnnotationConfigApplicationContext(LocalNeo4jConfig.class);
		assertThat(context, is(notNullValue()));
		assertThat(context.getBean(Neo4jStateRepository.class), is(notNullValue()));
	}
	
	

}

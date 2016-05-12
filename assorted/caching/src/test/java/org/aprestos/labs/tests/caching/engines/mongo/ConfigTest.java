package org.aprestos.labs.tests.caching.engines.mongo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.aprestos.labs.tests.caching.engines.mongo.conf.LocalMongoConfig;
import org.aprestos.labs.tests.caching.engines.mongo.repositories.StateMongoRepository;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ConfigTest {

	@Test
	public void bootstrapAppFromJavaConfig() {

		ApplicationContext context = new AnnotationConfigApplicationContext(LocalMongoConfig.class);
		assertThat(context, is(notNullValue()));
		assertThat(context.getBean(StateMongoRepository.class), is(notNullValue()));
	}
	
	

}

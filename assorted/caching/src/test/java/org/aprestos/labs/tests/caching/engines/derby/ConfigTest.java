package org.aprestos.labs.tests.caching.engines.derby;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.aprestos.labs.tests.caching.engines.derby.conf.LocalDerbyConfig;
import org.aprestos.labs.tests.caching.engines.derby.repositories.StateRepository;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ConfigTest {

	@Test
	public void bootstrapAppFromJavaConfig() {

		ApplicationContext context = new AnnotationConfigApplicationContext(LocalDerbyConfig.class);
		assertThat(context, is(notNullValue()));
		assertThat(context.getBean(StateRepository.class), is(notNullValue()));
	}
	
	

}

package org.aprestos.labs.tests.caching.engines.jcs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.aprestos.labs.tests.caching.engines.jcs.conf.LocalJcsConfig;
import org.aprestos.labs.tests.caching.engines.redis.repositories.KeyValueService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class ConfigTest {

	@Test
	public void bootstrapAppFromJavaConfig() {

		ApplicationContext context = new AnnotationConfigApplicationContext(LocalJcsConfig.class);
		assertThat(context, is(notNullValue()));
		assertThat(context.getBean("jcsMemoryAndDiskCache"), is(notNullValue()));
	}
	
	

}

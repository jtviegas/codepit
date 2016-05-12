package org.aprestos.labs.tests.caching.engines.redis;

import org.aprestos.labs.tests.caching.common.MemoryCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.redis.conf.LocalRedisConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.StateDataFeederLoopImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class RedisIntegrationTest extends MemoryCacheTestAbstract {
	
	private ApplicationContext context;
	
	public RedisIntegrationTest() {
		super();
		//load spring config
		context = new AnnotationConfigApplicationContext(LocalRedisConfig.class);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ICache<String, String> setUpCache() {
		return (ICache<String, String>)context.getBean("redisCache");
	}

	@Override
	protected DataFeeder<State> setUpDataFeeder() throws Exception {
		return new StateDataFeederLoopImpl(1000);
	}

}

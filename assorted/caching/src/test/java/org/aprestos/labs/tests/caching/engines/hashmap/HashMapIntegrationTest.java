package org.aprestos.labs.tests.caching.engines.hashmap;

import org.aprestos.labs.tests.caching.common.MemoryCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.hashmap.conf.LocalHashMapConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.StateDataFeederLoopImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HashMapIntegrationTest extends MemoryCacheTestAbstract {
	
	private ApplicationContext context;
	
	public HashMapIntegrationTest() {
		super();
		//load spring config
		context = new AnnotationConfigApplicationContext(LocalHashMapConfig.class);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ICache<String, String> setUpCache() {
		return (ICache<String, String>)context.getBean("hashmapCache");
	}

	@Override
	protected DataFeeder<State> setUpDataFeeder() throws Exception {
		return new StateDataFeederLoopImpl(1000);
	}

}

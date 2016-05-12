package org.aprestos.labs.tests.caching.engines.hashmap.prevayler;

import org.aprestos.labs.tests.caching.common.PersistingCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.hashmap.conf.LocalHashMapConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.StateDataFeederLoopImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PrevaylerHashMapIntegrationTest extends PersistingCacheTestAbstract {
	
	private ApplicationContext context;
	
	public PrevaylerHashMapIntegrationTest() {
		super();
		//load spring config
		context = new AnnotationConfigApplicationContext(LocalHashMapConfig.class);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ICache<String, String> setUpCache() {
		return (ICache<String, String>)context.getBean("prevaylerStateCache");
	}

	@Override
	protected DataFeeder<State> setUpDataFeeder() throws Exception {
		return new StateDataFeederLoopImpl(1000);
	}

}

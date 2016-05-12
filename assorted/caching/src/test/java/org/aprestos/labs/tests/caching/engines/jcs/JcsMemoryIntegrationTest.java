package org.aprestos.labs.tests.caching.engines.jcs;

import org.aprestos.labs.tests.caching.common.MemoryCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.jcs.conf.LocalJcsConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.StateDataFeederLoopImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JcsMemoryIntegrationTest extends MemoryCacheTestAbstract {
	
	private ApplicationContext context;
	
	public JcsMemoryIntegrationTest() {
		super();
		//load spring config
		context = new AnnotationConfigApplicationContext(LocalJcsConfig.class);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ICache<String, String> setUpCache() {
		return (ICache<String, String>)context.getBean("jcsMemoryCache");
	}

	@Override
	protected DataFeeder<State> setUpDataFeeder() throws Exception {
		return new StateDataFeederLoopImpl(1000);
	}

}

package org.aprestos.labs.tests.caching.engines.derby;

import org.aprestos.labs.tests.caching.common.MemoryCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.derby.conf.LocalDerbyConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.StateDataFeederLoopImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class DerbyIntegrationTest extends MemoryCacheTestAbstract{

	private ApplicationContext context;

	public DerbyIntegrationTest(){
		super();
		//load spring config
		context = new AnnotationConfigApplicationContext(LocalDerbyConfig.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ICache<String, String> setUpCache() {
		return (ICache<String, String>)context.getBean("derbyCache");
	}

	@Override
	protected DataFeeder<State> setUpDataFeeder() throws Exception {
		return new StateDataFeederLoopImpl(1000);
	}

	
	
}

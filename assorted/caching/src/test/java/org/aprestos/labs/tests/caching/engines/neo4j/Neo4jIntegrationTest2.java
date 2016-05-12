package org.aprestos.labs.tests.caching.engines.neo4j;

import org.aprestos.labs.tests.caching.common.MemoryCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.neo4j.conf.LocalNeo4jConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.StateDataFeederLoopImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Neo4jIntegrationTest2 extends MemoryCacheTestAbstract {
	
	private ApplicationContext context;
	
	public Neo4jIntegrationTest2() {
		super();
		//load spring config
		context = new AnnotationConfigApplicationContext(LocalNeo4jConfig.class);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ICache<String, String> setUpCache() {
		return (ICache<String, String>)context.getBean("neo4jCache");
	}

	@Override
	protected DataFeeder<State> setUpDataFeeder() throws Exception {
		return new StateDataFeederLoopImpl(1000);
	}

}

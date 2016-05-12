package org.aprestos.labs.tests.caching.engines.neo4j;

import org.aprestos.labs.tests.caching.common.MemoryGraphCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.neo4j.conf.LocalNeo4jConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.IGraphCache;
import org.aprestos.labs.tests.caching.model.ProcessingNode;
import org.aprestos.labs.tests.caching.utils.PNDataFeederLoopImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Neo4jProcessingNodeIntegrationTest extends MemoryGraphCacheTestAbstract {
	
	private ApplicationContext context;
	
	public Neo4jProcessingNodeIntegrationTest() {
		super();
		//load spring config
		context = new AnnotationConfigApplicationContext(LocalNeo4jConfig.class);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	protected IGraphCache<String, ProcessingNode> setUpCache() {
		return (IGraphCache<String, ProcessingNode>)context.getBean("processingNodeCache");
	}

	@Override
	protected DataFeeder<ProcessingNode> setUpDataFeeder() throws Exception {
		return new PNDataFeederLoopImpl(1000);
	}

}

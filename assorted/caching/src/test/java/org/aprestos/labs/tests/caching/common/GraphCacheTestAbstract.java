package org.aprestos.labs.tests.caching.common;

import org.apache.log4j.PropertyConfigurator;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.IGraphCache;
import org.aprestos.labs.tests.caching.model.ProcessingNode;
import org.junit.After;
import org.junit.Before;

public abstract class GraphCacheTestAbstract {

	protected IGraphCache<String, ProcessingNode> cache;

	protected DataFeeder<ProcessingNode> feeder;

	public GraphCacheTestAbstract() {
		PropertyConfigurator.configure("log4j.properties");
	}

	protected abstract IGraphCache<String, ProcessingNode> setUpCache();

	protected abstract DataFeeder<ProcessingNode> setUpDataFeeder() throws Exception;
	
	public abstract void testLoadOfPNs() throws Exception;
	

	@Before
	public void beforeTest() throws Exception {
		cache = setUpCache();
		feeder = setUpDataFeeder();
	}

	@After
	public void after() {
		cache = null;
	}

}
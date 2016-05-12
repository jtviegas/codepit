package org.aprestos.labs.tests.caching.common;

import org.apache.log4j.PropertyConfigurator;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.junit.After;
import org.junit.Before;

public abstract class CacheTestAbstract {

	protected ICache<String, String> cache;

	

	protected DataFeeder<State> feeder;
	protected static final String key = "key";
	protected static final String value = "value";

	public CacheTestAbstract() {
		PropertyConfigurator.configure("log4j.properties");
	}

	protected abstract ICache<String, String> setUpCache();

	protected abstract DataFeeder<State> setUpDataFeeder() throws Exception;
	
	public abstract void testLoadOfKeyValues() throws Exception;
	

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
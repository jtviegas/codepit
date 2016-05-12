package org.aprestos.labs.tests.caching.engines.derby;

import java.io.File;

import org.aprestos.labs.tests.caching.common.MemoryCacheTestAbstract;
import org.aprestos.labs.tests.caching.engines.derby.conf.LocalDerbyConfig;
import org.aprestos.labs.tests.caching.interfaces.DataFeeder;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.DataFeederImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class DerbyFileSourceIntegrationTest extends MemoryCacheTestAbstract{

	private static final String TMP_DIR = "tmp/";
	private static final String DATA_FILE = TMP_DIR + "data.csv";
	private ApplicationContext context;

	public DerbyFileSourceIntegrationTest(){
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
		return new DataFeederImpl(new File(DATA_FILE));
	}

	
	
}

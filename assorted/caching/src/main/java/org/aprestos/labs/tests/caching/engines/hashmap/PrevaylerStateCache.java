package org.aprestos.labs.tests.caching.engines.hashmap;

import java.io.IOException;

import org.aprestos.labs.tests.caching.engines.hashmap.prevayler.CreateStateTrx;
import org.aprestos.labs.tests.caching.engines.hashmap.prevayler.DeleteAllStatesTrx;
import org.aprestos.labs.tests.caching.engines.hashmap.prevayler.DeleteStateTrx;
import org.aprestos.labs.tests.caching.engines.hashmap.prevayler.GetState;
import org.aprestos.labs.tests.caching.engines.hashmap.prevayler.StateSink;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("prevaylerStateCache")
@Scope("prototype")
public class PrevaylerStateCache implements ICache<String, String> {

	private static final String DEFAULT_PERSISTING_FOLDER = "/tmp";
	private Prevayler<StateSink> prevayler;
	
	public PrevaylerStateCache(String persistingFolder) throws Exception {
		prevayler = PrevaylerFactory.createPrevayler(new StateSink(), persistingFolder);
	}
	
	public PrevaylerStateCache() throws Exception {
		prevayler = PrevaylerFactory.createPrevayler(new StateSink(), DEFAULT_PERSISTING_FOLDER);
	}

	public void clear() throws CacheException {
		prevayler.execute(new DeleteAllStatesTrx());
	}

	public void put(String key, String value) throws CacheException {
		try {
			State state = prevayler.execute(new CreateStateTrx(key));
			state.setValue(value);
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	public String get(String key) throws CacheException {
		State state = null;
		String result = null;
		
			try {
				if(null != (state = prevayler.execute(new GetState(key)))){
					result = state.getValue();
				}
			} catch (Exception e) {
				throw new CacheException(e);
			}
		
		return result;
	}

	public void remove(String key) throws CacheException {
		try {
			prevayler.execute(new DeleteStateTrx(key));
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}

	public void shutdown() throws CacheException {
		try {
			prevayler.takeSnapshot();
			prevayler.close();
		} catch (Exception e) {
			throw new CacheException(e);
		}
	}



}

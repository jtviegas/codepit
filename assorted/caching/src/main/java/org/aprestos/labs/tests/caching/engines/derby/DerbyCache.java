package org.aprestos.labs.tests.caching.engines.derby;

import org.apache.commons.lang.NotImplementedException;
import org.aprestos.labs.tests.caching.engines.derby.repositories.StateRepository;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("derbyCache")
public class DerbyCache implements ICache<String, String> {

	@Autowired
	private StateRepository repository;
	
	public DerbyCache() {}

	public void clear() {
		repository.deleteAll();
	}

	public void put(String key, String value) throws CacheException {
		repository.save(new State(key,value));
	}

	public String get(String key) {
		State state = null;
		String result = null;
		if( null != (state = repository.findByKey(key))){
			result = state.getValue();
		}
		return result;
	}

	public void remove(String key) throws CacheException {
		State state = null;
		if( null != (state = repository.findByKey(key))){
			repository.delete(state.getId());
		}
	}

	public void shutdown() {
		throw new NotImplementedException();
	}

}

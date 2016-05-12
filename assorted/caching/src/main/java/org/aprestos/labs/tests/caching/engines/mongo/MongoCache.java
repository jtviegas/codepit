package org.aprestos.labs.tests.caching.engines.mongo;

import org.apache.commons.lang.NotImplementedException;
import org.aprestos.labs.tests.caching.engines.mongo.repositories.StateMongoRepository;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.MongoState;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mongoCache")
public class MongoCache implements ICache<String, String> {

	@Autowired
	private StateMongoRepository repository;
	
	public MongoCache() {}

	public void clear() {
		repository.deleteAll();
	}

	public void put(String key, String value) throws CacheException {
		repository.save(new MongoState(key, value));
	}

	public String get(String key) {
		MongoState state = null;
		String result = null;
		if(null != (state = repository.findByKey(key)))
			result = state.getValue();
		
		return result;
	}

	public void remove(String key) throws CacheException {
		MongoState state = null;
		if(null != (state = repository.findByKey(key)))
			repository.delete(state);
	}

	public void shutdown() {
		throw new NotImplementedException();
	}

}

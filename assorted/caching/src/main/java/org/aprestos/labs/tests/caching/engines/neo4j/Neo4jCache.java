package org.aprestos.labs.tests.caching.engines.neo4j;

import org.apache.commons.lang.NotImplementedException;
import org.aprestos.labs.tests.caching.engines.neo4j.repositories.Neo4jStateRepository;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.model.Neo4jState;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("neo4jCache")
public class Neo4jCache implements ICache<String, String> {
	
	@Autowired
	private Neo4jStateRepository repository;

	public Neo4jCache() {}

	public void clear() throws CacheException {
		repository.deleteAll();
	}

	public void put(String key, String value) throws CacheException {
		repository.save(new Neo4jState(key,value));
	}

	public String get(String key) {
		Neo4jState state = null;
		String result = null;
		if(null != (state = repository.findByKey(key))){
			result = state.getValue();
		}
		return result;
	}

	public void remove(String key) throws CacheException {
		Neo4jState state = null;
		if(null != (state = repository.findByKey(key)))
			repository.delete(state);
	}

	public void shutdown() {
		throw new NotImplementedException();
	}

	

}

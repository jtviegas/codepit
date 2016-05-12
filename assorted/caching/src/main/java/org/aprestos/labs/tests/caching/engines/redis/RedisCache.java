package org.aprestos.labs.tests.caching.engines.redis;

import javax.inject.Inject;

import org.aprestos.labs.tests.caching.engines.redis.repositories.KeyValueService;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.springframework.stereotype.Component;

@Component("redisCache")
public class RedisCache implements ICache<String, String> {

	
	@Inject
	private KeyValueService service;
	
	public RedisCache() { }

	public void clear() {
		service.clear();
	}

	public void put(String key, String value) throws CacheException {
		service.put(key,value);
	}

	public String get(String key) {
		return service.get(key);
	}

	public void remove(String key) throws CacheException {
		service.remove(key);
	}

	public void shutdown() {
		service.shutdown();
	}

}

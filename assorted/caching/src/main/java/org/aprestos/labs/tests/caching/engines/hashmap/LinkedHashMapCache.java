package org.aprestos.labs.tests.caching.engines.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.springframework.stereotype.Component;

@Component("linkedHashmapCache")
public class LinkedHashMapCache implements ICache<String, String> {

	private Map<String,String> cache;
	
	public LinkedHashMapCache() {
		cache = new LinkedHashMap<String,String>();
	}

	public void clear() throws CacheException {
		cache.clear();
	}

	public void put(String key, String value) throws CacheException {
		cache.put(key, value);
	}

	public String get(String key) {
		return cache.get(key);
	}

	public void remove(String key) throws CacheException {
		cache.remove(key);
	}

	public void shutdown() {
		throw new NotImplementedException();
	}

}

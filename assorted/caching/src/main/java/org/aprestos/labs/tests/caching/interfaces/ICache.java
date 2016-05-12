package org.aprestos.labs.tests.caching.interfaces;

import org.aprestos.labs.tests.caching.utils.CacheException;

public interface ICache<E,T> {

	//void reload() throws CacheException;
	void clear() throws CacheException;
	void put(E key, T value) throws CacheException;
	T get(E key) throws CacheException;
	void remove(E key) throws CacheException;
	void shutdown() throws CacheException;
}

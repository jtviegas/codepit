package org.aprestos.labs.tests.caching.interfaces;

import java.util.List;

import org.aprestos.labs.tests.caching.utils.CacheException;

public interface IGraphCache<E, T>  extends ICache<E, T> {

	List<T> getChildren(E key) throws CacheException;
}

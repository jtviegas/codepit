package org.aprestos.labs.tests.caching.engines.jcs;

import org.apache.commons.lang.StringUtils;
import org.apache.jcs.JCS;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.aprestos.labs.tests.caching.interfaces.ICache;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.springframework.stereotype.Component;

@Component("jcsMemoryAndDiskCache")
public class JcsMemoryAndDiskCache implements ICache<String, String> {

	private static final String REGION = "default";
	private JCS cache;
	
	public JcsMemoryAndDiskCache() throws org.apache.jcs.access.exception.CacheException {
		String path = "/" + StringUtils.join(
				this.getClass().getPackage().getName().split("\\."), 
				"/");
		String fileName = this.getClass().getSimpleName() + ".ccf";
		String filePath = path + "/" + fileName;
		//String filePath = "/" + fileName;
		
		JCS.setConfigFilename(filePath);
		cache = JCS.getInstance(REGION);

	}
	

	public void clear() throws CacheException {
		try {
			cache.clear();
		} catch (org.apache.jcs.access.exception.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void put(String key, String value) throws CacheException {
		try {
			cache.put(key, value);
		} catch (org.apache.jcs.access.exception.CacheException e) {
			throw new CacheException(e);
		}
	}

	public String get(String key) {
		return (String)cache.get(key);
	}

	public void remove(String key) throws CacheException {
		try {
			cache.remove(key);
		} catch (org.apache.jcs.access.exception.CacheException e) {
			throw new CacheException(e);
		}
	}

	public void shutdown() {
		CompositeCacheManager.getInstance().shutDown();
	}

}

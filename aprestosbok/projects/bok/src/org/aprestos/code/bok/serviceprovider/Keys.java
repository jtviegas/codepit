/**
 * Keys.java
 * copyright aprestos.org, 2008.
 */
package org.aprestos.code.bok.serviceprovider;

/**
 * 
 */
public enum Keys
{

    	BUNDLES_DIR("bundles.dir"), 
	BUNDLES_CACHE_DIR("bundles.cache.dir"),
	OSGi_LOG_LEVEL("osgi.log.level");
	
	private String key;
	
	Keys(String key)
	{
		this.key = key;
	}
	
	public String getKey()
	{
		return this.key;
	}
    
}

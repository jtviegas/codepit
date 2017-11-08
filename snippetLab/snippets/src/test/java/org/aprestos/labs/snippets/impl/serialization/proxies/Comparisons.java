package org.aprestos.labs.snippets.impl.serialization.proxies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;

public class Comparisons {

    private List<Map<String, String>> createConfigs(int n) {
	
	List<Map<String, String>> configs = new ArrayList<Map<String, String>>();
	
	for(int i=0; i<n; i++){
	    Map<String, String> c1 = new HashMap<String, String>();
		c1.put(RandomStringUtils.random(5), RandomStringUtils.random(5));
		c1.put(RandomStringUtils.random(5), RandomStringUtils.random(5));
		c1.put(RandomStringUtils.random(5), RandomStringUtils.random(5));
		configs.add(c1);
	}
	return configs;
    }
    
    private Set<ToBePersisted> getObjs(int n, int nConfigs) {
	Set<ToBePersisted> result = new HashSet<ToBePersisted>();
	
	List<Map<String, String>> configs = createConfigs(nConfigs);
	Random r = new Random();
	for(int i=0; i<n; i++)
	    result.add(new ToBePersisted(RandomStringUtils.random(5), r.nextInt(), r.nextBoolean(), configs.get(i%configs.size())));
	    
	
	return result;
    }
    
    private Set<ToBePersistedOptimized> getOptimizedObjs(int n, int nConfigs) {
	Set<ToBePersistedOptimized> result = new HashSet<ToBePersistedOptimized>();
	
	List<Map<String, String>> configs = createConfigs(nConfigs);
	Random r = new Random();
	for(int i=0; i<n; i++)
	    result.add(new ToBePersistedOptimized(RandomStringUtils.random(5), r.nextInt(), r.nextBoolean(), configs.get(i%configs.size())));
	    
	
	return result;
    }


}

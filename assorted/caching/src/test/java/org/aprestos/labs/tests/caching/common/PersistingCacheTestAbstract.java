package org.aprestos.labs.tests.caching.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.aprestos.labs.tests.caching.model.State;
import org.aprestos.labs.tests.caching.utils.CacheException;
import org.junit.Assert;
import org.junit.Test;


public abstract class PersistingCacheTestAbstract extends CacheTestAbstract {

	public PersistingCacheTestAbstract() {
		super();
	}


	@Override
	@Test
	public void testLoadOfKeyValues() throws Exception {
		
		int flagVal = 3;
		State state = null;
		boolean takeSample = false;
		int added = 0, retrieved = 0, unretrieved = 0;
		long startAddTS = 0, finishAddStartRetrieveTS = 0, startRetrieveTS=0, endRetrieveTS = 0, startLoadingCacheTS = 0;
		List<State> samples = null;
		
		try {
			Random r = new Random();
			samples = new ArrayList<State>();
			
			startAddTS = System.currentTimeMillis();
			
			while(feeder.hasNext()){
				state = feeder.next();
				takeSample  = ((r.nextInt(8)%flagVal) == 2);
				if(takeSample)
					samples.add(state);
				
				cache.put(state.getKey(), state.getValue());
				added++;
			}
			
			finishAddStartRetrieveTS = System.currentTimeMillis();
			
			cache.shutdown();
			//reload a new cache instance
			startLoadingCacheTS = System.currentTimeMillis();
			this.cache = setUpCache();
			startRetrieveTS = System.currentTimeMillis();
			
			for(State s: samples){
				if(null != cache.get(s.getKey()))
					retrieved++;
				else
					unretrieved++;
			}
			
			endRetrieveTS = System.currentTimeMillis();

			System.out.println(String.format("elements: added %d | retrieved %d | missed to retrieve %d", added, retrieved, unretrieved));
			System.out.println(String.format("adding all data took %d seconds and retrieving took %d seconds", 
					(finishAddStartRetrieveTS - startAddTS)/1000, (endRetrieveTS - startRetrieveTS)/1000));
			System.out.println(String.format("adding all data took %d milliseconds and retrieving took %d milliseconds", 
					(finishAddStartRetrieveTS - startAddTS), (endRetrieveTS - startRetrieveTS)));
			System.out.println(String.format("cache reloading took %d milliseconds",(startRetrieveTS - startLoadingCacheTS)));

			Assert.assertEquals(samples.size(), retrieved);
			
		} catch (CacheException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}

	}

}

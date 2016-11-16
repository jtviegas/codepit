package org.aprestos.labs.snippets.impl.lang.collections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class Comparisons {


	@Test
	public void mapComparisons_one() {
		
	    	Map<String, String> c1 = new HashMap<String, String>();
	    	c1.put("a", "1");
	    	c1.put("b", "2");
	    	c1.put("c", "3");
	    	c1.put("d", "4");
	    	
	    	Map<String, String> c2 = new HashMap<String, String>();
	    	c2.put("a", "1");
	    	c2.put("b", "2");
	    	c2.put("c", "3");
	    	c2.put("d", "4");
	    	
	    	 Assert.assertTrue(c2.equals(c1));
	    	 
	    	Map<String, String> c3 = new HashMap<String, String>();
	    	c3.put("c", "3");
	    	c3.put("d", "4");
	    	c3.put("a", "1");
	    	c3.put("b", "2");
	    	
	    	
	    	 Assert.assertTrue(c3.equals(c1));
	    	 
	    	 Assert.assertTrue(Arrays.equals(c3.entrySet().toArray(), c1.entrySet().toArray()));
	    	 
		
		
	}

}

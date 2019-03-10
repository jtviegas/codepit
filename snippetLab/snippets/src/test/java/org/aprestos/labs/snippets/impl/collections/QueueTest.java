package org.aprestos.labs.snippets.impl.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class QueueTest {


	@Test
	public void simpleTest() throws Exception {
	    
	    Queue<String> o = new LinkedList<>();
	    
	    o.add("a"); 
	    o.add("b"); 
	    o.add("c");
	    
	    Assert.assertTrue(o.poll().equals("a"));
	    Assert.assertTrue(o.poll().equals("b"));
	    Assert.assertTrue(o.poll().equals("c"));
	}
	
	@Test
	public void simpleTest2() throws Exception {
	    
	    Queue<String> o = new LinkedList<>();
	    
	    o.add("a"); 
	    o.add("b"); 
	    o.add("c");
	    
	    o.remove("b");
	    
	    Assert.assertTrue(o.poll().equals("a"));
	    Assert.assertTrue(o.poll().equals("c"));
	}
	
	@Test
	public void simpleTest3() throws Exception {
	    
	    Queue<String> o = new LinkedList<>();
	    
	    o.add("a"); 
	    o.add("b"); 
	    o.add("c");
	    
	    o.poll();
	    
	    Assert.assertTrue(o.poll().equals("b"));
	    Assert.assertTrue(o.poll().equals("c"));
	}


	
}

package org.aprestos.labs.challenges.treesandgraphs;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Example {

	@Test
	public void test() throws Exception {
	  
	  Tree a = new Tree(2);
	  Tree.insert(a, 1, null);
	  Tree.insert(a, 7, null);
	  Tree.insert(a, 4, null);
	  Tree.insert(a, 3, null);
	  Tree.insert(a, 8, null);
	  
	  Tree b = new Tree(2);
    Tree.insert(b, 1, null);
    Tree.insert(b, 4, null);
    Tree.insert(b, 3, null);
    Tree.insert(b, 8, null);
	  
    Tree.delete(a, 7);
	  
		Assert.assertEquals(a, b);
	}



}

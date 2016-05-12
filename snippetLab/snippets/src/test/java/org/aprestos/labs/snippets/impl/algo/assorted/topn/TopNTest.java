package org.aprestos.labs.snippets.impl.algo.assorted.topn;

import org.junit.Assert;
import org.junit.Test;

public class TopNTest {

	@Test
	public void testArrayA() throws Exception {

		int[] a = new int[]{2,7,5,2,5,11};
		LinkedList expected = new LinkedList(11);
		expected.add(7).add(5).add(2);
		Sorter o = new Sorter();
		LinkedList actual = o.heapsort(a);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testArrayC() throws Exception {

		int[] a = new int[]{2,7,5,2,5,11,9,23,4,6,1,45,45,23};
		LinkedList expected = new LinkedList(45);
		expected.add(23).add(11).add(9).add(7).add(6).add(5).add(4).add(2).add(1);;
		
		Sorter o = new Sorter();
		LinkedList actual = o.heapsort(a);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testTopN() throws Exception {

		LinkedList expected = new LinkedList(34);
		expected.add(33).add(22).add(11);
		LinkedList actual = new TopN().topN(4, "tmp/topn.data");
		Assert.assertEquals(expected, actual);
		
	}
	

}

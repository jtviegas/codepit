package org.aprestos.labs.challenges.treesandgraphs;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

	@Test
	public void testTree() throws Exception {

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

	@Test
	public void testGraph() throws Exception {
		int[][] s = new int[][] { { 0, 1 }, { 1, 2 }, { 1, 5 }, { 2, 5 }, { 2, 3 }, { 3, 4 }, { 5, 4 } };

		Graph g = new Graph();
		g.read(s, false);
		g.print();

	}

}

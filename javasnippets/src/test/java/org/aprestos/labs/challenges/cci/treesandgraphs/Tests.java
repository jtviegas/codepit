package org.aprestos.labs.challenges.cci.treesandgraphs;

import java.util.Arrays;

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
	public void testUndirectedGraph() throws Exception {
		int[][] s = new int[][] { { 0, 1 }, { 1, 2 }, { 1, 5 }, { 2, 5 }, { 2, 3 }, { 3, 4 }, { 5, 4 } };

		Graph g = Graph.read(s, 6, false);
		g.print();

		int[] parent = g.bfs(0);
		System.out.println(Arrays.toString(parent));

		Assert.assertArrayEquals(new int[] { 4, 5, 1, 0 }, g.find_path(0, 4, parent));
		Assert.assertArrayEquals(new int[] { 3, 2, 1 }, g.find_path(1, 3, parent));
	}

	@Test
	public void testDirectedGraph() throws Exception {
		int[][] s = new int[][] { { 0, 1 }, { 1, 2 }, { 1, 5 }, { 2, 5 }, { 2, 3 }, { 3, 4 }, { 5, 4 } };

		Graph g = Graph.read(s, 6, true);
		g.print();

		int[] parent = g.bfs(0);
		System.out.println(Arrays.toString(parent));

		Assert.assertArrayEquals(new int[] { 4, 5, 1, 0 }, g.find_path(0, 4, parent));
		Assert.assertArrayEquals(new int[] { 3, 2, 1 }, g.find_path(1, 3, parent));
		Assert.assertEquals(false, g.isTherePath(5, 3));

	}

	/*
	   0
	   |
	   1
	 /   \
	 2 -> 5
	 |    |
	 3 -> 4
	 */
	@Test
	public void testDirectedGraphDfs() throws Exception {
		int[][] s = new int[][] { { 0, 1 }, { 1, 2 }, { 1, 5 }, { 2, 5 }, { 2, 3 }, { 3, 4 }, { 5, 4 } };

		Graph g = Graph.read(s, 6, true);
		g.print();

		int[] parent = g.do_dfs(0);
		System.out.println(Arrays.toString(parent));

		Assert.assertArrayEquals(new int[] { 4, 5, 2, 1, 0 }, g.find_path(0, 4, parent));
		Assert.assertArrayEquals(new int[] { 3, 2, 1 }, g.find_path(1, 3, parent));
		Assert.assertEquals(false, g.isTherePath(5, 3));

	}

}

package org.aprestos.labs.challenges.treesandgraphs;

import java.util.Arrays;
import java.util.Queue;

import com.google.common.collect.Queues;

public class Graph {
	/*
	 * private static final int MAX_VERTEX_NUMBER = 1000;
	 */

	EdgeNode[] edges = new EdgeNode[] {};
	int[] degree = new int[] {};
	int nVertices;
	int nEdges;
	boolean directed;

	private void init(boolean directed) {
		this.directed = directed;
	}

	private int[] addToArray(int o, int index, int[] a) {
		int[] r = null;
		if (index >= a.length)
			r = expandArray(a, index - a.length + 1);

		r[index] = o;

		return r;

	}

	private <T> T[] addToObjArray(T o, int index, T[] a) {
		T[] r = null;
		if (index >= a.length)
			r = expandObjArray(a, index - a.length + 1);

		r[index] = o;

		return r;

	}

	private int[] expandArray(int[] a, int n) {
		int[] r = new int[a.length + 1];
		System.arraycopy(a, 0, r, 0, a.length + n);
		return r;
	}

	private <T> T[] expandObjArray(T[] a, int n) {
		@SuppressWarnings("unchecked")
		T[] r = (T[]) new Object[a.length + 1];
		System.arraycopy(a, 0, r, 0, a.length + n);
		return r;
	}

	void read(int[][] def, boolean directed) {

		init(directed);

		for (int i = 0; i < def.length; i++)
			insert(def[i][0], def[i][1], directed);

	}

	void insert(int x, int y, boolean directed) {

		// create a new edgenode and attach the other ones beneath
		EdgeNode node = new EdgeNode(y, 0, edges[x]);

		if (null == edges[x])
			nVertices++;

		// put itself on top of the linked list
		edges = addToObjArray(node, x, edges);
		if (x >= degree.length)
			degree = addToArray(1, x, degree);
		else
			degree[x]++;

		if (!directed)
			insert(y, x, true);
		else
			nEdges++;

	}

	void print() {

		for (int i = 0; i < nVertices; i++) {
			System.out.print(String.format("%d: ", i));
			EdgeNode p = edges[i];
			while (null != p) {
				System.out.print(String.format(" %d", p.y));
				p = p.next;
			}
			System.out.print(System.getProperty("line.separator"));
		}

	}

	void process_vertex_start(int v) {

	}

	void process_vertex_end(int v) {
		System.out.println(String.format("procesed vertex %d", v));
	}

	void process_edge(int x, int y) {
		System.out.println(String.format("processed edge %d<=>%d", x, y));
	}

	int[] find_path(int start, int end, int[] parent) {
		int count = 0;
		int[] r = new int[MAX_VERTEX_NUMBER + 1];
		int v = end;

		r[count++] = v;
		while (-1 < parent[v])
			r[count++] = (v = parent[v]);

		int[] o = new int[count];
		System.arraycopy(r, 0, o, 0, count);
		return o;
	}

	int[] bfs(int start) {

		Queue<Integer> queue = Queues.newArrayDeque();
		int x, y;
		boolean[] discovered = new boolean[MAX_VERTEX_NUMBER + 1];
		boolean[] processed = new boolean[MAX_VERTEX_NUMBER + 1];
		int[] parent = new int[MAX_VERTEX_NUMBER + 1];
		Arrays.fill(parent, -1);

		queue.add(start);
		discovered[start] = true;

		while (!queue.isEmpty()) {
			x = queue.poll();
			process_vertex_start(x);
			processed[x] = true;

			EdgeNode p = edges[x];
			while (null != p) {
				y = p.y;

				if (!processed[y])
					process_edge(x, y);

				if (!discovered[y]) {
					discovered[y] = true;
					parent[y] = x;
					queue.add(y);
				}

				/*
				 * if( !processed[y] || directed ) discovered[y] = true;
				 */

				p = p.next;
			}
			processed[x] = true;
			process_vertex_end(x);
		}

		return parent;
	}

}

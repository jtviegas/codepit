package org.aprestos.labs.challenges.treesandgraphs;

import java.lang.reflect.Array;
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
		int[] r = a;
		if (index >= a.length)
			r = expandArray(a, index - a.length + 1);

		r[index] = o;

		return r;

	}
	
	private boolean[] addToArray(boolean o, int index, boolean[] a) {
    boolean[] r = a;
    if (index >= a.length)
      r = expandArray(a, index - a.length + 1);

    r[index] = o;

    return r;

  }

	private <T> T[] addToObjArray(Class<?> t, T o, int index, T[] a) {
		T[] r = a;
		if (index >= a.length)
			r = expandObjArray(t, a, index - a.length + 1);

		r[index] = o;

		return r;
	}

	private int[] expandArray(int[] a, int n) {
		int[] r = new int[a.length + n];
		System.arraycopy(a, 0, r, 0, a.length);
		return r;
	}
	
	private boolean[] expandArray(boolean[] a, int n) {
    boolean[] r = new boolean[a.length + n];
    System.arraycopy(a, 0, r, 0, a.length);
    return r;
  }

	private <T> T[] expandObjArray(Class<?> t, T[] a, int n) {
		@SuppressWarnings("unchecked")
		
		T[] r = (T[]) Array.newInstance(t, a.length + n);
		System.arraycopy(a, 0, r, 0, a.length);
		return r;
	}

	void read(int[][] def, boolean directed) {

		init(directed);

		for (int i = 0; i < def.length; i++)
			insert(def[i][0], def[i][1], directed);

	}

	void insert(int x, int y, boolean directed) {

		// create a new edgenode and attach the other ones beneath
		EdgeNode node = new EdgeNode(y, 0, edges.length > x ? edges[x] : null );

		if (edges.length > x && null == edges[x])
			nVertices++;

		// put itself on top of the linked list
		edges = addToObjArray(EdgeNode.class, node, x, edges);
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
		int[] r = new int[] {};
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
		boolean[] discovered = new boolean[] {};
		boolean[] processed = new boolean[] {};
		int[] parent = new int[] {};

		queue.add(start);
		parent = addToArray(-1, start, parent);
		discovered = addToArray(true, start, discovered);

		while (!queue.isEmpty()) {
			x = queue.poll();
			process_vertex_start(x);

			EdgeNode p = edges[x];
			while (null != p) {
				y = p.y;

				if (!processed[y])
					process_edge(x, y);

				if (!discovered[y]) {
					discovered = addToArray(true, y, discovered);
					parent = addToArray(x, y, parent);
					queue.add(y);
				}

				/*
				 * if( !processed[y] || directed ) discovered[y] = true;
				 */

				p = p.next;
			}
			processed = addToArray(true, x, processed);
			process_vertex_end(x);
		}

		return parent;
	}

}

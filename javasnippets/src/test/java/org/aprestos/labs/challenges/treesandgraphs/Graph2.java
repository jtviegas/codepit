package org.aprestos.labs.challenges.treesandgraphs;

import java.lang.reflect.Array;
import java.util.Queue;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Queues;

public class Graph2 {

	EdgeNode[] edges = new EdgeNode[] {};
	int[] degree = new int[] {};
	int nVertices;
	int nEdges;
	boolean directed;

	private void init(boolean directed) {
		this.directed = directed;
	}

	private int[] setInArray(int o, int index, int[] a) {
		int[] r = a;
		if (index >= a.length)
			r = expandArray(a, index - a.length + 1);

		r[index] = o;

		return r;

	}

	private boolean[] setInArray(boolean o, int index, boolean[] a) {
		boolean[] r = a;
		if (index >= a.length)
			r = expandArray(a, index - a.length + 1);

		r[index] = o;

		return r;

	}

	private <T> T[] setInArray(Class<?> t, T o, int index, T[] a) {
		T[] r = a;
		if (index >= a.length)
			r = expandArray(t, a, index - a.length + 1);

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

	private <T> T[] expandArray(Class<?> t, T[] a, int n) {
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

		EdgeNode adjacentEdgeNode = null;
		if (edges.length - 1 >= x && null != edges[x])
			adjacentEdgeNode = edges[x];
		else
			nVertices++;

		// create a new edgenode and attach the other ones beneath
		EdgeNode node = new EdgeNode(y, 0, adjacentEdgeNode);

		// put itself on top of the linked list
		edges = setInArray(EdgeNode.class, node, x, edges);

		if (degree.length - 1 < x)
			degree = setInArray(1, x, degree);
		else
			degree[x]++;

		if (!directed)
			insert(y, x, true);
		else
			nEdges++;

	}

	void print() {
		System.out.println(String.format("edges: %d", nEdges));
		System.out.println(String.format("vertexes: %d", nVertices));
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
		parent = setInArray(-1, start, parent);
		discovered = setInArray(true, start, discovered);

		while (!queue.isEmpty()) {
			x = queue.poll();
			process_vertex_start(x);

			EdgeNode p = edges[x];
			while (null != p) {
				y = p.y;

				if (!processed[y])
					process_edge(x, y);

				if (!discovered[y]) {
					discovered = setInArray(true, y, discovered);
					parent = setInArray(x, y, parent);
					queue.add(y);
				}

				/*
				 * if( !processed[y] || directed ) discovered[y] = true;
				 */

				p = p.next;
			}
			processed = setInArray(true, x, processed);
			process_vertex_end(x);
		}

		return parent;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

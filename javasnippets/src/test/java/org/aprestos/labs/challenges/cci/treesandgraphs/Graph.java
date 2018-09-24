package org.aprestos.labs.challenges.cci.treesandgraphs;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Queues;

public class Graph {

	List<Integer>[] edges;
	boolean directed;
	int numEdge;
	// helping hands
	boolean[] discovered;
	boolean[] processed;
	int[] parent;

	@SuppressWarnings("unchecked")
	Graph(int n, boolean directed) {
		this.directed = directed;
		this.edges = (List<Integer>[]) Array.newInstance(List.class, n);
	}

	static Graph read(int[][] def, int n, boolean directed) {

		Graph r = new Graph(n, directed);

		for (int i = 0; i < def.length; i++)
			r.insert(def[i][0], def[i][1], directed);

		return r;
	}

	void insert(int x, int y, boolean directed) {

		List<Integer> al = null;
		if (null == (al = edges[x])) {
			al = new LinkedList<Integer>();
			edges[x] = al;
		}

		al.add(y);
		numEdge++;
		if (!directed)
			insert(y, x, true);

	}

	boolean isThereEdge(int x, int y) {
		boolean r = false;

		List<Integer> al = null;
		if (null != (al = edges[x])) {
			al = new LinkedList<Integer>();
			r = al.contains(y);
		}

		return r;
	}

	int[] findSuccessors(int x) {
		int[] r = new int[0];
		List<Integer> al = null;
		if (null != (al = edges[x])) {
			al = new LinkedList<Integer>();
			r = new int[al.size()];
			int index = 0;
			for (Integer i : al)
				r[index++] = i;
		}

		return r;
	}

	void remove(int x, int y) {

		List<Integer> al = null;
		if (null != (al = edges[x])) {
			al = new LinkedList<Integer>();
			al.remove(y);
			if (!directed)
				remove(y, x);
			numEdge--;
		}

	}

	boolean isTherePath(int x, int y) {
		return 0 < find_path(x, y, bfs(0)).length;
	}

	void print() {
		System.out.println(String.format("vertex: %d", edges.length));
		for (int i = 0; i < edges.length; i++) {
			System.out.print(String.format("%d: ", i));
			List<Integer> al = null;
			if (null != (al = edges[i]))
				for (Integer edge : al)
					System.out.print(String.format(" %d", edge));
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
		List<Integer> r = new LinkedList<Integer>();
		int v = end;
		boolean found = false;

		r.add(v);
		while (-1 < parent[v]) {
			v = parent[v];
			r.add(v);
			if (v == start) {
				found = true;
				break;
			}
		}

		int[] o = null;
		if (found) {
			o = new int[r.size()];

			for (int i = 0; i < r.size(); i++)
				o[i] = r.get(i);
		} else
			o = new int[0];

		return o;
	}

	int[] bfs(int start) {

		Queue<Integer> queue = Queues.newArrayDeque();
		discovered = new boolean[edges.length];
		processed = new boolean[edges.length];
		parent = new int[edges.length];

		int x;

		queue.add(start);
		parent[start] = -1;
		discovered[start] = true;

		while (!queue.isEmpty()) {
			x = queue.poll();
			process_vertex_start(x);

			List<Integer> al = null;
			if (null != (al = edges[x]))
				for (Integer y : al) {
					if (!processed[y])
						process_edge(x, y);

					if (!discovered[y]) {
						discovered[y] = true;
						parent[y] = x;
						queue.add(y);
					}

				}
			processed[x] = true;
			process_vertex_end(x);
		}

		return parent;
	}

	int[] do_dfs(int start) {

		Stack<Integer> st = new Stack<Integer>();
		discovered = new boolean[edges.length];
		processed = new boolean[edges.length];
		parent = new int[edges.length];

		discovered[start] = true;
		parent[start] = -1;
		st.add(start);
		dfs(st);

		return parent;
	}

	void dfs(Stack<Integer> st) {

		Integer o = st.pop();
		System.out.println(String.format("dfs'ing %d", o));
		List<Integer> al = null;
		if (null != (al = edges[o])) {
			for (Integer e : al) {
				if (!discovered[e]) {
					discovered[e] = true;
					st.add(e);
					parent[e] = o;
					dfs(st);
				}
			}
		}
		processed[o] = true;

	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

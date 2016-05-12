package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures;

public interface UnionFindAlgo {

	/**
	 * check if connected
	 * @param a
	 * @param b
	 * @return
	 */
	public abstract boolean connected(int a, int b);

	/**
	 * connect a and b
	 * @param a
	 * @param b
	 */
	public abstract void union(int a, int b);

}
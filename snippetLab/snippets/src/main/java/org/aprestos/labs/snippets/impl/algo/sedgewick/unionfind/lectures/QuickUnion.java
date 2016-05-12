package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures;

import java.util.Arrays;


public class QuickUnion implements UnionFindAlgo{
	
	private int[] id;
	
	
	public QuickUnion(int n){
		this.id = new int[n];
		for(int i = 0 ; i < this.id.length ; i++ )
			this.id[i] = i;
	}
	
	/* (non-Javadoc)
	 * @see org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures.UnionFindAlgo#connected(int, int)
	 */
	public boolean connected(int a, int b){
		return root(a) == root(b);
	}
	
	/* (non-Javadoc)
	 * @see org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures.UnionFindAlgo#union(int, int)
	 */
	public void union (int a, int b){
		int rootA = root(a);
		this.id[rootA] = root(b);
	}
	
	public int root(int n){
		while(n != this.id[n])
			n = this.id[n];

		return n;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append(System.lineSeparator() + "-----------------------------------"  + System.lineSeparator());
		buf.append("QuickUnion"  + System.lineSeparator());
		buf.append("roots: "  + System.lineSeparator());
		buf.append(Arrays.toString(id) + System.lineSeparator());
		return buf.toString();
	}

}

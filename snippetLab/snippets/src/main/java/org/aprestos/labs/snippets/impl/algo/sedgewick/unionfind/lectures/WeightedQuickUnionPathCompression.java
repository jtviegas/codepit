package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures;

import java.util.Arrays;


public class WeightedQuickUnionPathCompression  implements UnionFindAlgo{
	
	private int[] id;
	private int[] rs;
	
	
	
	public WeightedQuickUnionPathCompression(int n){
		this.id = new int[n];
		this.rs = new int[n];
	
		for(int i = 0 ; i < this.id.length ; i++ ){
			this.id[i] = i;
			this.rs[i] = 1;
		}
	}
	
	
	/**
	 * check if connected
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean connected(int a, int b){
		return root(a) == root(b);
	}
	
	/**
	 * connect a and b
	 * @param a
	 * @param b
	 */
	public void union (int a, int b){
		
		int rootA = root(a);
		int rootB = root(b);
		
		if(rs[a] < rs [b]){
			this.id[rootB] = rootA;
			rs[a] += rs[b];
		}
		else {
			this.id[rootA] = rootB;
			rs[b] += rs[a];
		}
		
	
	}
	
	int root(int n){
		while(n != this.id[n]){
			n = this.id[n];
			this.id[n] = this.id[this.id[n]];//if not root force it to point to parent root
		}

		return n;
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer();
		buf.append(System.lineSeparator() + "-----------------------------------"  + System.lineSeparator());
		buf.append("WeightedQuickUnionPathCompression"  + System.lineSeparator());
		buf.append("sizes: "  + System.lineSeparator());
		buf.append(Arrays.toString(rs) + System.lineSeparator());
		buf.append("roots: "  + System.lineSeparator());
		buf.append(Arrays.toString(id) + System.lineSeparator());
		return buf.toString();
	}

}

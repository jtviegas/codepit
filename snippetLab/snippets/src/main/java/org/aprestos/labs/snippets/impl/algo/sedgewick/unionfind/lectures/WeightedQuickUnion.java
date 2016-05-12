package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures;


class WeightedQuickUnion{
	
	private int[] id;
	private int[] rs;
	
	
	public WeightedQuickUnion(int n){
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
	boolean connected(int a, int b){
		return root(a) == root(b);
	}
	
	/**
	 * connect a and b
	 * @param a
	 * @param b
	 */
	void union (int a, int b){
		
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
		while(n != this.id[n])
			n = this.id[n];

		return n;
	}

}

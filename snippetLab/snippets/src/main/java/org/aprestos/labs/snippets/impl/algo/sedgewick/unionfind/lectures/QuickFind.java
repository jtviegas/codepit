package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures;


class QuickFind{
	
	private int[] id;
	
	
	public QuickFind(int n){
		this.id = new int[n];
		for(int i = 0 ; i < this.id.length ; i++ )
			this.id[i] = i;
	}
	
	/**
	 * check if connected
	 * @param a
	 * @param b
	 * @return
	 */
	boolean connected(int a, int b){
		return this.id[a] == this.id[b];
	}
	
	/**
	 * connect a and b
	 * @param a
	 * @param b
	 */
	void union (int a, int b){
		int aid = this.id[a];
		int bid = this.id[b];
		
		for(int i = 0 ; i < this.id.length ; i++ ){
			if(aid == this.id[i])
				this.id[i]=bid;
		}
	}


}

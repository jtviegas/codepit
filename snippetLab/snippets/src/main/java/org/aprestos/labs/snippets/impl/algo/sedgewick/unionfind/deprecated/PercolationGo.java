package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.deprecated;

import org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures.QuickUnion;
import org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures.UnionFindAlgo;
import org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures.WeightedQuickUnionPathCompression;

public class PercolationGo {
	
	public enum TYPE { WQUPC, QU, QF, QUPC, }
	
	public static PercolationGo getInstance(TYPE t, int N){
		PercolationGo o = null;
		int[] openSites = new int[1 + N*N + 1];
		//open top and bottom sites
		openSites[0] = 1;
		openSites[N*N + 1] = 1;
				
		switch(t){
		case WQUPC:
			o = new PercolationGo(N, new WeightedQuickUnionPathCompression(openSites.length), openSites);
			break;
		case QU:
			o = new PercolationGo(N, new QuickUnion(openSites.length), openSites);
			break;
		default:
			break;
		}

		
			
		return o;
	}
	
	int[] openSites;
	UnionFindAlgo algo;
	int n;
	
	private PercolationGo(int N, UnionFindAlgo o, int[] openSites){
		
		if(0 < N) 
			throw new IllegalArgumentException("size must be bigger than 0");
		
		this.n = N;
		this.openSites = openSites;
		
		//add the virtual sites to top and bottom
		this.algo = o;
		
		
	}
	
	public void open(int i, int j) {
		
		if(i >= n || j >= n) 
			throw new IndexOutOfBoundsException("indexes must be smaller than n provided");
		
		//translate client index to current array that has virtual sites
		int index = (i*n) + j + 1;
		if(1 == openSites[index])
			return;
		
		openSites[index]=1;
		
		if(isTopRow(index)){
			//connect it with the root/top virtual site
			this.algo.union(index, 0);
		}
		if(isBottomRow(index)){
			//connect it with the bottom virtual site
			this.algo.union(index, (n*n) + 1);
		}
		
		int row = (index -1)/n;
		//adjacent sites
		//left 
		int leftAdj = index -1;
		if( (1 == openSites[leftAdj] && (leftAdj-1)/n == row) )
			this.algo.union(index, leftAdj);
		
		//right 
		int rightAdj = index -1;
		if( (1 == openSites[rightAdj] && (rightAdj-1)/n == row) )
			this.algo.union(index, rightAdj);
		
		//top
		int topAdj = index - n;
		if( (topAdj > 0) && 1 == openSites[topAdj] )
			this.algo.union(index, topAdj);
		
		//bottom
		int bottomAdj = index + n;
		if( (bottomAdj < (n*n + 1)) && 1 == openSites[bottomAdj] )
			this.algo.union(index, bottomAdj);

	}

	public boolean isOpen(int i, int j) {
		if(i >= n || j >= n) 
			throw new IndexOutOfBoundsException("indexes must be smaller than n provided");
		
		// TODO Auto-generated method stub
		int index = (i*n) + j + 1;
		
		return (1 == openSites[index]);
	}

	public boolean isFull(int i, int j) {
		if(i >= n || j >= n) 
			throw new IndexOutOfBoundsException("indexes must be smaller than n provided");
		
		return algo.connected(0, (i*n) + j + 1);
	}

	public boolean percolates() {

		boolean result = algo.connected(0, n*n + 1);
		if(result){
			System.out.println(algo.toString());
		}
		return result;
	}
	
	private boolean isTopRow(int i){
		//client indexes were shifted right by one so
		return (i > 0 && i < (n+1));
	}
	
	private boolean isBottomRow(int i){
		return ( i > (n*(n-1)) && i < ((n*n) + 1) );
	}

}

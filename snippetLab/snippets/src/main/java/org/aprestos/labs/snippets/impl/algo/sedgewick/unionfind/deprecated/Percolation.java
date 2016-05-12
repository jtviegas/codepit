package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.deprecated;

import java.util.Random;

import org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures.QuickUnion;
import org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.lectures.UnionFindAlgo;

public class Percolation {
	
	
	public static void main(String[] args){
		
		Random r = new Random();
		int size = 3;
		Percolation o = new Percolation(size);
		
		
		
		int iteration = -1;
		while(! o.percolates() ){
			iteration++;
			o.open(r.nextInt(size), r.nextInt(size));
		}
		
		System.out.println("system percolated at iteration " + iteration);
	}
	
	
	
	private int[] openSites;
	private UnionFindAlgo algo;
	private int n;
	
	public Percolation(int N){
		
		if(1 > N) 
			throw new IllegalArgumentException("size must be bigger than 0");
		
		this.n = N;
		//add the virtual sites to top and bottom
		this.openSites = new int[1 + N*N + 1];
		
		//open top and bottom sites
		openSites[0] = 1;
		openSites[N*N + 1] = 1;

		this.algo = new QuickUnion(openSites.length);
		
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

package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.assignments;

import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//import edu.princeton.cs.algs4.QuickFindUF;

public class Percolation {

	private boolean[] openSites1;
	private boolean[] openSites2;
	// to avoid the backwash problem we'll maintain two algos
	// algo with 1 top virtual site to query for fullness
	private WeightedQuickUnionUF algo1;
	// algo with 1 top virtual site and one bottom virtual site to query for
	// percolation
	private WeightedQuickUnionUF algo2;

	private int n;
	private boolean percolates = false;

	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
	
		if (1 > N)
			throw new IllegalArgumentException("size must be bigger than 0");
		// grid dimension
		this.n = N;

		// add the virtual sites to top and bottom
		// of the grid
		this.openSites1 = new boolean[1 + N * N];
		this.openSites2 = new boolean[1 + N * N + 1];
		
		// open top and bottom sites
		openSites1[0] = true;
		openSites2[0] = true;
		openSites2[N*N + 1] = true;
		
		this.algo1 = new WeightedQuickUnionUF(openSites1.length);
		this.algo2 = new WeightedQuickUnionUF(openSites2.length);


	}

	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		// we are going to receive 1-based indexes
		// on rows and columns
		int x = i - 1;
		int y = j - 1;

		if (x >= n || y >= n || x < 0 || y < 0)
			throw new IndexOutOfBoundsException(
					"indexes must be smaller than N");

		// translate client index to current array that has virtual sites
		int index = (x * n) + y + 1;
		if (openSites1[index])
			return;

		openSites1[index] = true;
		openSites2[index] = true;
		
		if (isTopRow(index)) {
			// connect it with the root/top virtual site
			this.algo1.union(index, 0);
			this.algo2.union(index, 0);
		}
		else {
			// top adjacent
			int topAdj = index - n;
			if ((topAdj > 0) && openSites1[topAdj]){
				this.algo1.union(index, topAdj);
				this.algo2.union(index, topAdj);
			}
		}

		int row = (index - 1) / n;

		if (isBottomRow(index)) {
			// connect it with the bottom virtual site
			this.algo2.union(index, (this.n * this.n) + 1);
		} else {
			// bottom adjacent site
			int bottomAdj = index + n;
			if ((bottomAdj < (n * n + 1)) && openSites1[bottomAdj]) {
				this.algo1.union(index, bottomAdj);
				this.algo2.union(index, bottomAdj);
			}
		}

		// other adjacent sites
		// left
		int leftAdj = index - 1;
		if ( (leftAdj > 0) && (openSites1[leftAdj] && (leftAdj - 1) / n == row)){
			this.algo1.union(index, leftAdj);
			this.algo2.union(index, leftAdj);
		}
		
		// right
		int rightAdj = index + 1;
		if ((rightAdj <= (this.n * this.n))&& (openSites1[rightAdj] && (rightAdj - 1) / n == row)){
			this.algo1.union(index, rightAdj);
			this.algo2.union(index, rightAdj);
		}

	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		// we are going to receive 1-based indexes
		int x = i - 1;
		int y = j - 1;

		if (x >= n || y >= n || x < 0 || y < 0)
			throw new IndexOutOfBoundsException(
					"indexes must be smaller than N");

		// TODO Auto-generated method stub
		int index = (x * n) + y + 1;

		return (openSites1[index]);

	}

	// is site (row i, column j) full? connects to the top row?
	public boolean isFull(int i, int j) {

		// we are going to receive 1-based indexes
		int x = i - 1;
		int y = j - 1;

		if (x >= n || y >= n || x < 0 || y < 0)
			throw new IndexOutOfBoundsException(
					"indexes must be smaller than N");
		//in order to prevent teh backwash problem we'll use the
		//algo without the bottom row
		return algo1.connected(0, (x * n) + y + 1);

	}

	// does the system percolate?
	public boolean percolates() {

		if (!this.percolates) {
			//for this we use the algo with both 
			//virtual top and bottom sites 
			this.percolates = algo2.connected(0, (this.n * this.n) + 1);
		}
		return this.percolates;
	}

	private boolean isTopRow(int i) {
		// client indexes were shifted right by one so
		return (i > 0 && i < (n + 1));
	}

	private boolean isBottomRow(int i) {
		return (i > (n * (n - 1)) && i < ((n * n) + 1));
	}

	// test client (optional)
	public static void main(String[] args) {

		int size = 3;
		Percolation o = new Percolation(size);

		int iteration = -1;
		while (!o.percolates()) {
			iteration++;
			o.open(StdRandom.uniform(size) + 1, StdRandom.uniform(size) + 1);
		}

		//System.out.println("system percolated at iteration " + iteration);

	}
}

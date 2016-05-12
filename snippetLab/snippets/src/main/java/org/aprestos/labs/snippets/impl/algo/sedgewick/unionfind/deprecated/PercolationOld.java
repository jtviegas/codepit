package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.deprecated;

import edu.princeton.cs.algs4.StdRandom;
//import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import edu.princeton.cs.algs4.QuickFindUF;
  
public class PercolationOld {
  
  private int[] openSites;
  private WeightedQuickUnionUF algo;
  private int n;

  // create N-by-N grid, with all sites blocked
  public PercolationOld(int N) {
      if (1 > N) 
          throw new IllegalArgumentException("size must be bigger than 0");
      //grid dimension
      this.n = N;
      
      //add the virtual sites to top and bottom
      //of the grid
      this.openSites = new int[1 + N*N + 1];
      
      //open top and bottom sites
      openSites[0] = 1;
      openSites[N*N + 1] = 1;
      
      this.algo = new WeightedQuickUnionUF(openSites.length);
      
  } 
  // open site (row i, column j) if it is not open already
  public void open(int i, int j) {
      //we are going to receive 1-based indexes
	  //on rows and columns
	  int x = i-1;
      int y= j-1;
      
      if (x >= n || y >= n || x < 0 || y < 0) 
          throw new IndexOutOfBoundsException("indexes must be smaller than N");
      
      //translate client index to current array that has virtual sites
      int index = (x*n) + y + 1;
      if (1 == openSites[index])
          return;
      
      openSites[index] = 1;
      
      if (isTopRow(index)) {
          //connect it with the root/top virtual site
          this.algo.union(index, 0);
      }
      if (isBottomRow(index)) {
          //connect it with the bottom virtual site
          this.algo.union(index, (n*n) + 1);
      }
      
      int row = (index -1)/n;
      //adjacent sites
      //left 
      int leftAdj = index -1;
      if ((1 == openSites[leftAdj] && (leftAdj-1)/n == row))
          this.algo.union(index, leftAdj);
      
      //right 
      int rightAdj = index + 1;
      if ((1 == openSites[rightAdj] && (rightAdj-1)/n == row))
          this.algo.union(index, rightAdj);
      
      //top
      int topAdj = index - n;
      if ((topAdj > 0) && 1 == openSites[topAdj])
          this.algo.union(index, topAdj);
      
      //bottom
      int bottomAdj = index + n;
      if ((bottomAdj < (n*n + 1)) && 1 == openSites[bottomAdj])
          this.algo.union(index, bottomAdj);
      
  }
  
  // is site (row i, column j) open?
  public boolean isOpen(int i, int j) {
       //we are going to receive 1-based indexes
	  int x = i-1;
      int y= j-1;
      
        if (x >= n || y >= n || x < 0 || y < 0) 
            throw new IndexOutOfBoundsException("indexes must be smaller than N");
        
        // TODO Auto-generated method stub
        int index = (x*n) + y + 1;
        
        return (1 == openSites[index]);
      
  }
  
  // is site (row i, column j) full?
  public boolean isFull(int i, int j) {
	  
	//we are going to receive 1-based indexes
	  int x = i-1;
      int y= j-1;
      
        if (x >= n || y >= n || x < 0 || y < 0) 
            throw new IndexOutOfBoundsException("indexes must be smaller than N");
      
      return algo.connected(0, (x*n) + y + 1);

  } 
  
  // does the system percolate?
  public boolean percolates() {
  
      boolean result = algo.connected(0, n*n + 1);
      //if(result){
      //    System.out.println(algo.toString());
      //}
      return result;

  }       
  
  private boolean isTopRow(int i) {
      //client indexes were shifted right by one so
      return (i > 0 && i < (n+1));
  }
  
  private boolean isBottomRow(int i) {
      return (i > (n*(n-1)) && i < ((n*n) + 1));
  }

  // test client (optional)
  public static void main(String[] args) {
      
      int size = 3;
      PercolationOld o = new PercolationOld(size);
      
      int iteration = -1;
      while (!o.percolates()) {
          iteration++;
          o.open(StdRandom.uniform(size)+1, StdRandom.uniform(size)+1);
      }
      
      System.out.println("system percolated at iteration " + iteration);

  }
}
package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.deprecated;

import java.util.Random;

import org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.deprecated.PercolationGo.TYPE;

public class Assignment {
	
	int size = 2;
	PercolationGo o;
	Random r = new Random();
	
	public void wqupc() throws Exception {
		
		o = PercolationGo.getInstance(TYPE.WQUPC, size);
		
		int iteration = -1;
		while(! o.percolates() ){
			iteration++;
			int[] s = getUnblockedSite();
			o.open(s[0], s[1]);
		}
		
		System.out.println("system percolated at iteration " + iteration);
	}
	
	public void qu() throws Exception {
		
		o = PercolationGo.getInstance(TYPE.QU, size);
		
		int iteration = -1;
		while(! o.percolates() ){
			iteration++;
			int[] s = getUnblockedSite();
			o.open(s[0], s[1]);
		}
		
		System.out.println("system percolated at iteration " + iteration);
	}
	
	private int[] getUnblockedSite() {
		int[] result = new int[2];
		
		do{
			result[0] = r.nextInt(size);
			result[1] = r.nextInt(size);
		} while(o.isOpen(result[0], result[1]));
			
		
		return result;
	}

}

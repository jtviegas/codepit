package org.aprestos.labs.snippets.impl.algo.sedgewick.unionfind.assignments;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
//import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double xMean, xStdDev;
    private int t;
    
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        
        if (1 > N || 1 > T)
            throw new IllegalArgumentException("both arguments must be > 0");
        this.t = T;

        double[] openFractions = new double[T];
            
        Percolation o = null;
        for (int i = 0; i < T; i++) {
            o = new Percolation(N);
            //Stopwatch watch = new Stopwatch();
            int iteration = 0;
            while (!o.percolates()) {
            	int x = StdRandom.uniform(N)+1;
            	int y = StdRandom.uniform(N)+1;
            	if(!o.isOpen(x, y)){
            		iteration++;
                	o.open(x, y);
            	}
            }
            //System.out.println("system percolated at iteration " + iteration);
            //System.out.println("experiment took in seconds: " + watch.elapsedTime());
            openFractions[i] = (iteration*1.0)/(N*N*1.0);
        }
        
        this.xMean = StdStats.mean(openFractions);
        this.xStdDev = StdStats.stddev(openFractions);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return this.xMean;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.xStdDev;
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.xMean - 
                ((1.96 * this.xStdDev) / (Math.sqrt(this.t*1.0)));
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.xMean + 
                ((1.96 * this.xStdDev) / (Math.sqrt(this.t*1.0)));
    }

    public static void main(String[] args) {
        
        if (2 != args.length)
            throw new IllegalArgumentException("need to provide 2 argumens:" + 
             "dimension of percolation grid and number of experiments");
        
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);
        System.out.println(String.format(
              "mean                     = %f", ps.mean()));
        System.out.println(String.format(
              "stddev                   = %f", ps.stddev()));
        System.out.println(String.format(
              "95%% confidence interval = %f, %f", 
                   ps.confidenceLo(), ps.confidenceHi()));
        
            
    }
}

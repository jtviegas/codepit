package org.aprestos.labs.snippets.impl.algo.sedgewick.selection;

import edu.princeton.cs.algs4.StdRandom;

/**
 * given an array of N items, find the Kth largest
 * example: min(k=0), max(k=N-1). median(k=N/2)
 * @author joaovieg
 *
 */
public class Impl<T extends Comparable<T>> {

    protected T[] items;
    
    public T select(T[] items, int k){
	this.items = items;
	
	StdRandom.shuffle(this.items);
	int lo = 0, hi = this.items.length-1;
	while(hi > lo){
	    int j = partition(lo, hi);
	    if(j<k) lo = j+1;
	    else if (j>k) hi = j-1;
	    else return this.items[k];
	}
	
	return this.items[k];

    }

    private int partition(int lo, int hi){
	
  	int i = lo, j = hi + 1;
  	
  	while(true){
  	    
  	    while(items[++i].compareTo((items[lo])) < 0)
  		if(i == hi) break;
  	    
  	    if(items[--j].compareTo(items[lo]) > 0)
  		if(j == lo) break;//redundant
  	    
  	    if(i>=j)
  		break;
  	    
  	    exchange(i, j);

  	}
  	
  	exchange(lo, j);
  	
  	return j;
      }
    
    protected void exchange(int i, int j){
   	T swap = items[i];
   	items[i] = items[j];
   	items[j] = swap;
       }
}

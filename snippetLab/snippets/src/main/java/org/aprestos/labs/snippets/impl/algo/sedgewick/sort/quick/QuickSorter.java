package org.aprestos.labs.snippets.impl.algo.sedgewick.sort.quick;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSorter<T extends Comparable<T>> extends AbstractSorter<T> {

    public QuickSorter(T[] items) {
	super(items);
    }

    @Override
    public void sort() {

	StdRandom.shuffle(items);
	sort(0, items.length -1);

    }
    
    private void sort(int lo, int hi){
	
	if(hi <= lo)
	    return;
	int j = partition(lo, hi);
	sort(lo, j-1);
	sort(j+1, hi);
    }
    
    private int partition(int lo, int hi){
	
	T v = items[lo];
	int i = lo, j = hi + 1;
	
	while(true){
	    
	    while(items[++i].compareTo((v)) < 0)
		if(i == hi) break;
	    
	    while(items[--j].compareTo(v) > 0)
		if(j == lo) break;//redundant
	    
	    if(i>=j)
		break;
	    
	    exchange(i, j);

	}
	
	exchange(lo, j);
	
	return j;
    }


}

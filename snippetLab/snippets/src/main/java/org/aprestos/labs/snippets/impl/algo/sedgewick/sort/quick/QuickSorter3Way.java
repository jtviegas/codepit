package org.aprestos.labs.snippets.impl.algo.sedgewick.sort.quick;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter;

import edu.princeton.cs.algs4.StdRandom;

public class QuickSorter3Way<T extends Comparable<T>> extends AbstractSorter<T> {

    public QuickSorter3Way(T[] items) {
	super(items);
    }

    @Override
    public void sort() {

	StdRandom.shuffle(items);
	sort(0, items.length -1);

    }
    
    private void sort(int lo, int hi){
	
	if(hi <= lo) return;
	T v = items[lo];
	int i = lo, gt = hi, lt = lo;
	
	while(i <= gt){
	    int cmp = items[i].compareTo((v));
	    if(cmp < 0) exchange(i++, lt++);
	    else if (cmp > 0) exchange(i, gt--);
	    else i++;
	}

	sort(lo, lt-1);
	sort(gt+1, hi);
    }
    


}

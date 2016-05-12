package org.aprestos.labs.snippets.impl.algo.sedgewick.sort.insertion;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter;

public class InsertionSorter<T extends Comparable<T>> extends AbstractSorter<T> {

    public InsertionSorter(T[] items) {
	super(items);
    }

    @Override
    public void sort() {
	for(int i = 0; i < items.length ; i++ ){
	    for(int j = i; j > 0; j-- ){
		    if(less(items[j], items[j-1]))
			exchange(j, j-1);
		    else
			break;
	    }   
	}
    }

}

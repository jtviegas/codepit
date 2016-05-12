package org.aprestos.labs.snippets.impl.algo.sedgewick.sort.selection;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter;

public class SelectionSorter<T extends Comparable<T>> extends AbstractSorter<T> {

    public SelectionSorter(T[] items) {
	super(items);
    }

    @Override
    public void sort() {
	for(int i = 0; i < (items.length - 1) ; i++ ){
	    int min = i;
	    for(int j = i+1; j < items.length; j++ ){
		    if(less(items[j], items[min])){
			exchange(j, min);
			//min = j;
		    }
		}   
	}
    }

}

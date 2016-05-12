package org.aprestos.labs.snippets.impl.algo.sedgewick.sort.shell;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter;

public class ShellSorter<T extends Comparable<T>> extends AbstractSorter<T> {

    public ShellSorter(T[] items) {
	super(items);
    }

    @Override
    public void sort() {

	int n = items.length;	
	int hop = 1;
	while(hop < n/3) hop = 3*hop + 1;
	
	while(hop >= 1){
	    
	    for(int i = hop; i < n ; i++ )
		    for(int j = i; j >= hop && less(items[j], items[j-hop]); j -= hop )
				exchange(j, j-hop);

	    hop /= 3;
	}
	
	
    }
    
    public int getAdjustedInitialHop(int n){
	
	int result = 0;
	
	int lowerHop = 0, upperHop = 1;
	
	if(n < 4)
	    result=1;
	else
	    result = n/3;
	
	while(!(result < upperHop && result >= lowerHop)){
	    lowerHop = upperHop;
	    upperHop = (upperHop*3) + 1;
	}
	
	result = lowerHop;
	
	return result;
    }


}

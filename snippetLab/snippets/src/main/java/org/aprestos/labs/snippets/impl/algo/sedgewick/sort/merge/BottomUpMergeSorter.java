package org.aprestos.labs.snippets.impl.algo.sedgewick.sort.merge;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter;

public class BottomUpMergeSorter<T extends Comparable<T>> extends AbstractSorter<T> {

    public BottomUpMergeSorter(T[] items) {
	super(items);
    }

    @Override
    public void sort() {

	int n = items.length;	
	
	doSort(items, 0, ( n == 0 ? 0 : n-1));
	
	
    }
    
    private void doSort(T[] a, int low, int high){
	
	if(low > high)
	    throw new IllegalArgumentException("low must be smaller or equal to high!");
	
	if( low == high )
	    return;
	
	int N = a.length;
	
	for(int sz=1; sz < N; sz += sz ){
	    for( int j = 0 ; j < N - sz; j += sz + sz){
		merge(a, j, j + sz -1, Math.min(j + sz + sz -1, N-1));
	    }
	}
	
    }

    private void merge(T[] arr, int low, int mid, int high){
	
	@SuppressWarnings("unchecked")
	T[] x = (T[]) new Comparable[arr.length];
	System.arraycopy(arr, 0, x, 0, arr.length);
	
	if (! isSorted(arr, low, mid))
	    throw new IllegalArgumentException("array must be sorted [1st]");
	if (! isSorted(arr, mid+1, high))
	    throw new IllegalArgumentException("array must be sorted [2nd]");

   	int i = low, j = mid + 1;
   	for(int k = low; k <= high; k++ ){
   	    
   	    //if we exhausted the first array then just copy back the 2nd array elements
   	    if(i > mid)
   		arr[k] = x[j++];
   	    //if we exhausted the second array then just copy back the 1st array elements
   	    else if( j > high )
   		arr[k] = x[i++];
   	    else if( less( x[j], x[i] ) )
   		arr[k] = x[j++];
   	    else
   		arr[k] = x[i++];
   	    
   	}
   	
    }


}

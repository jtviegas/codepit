package org.aprestos.labs.snippets.impl.algo.sedgewick.sort.merge;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.AbstractSorter;

public class MergeSorter<T extends Comparable<T>> extends AbstractSorter<T> {

    public MergeSorter(T[] items) {
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

	int mid = ((high - low)/2) + low;
	doSort(a, low, mid);
	doSort(a, mid+1, high);
	merge(a, low, mid, high);
	
    }
    
    /**
     * merge sorted subarrays
     * 
     * @param arr
     * @param low
     * @param mid
     * @param high
     */
    private void merge(T[] arr, int low, int mid, int high){
	
	//copy the array
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

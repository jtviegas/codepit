package org.aprestos.labs.snippets.impl.algo.sedgewick.sort;

import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.insertion.InsertionSorter;
import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.merge.BottomUpMergeSorter;
import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.merge.MergeSorter;
import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.quick.QuickSorter;
import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.quick.QuickSorter3Way;
import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.selection.SelectionSorter;
import org.aprestos.labs.snippets.impl.algo.sedgewick.sort.shell.ShellSorter;


public abstract class AbstractSorter<T  extends Comparable<T>> implements Sorter<T> {
    
    protected T[] items;
    
    public enum SorterType{
	Selection, Insertion, Shell, Merge, BottomUpMerge, Quick, Quick3Way
    }
    
    public static <T extends Comparable<T>> Sorter<T> getInstance(SorterType type, T[] items){
	Sorter<T> instance = null;
	switch(type){
		case Insertion:
		    instance = new InsertionSorter<T>(items);
		    break;
		case Selection:
		    instance = new SelectionSorter<T>(items);
		    break;
		case Shell:
		    instance = new ShellSorter<T>(items);
		    break;
		case Merge:
		    instance = new MergeSorter<T>(items);
		    break;
		case BottomUpMerge:
		    instance = new BottomUpMergeSorter<T>(items);
		    break;
		case Quick:
		    instance = new QuickSorter<T>(items);
		    break;
		case Quick3Way:
		    instance = new QuickSorter3Way<T>(items);
		    break;
		default:
		    instance = new SelectionSorter<T>(items);
	}
	return instance;
    }
    
    protected AbstractSorter(T[] items){
	this.items = items;
    }
    
    protected T[] createArray(int size) {
	  @SuppressWarnings("unchecked")
	T[] arr = (T[])new Object[size];  // Suppose this was allowed for the time being.
	  return arr;
    }
    /* (non-Javadoc)
     * @see org.aprestos.labs.snippets.impl.algo.sedgewick.sort.Sorter#sort()
     */
    public abstract void sort();
    
    @SuppressWarnings("unchecked")
    public static <T> boolean less(T a1, T a2) {
	boolean r = false;
	if( !(a1 instanceof Comparable<?>) || !(a2 instanceof Comparable<?>) )
	    throw new IllegalArgumentException("arguments must implement the Comparable interface!");
	
	if(0 > ((Comparable<T>) a1).compareTo(a2))
	    r = true;
	
	return r;
    }
    
    
    
    protected void exchange(int i, int j){
	T swap = items[i];
	items[i] = items[j];
	items[j] = swap;
    }
    
    public static <T> Boolean isSorted(T[] arr, int low, int high){
	
	boolean result=true;
	
	if(null == arr)
	    throw new IllegalArgumentException("must provide an array");
	
	if((high - low + 1) < 2)
	    return true;
	
	for(int i=low + 1 ; i <= high; i++){
	    if( less(arr[i], arr[i-1]) ){
		result=false;
		break;
	    }
	}
	
	return result;
    }
}

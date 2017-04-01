package org.aprestos.labs.snippets.impl.collections.arraylist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class FilteredIterator<T>{

    private Set<T> source;
    private Map<Filter<T>, List<T>> filtersMap = new HashMap<Filter<T>, List<T>>();
    private List<Iterator<T>> iterators = new ArrayList<Iterator<T>>();
    private T[] current;
    
    
    public FilteredIterator(Set<T> source, List<Filter<T>> filters){
	this.source = source;
	
	for(Filter<T> f: filters)
	    filtersMap.put(f, new ArrayList<T>());

	init();
    }

    public boolean hasNext(int filterIndex) {
	return iterators.get(filterIndex).hasNext();
    }

    public T next(int filterIndex) {
	current[filterIndex] = iterators.get(filterIndex).next();
	return current[filterIndex] ;
    }
    
    public void remove(int filterIndex){
	if(null == current[filterIndex] )
	    throw new NoSuchElementException();
	iterators.get(filterIndex).remove();
	T element = current[filterIndex];
	source.remove(element);
	current[filterIndex] = null;
    }

    
    @SuppressWarnings("unchecked")
    private void init(){
	for(T o:source){
	    for( Entry<Filter<T>,List<T>>  e:filtersMap.entrySet() ){
		if(e.getKey().filter(o)){
		    e.getValue().add(o);
		    break;
		}
	    }
	}
	
	for( List<T> l: filtersMap.values())
	    iterators.add(l.iterator());
	
	current = (T[])new Object[filtersMap.values().size()];

    }
    
    
}

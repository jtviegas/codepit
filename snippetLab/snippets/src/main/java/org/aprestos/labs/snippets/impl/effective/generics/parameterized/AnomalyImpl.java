package org.aprestos.labs.snippets.impl.effective.generics.parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnomalyImpl<T> implements Anomaly<T> {

    private T id;
    private List<T> childrenIDs = new ArrayList<T>();
    private double quantifier;
	
	
    public AnomalyImpl(T id, Collection<T> ids) {
	this.id = id;
	this.childrenIDs.addAll(ids);
    }
   

    @Override
    public T getId() {
	return id;
    }


    @Override
    public Collection<T> getChildrenIDs() {
	return new ArrayList<T>(this.childrenIDs);
    }


    @Override
    public double getQuantifier() {
	return quantifier;
    }
    
    
}

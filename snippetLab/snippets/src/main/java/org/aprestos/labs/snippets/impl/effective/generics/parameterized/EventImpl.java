package org.aprestos.labs.snippets.impl.effective.generics.parameterized;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventImpl<T> implements Event<T> {

    private T id;
    private List<T> childrenIDs = new ArrayList<T>();
    
    public EventImpl(T id, Collection<? extends T> ids) {
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

}

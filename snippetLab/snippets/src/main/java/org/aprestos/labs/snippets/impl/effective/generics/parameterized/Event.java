package org.aprestos.labs.snippets.impl.effective.generics.parameterized;

import java.util.Collection;

public interface Event<T>
{
	public T getId();
	public Collection<T> getChildrenIDs();
}






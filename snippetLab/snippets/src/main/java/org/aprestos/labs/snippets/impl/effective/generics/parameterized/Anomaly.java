package org.aprestos.labs.snippets.impl.effective.generics.parameterized;

public interface Anomaly<T> extends Event<T>
{
	public double getQuantifier();
}

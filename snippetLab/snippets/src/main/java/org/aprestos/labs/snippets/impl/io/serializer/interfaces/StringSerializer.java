package org.aprestos.labs.snippets.impl.io.serializer.interfaces;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;

public interface StringSerializer<T> {
	
	String toString(T o) throws SerializerException;
	T toObject(String s) throws SerializerException;
}

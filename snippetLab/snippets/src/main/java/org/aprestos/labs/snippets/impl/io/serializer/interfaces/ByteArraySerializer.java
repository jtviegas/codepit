package org.aprestos.labs.snippets.impl.io.serializer.interfaces;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;

public interface ByteArraySerializer<T> {
	
	byte[] toByteArray(T o) throws SerializerException;
	T toObject(byte[] ba) throws SerializerException;
}

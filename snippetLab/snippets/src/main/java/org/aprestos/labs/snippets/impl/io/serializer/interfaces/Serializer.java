package org.aprestos.labs.snippets.impl.io.serializer.interfaces;

import java.io.File;
import java.util.List;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;

public interface Serializer<T> {
	void start() throws SerializerException;
	void stop() throws SerializerException;
	void reset() throws SerializerException;
	void serialize(T obj) throws SerializerException;
	void serialize(List<T> objs) throws SerializerException;
	List<T> deserialize() throws SerializerException;
	void setFolder(File folder);
}

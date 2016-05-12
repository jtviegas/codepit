package org.aprestos.labs.snippets.impl.io.serializer;

import org.aprestos.labs.snippets.impl.io.serializer.impl.file.ObjectOutputStreamFileSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.impl.file.ObjectOutputStreamMultipleFileSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.Serializer;

public class SerializerFactory {

	public static Serializer<?> getSerializer(SerializerType type){
		Serializer<?> result = null;
		
		switch (type){
		case ObjectOutputStreamFileSerializer:
			result = new ObjectOutputStreamFileSerializer();
			break;
		case ObjectOutputStreamMultipleFileSerializer:
			result = new ObjectOutputStreamMultipleFileSerializer();
			break;

			default:
				result=null;
		}
		return result;
	}
	
	public static enum SerializerType {
		ObjectOutputStreamFileSerializer,ObjectOutputStreamMultipleFileSerializer, 
	}

}

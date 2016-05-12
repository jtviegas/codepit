package org.aprestos.labs.snippets.impl.io.serializer;

import org.aprestos.labs.snippets.impl.io.serializer.interfaces.ByteArraySerializer;

public class ByteArraySerializerFactory {

	public static ByteArraySerializer<?> getByteArraySerializer(ByteArraySerializerType type){
		ByteArraySerializer<?> result = null;
		
		switch (type){
		case SimpleByteArraySerializer:
			result = null;
			break;
			
			default:
				result=null;
		}
		return result;
	}
	
	public static enum ByteArraySerializerType {
		SimpleByteArraySerializer, 
	}

}

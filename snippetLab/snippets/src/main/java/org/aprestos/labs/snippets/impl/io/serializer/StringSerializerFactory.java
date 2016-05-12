package org.aprestos.labs.snippets.impl.io.serializer;

import org.aprestos.labs.snippets.impl.io.serializer.impl.string.ByteArrayDefaultCharsetStringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.impl.string.ByteArrayUTF8CharsetStringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.impl.string.ByteArrayWithBase64AndUSACIIEncodingStringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.impl.string.GsonStringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.StringSerializer;

public class StringSerializerFactory {

	public static StringSerializer<?> getStringSerializer(StringSerializerType type){
		StringSerializer<?> result = null;
		
		switch (type){
		case SimpleByteArrayStringSerializer:
			result = null;
			break;
		case ByteArrayUTF8CharsetStringSerializer:
			result = new ByteArrayUTF8CharsetStringSerializer();
			break;
		case GSONStringSerializer:
			result = new GsonStringSerializer();
			break;
		case ByteArrayWithBase64AndUSACIIEncodingStringSerializer:
			result = new ByteArrayWithBase64AndUSACIIEncodingStringSerializer();
			break;
		case ByteArrayDefaultCharsetStringSerializer:
			result = new ByteArrayDefaultCharsetStringSerializer();
			break;
			default:
				result=null;
		}
		return result;
	}
	
	public static enum StringSerializerType {
		SimpleByteArrayStringSerializer, ByteArrayUTF8CharsetStringSerializer,GSONStringSerializer,
		ByteArrayWithBase64AndUSACIIEncodingStringSerializer,ByteArrayDefaultCharsetStringSerializer
	}

}

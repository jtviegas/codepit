package org.aprestos.labs.snippets.impl.io.serializer.impl.string;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.StringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.objects.StringListOperation;

public class ByteArrayWithBase64AndUSACIIEncodingStringSerializer implements StringSerializer<StringListOperation> {

	public ByteArrayWithBase64AndUSACIIEncodingStringSerializer() {

	}

	
	public String toString(StringListOperation o) throws SerializerException {
		String result = null;
		try {
			
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(o);
			so.flush();
			byte[] bs = bo.toString().getBytes("US-ASCII"); 
			bs = Base64.encodeBase64(bs);
			result = new String(bs, "US-ASCII");

		} catch (IOException e) {
			throw new SerializerException(e);
		}
		return result;
	}

	
	public StringListOperation toObject(String s) throws SerializerException {

		StringListOperation result = null;

		try {
			byte[] bs = s.getBytes("US-ASCII"); 
			bs = Base64.decodeBase64(bs);
			String sobj = new String(bs, "US-ASCII");
			ByteArrayInputStream bis = new ByteArrayInputStream(s.getBytes());
			ObjectInputStream ois = new ObjectInputStream(bis);
			result = (StringListOperation) ois.readObject();
		} catch (Exception e) {
			throw new SerializerException(e);
		}

		return result;
	}

}

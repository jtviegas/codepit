package org.aprestos.labs.snippets.impl.io.serializer.impl.string;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.StringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.objects.StringListOperation;



public class ByteArrayUTF8CharsetStringSerializer  implements StringSerializer<StringListOperation> {

	public ByteArrayUTF8CharsetStringSerializer() {

	}

	
	public String toString(StringListOperation o) throws SerializerException {
		String result = null; 
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			 ObjectOutputStream so = new ObjectOutputStream(bo);
			 so.writeObject(o); 
			 so.flush(); 
			 result = bo.toString("UTF-8");
		} catch (IOException e) {
			 throw new SerializerException(e);
		}
		return result;
	}

	
	public StringListOperation toObject(String s) throws SerializerException {
		
		StringListOperation result = null;
		
		 try {
			ByteArrayInputStream bis = new ByteArrayInputStream(s.getBytes(Charset.forName("UTF-8")));
			 ObjectInputStream ois = new ObjectInputStream(bis); 
			 result = (StringListOperation)ois.readObject();
		} catch (Exception e) {
			 throw new SerializerException(e);
		}
		 
		return result;
	}


}

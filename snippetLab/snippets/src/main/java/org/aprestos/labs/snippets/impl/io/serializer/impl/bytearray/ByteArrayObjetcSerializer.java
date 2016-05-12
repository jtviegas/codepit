package org.aprestos.labs.snippets.impl.io.serializer.impl.bytearray;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.ByteArraySerializer;
import org.aprestos.labs.snippets.impl.io.serializer.objects.StringListOperation;



public class ByteArrayObjetcSerializer  implements ByteArraySerializer<StringListOperation> {

	public ByteArrayObjetcSerializer() {

	}




	public StringListOperation toObject(String s) throws SerializerException {
		
		StringListOperation result = null;
		
		 try {
			ByteArrayInputStream bis = new ByteArrayInputStream(s.getBytes());
			 ObjectInputStream ois = new ObjectInputStream(bis); 
			 result = (StringListOperation)ois.readObject();
		} catch (Exception e) {
			 throw new SerializerException(e);
		}
		 
		return result;
	}



	
	public byte[] toByteArray(StringListOperation o) throws SerializerException {
		byte[] result = null; 
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			 ObjectOutputStream so = new ObjectOutputStream(bo);
			 so.writeObject(o); 
			 so.flush(); 
			 result = bo.toByteArray();
		} catch (IOException e) {
			 throw new SerializerException(e);
		}
		return result;
	}



	public StringListOperation toObject(byte[] ba) throws SerializerException {
		StringListOperation result = null;
		
		 try {
			ByteArrayInputStream bis = new ByteArrayInputStream(ba);
			 ObjectInputStream ois = new ObjectInputStream(bis); 
			 result = (StringListOperation)ois.readObject();
		} catch (Exception e) {
			 throw new SerializerException(e);
		}
		 
		return result;
	}


}

package org.aprestos.labs.snippets.impl.io.serializer.impl.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.Serializer;
import org.aprestos.labs.snippets.impl.io.serializer.objects.StringListOperation;



public class ByteArraySerializerImpl  implements Serializer<StringListOperation> {

	public ByteArraySerializerImpl() {

	}


	public String toString(StringListOperation o) throws SerializerException {
		String result = null; 
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			 ObjectOutputStream so = new ObjectOutputStream(bo);
			 so.writeObject(o); 
			 so.flush(); 
			 result = bo.toString();
		} catch (IOException e) {
			 throw new SerializerException(e);
		}
		return result;
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

	
	public void start() throws SerializerException {
		// TODO Auto-generated method stub
		
	}

	
	public void stop() throws SerializerException {
		// TODO Auto-generated method stub
		
	}


	public void reset() throws SerializerException {
		// TODO Auto-generated method stub
		
	}


	public void serialize(StringListOperation obj) throws SerializerException {
		// TODO Auto-generated method stub
		
	}


	public void serialize(List<StringListOperation> objs) throws SerializerException {
		// TODO Auto-generated method stub
		
	}


	public List<StringListOperation> deserialize() throws SerializerException {
		// TODO Auto-generated method stub
		return null;
	}


	public void setFolder(File folder) {
		// TODO Auto-generated method stub
		
	}


}

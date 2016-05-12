package org.aprestos.labs.snippets.impl.io.serializer.impl.file;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.Serializer;
import org.aprestos.labs.snippets.impl.io.serializer.objects.StringListOperation;

public class ObjectOutputStreamFileSerializer implements Serializer<StringListOperation> {

	private File folder;
	private File file;
	private FileChannel channel;
	
	public ObjectOutputStreamFileSerializer() {}


	public void start() throws SerializerException {
		try {
			this.file = new File(folder.getAbsolutePath() + System.getProperty("file.separator") + this.getClass().getSimpleName());
			this.channel = new RandomAccessFile(this.file, "rws").getChannel();
		} catch (FileNotFoundException e) {
			throw new SerializerException(e);
		}
	}


	public void stop() throws SerializerException {
		if(null != channel){
			try { channel.close(); } catch (IOException e) { }
		}
		channel = null;
	}


	public void reset() throws SerializerException {
		stop();
		if(file.exists()) 
			file.delete();
		start();
	}

	
	public void serialize(StringListOperation obj) throws SerializerException {
		
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(obj);
			so.flush();
			
			this.channel.write(ByteBuffer.wrap(bo.toByteArray()));
		} catch (Exception e) {
			throw new SerializerException(e);
		}
	}

	
	public void serialize(List<StringListOperation> objs) throws SerializerException {
		for(StringListOperation o:objs)
			serialize(o);
	}

	
	public List<StringListOperation> deserialize() throws SerializerException {
		List<StringListOperation> result = new ArrayList<StringListOperation>();
		StringListOperation obj = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			try {
				while (null != (obj = (StringListOperation)ois.readObject()))
					result.add(obj);
			} catch (EOFException e) {}
			
		} catch (Exception e) {
			throw new SerializerException(e);
		}

		return result;
	}


	public void setFolder(File folder) {
		this.folder = folder;
	}

}

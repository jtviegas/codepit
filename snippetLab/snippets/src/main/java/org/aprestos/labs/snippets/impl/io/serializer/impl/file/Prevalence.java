package org.aprestos.labs.snippets.impl.io.serializer.impl.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.Serializer;

public class Prevalence<T> implements Serializer<T> {

	//ObjectOutputStreamMultipleFileSerializer-<operation id>.<timestamp>
	protected static final String NAME_FORMAT = "%s.%s";
	protected static final String NAME_REGEXP = Prevalence.class.getSimpleName() + ".[0-9]+";
	
	private File folder;
	public Prevalence() {}


	public void start() throws SerializerException {
	
			if(!(this.folder.canRead() && this.folder.canWrite())) 
				throw new SerializerException("can write or read for the specified folder: " + this.folder);
	
	}

	
	public void stop() throws SerializerException {

	}

	
	public void reset() throws SerializerException {
		try {
			cleanupFiles();
		} catch (IOException e) {
			throw new SerializerException(e);
		}
	}

	@SuppressWarnings("resource")

	public void serialize(T obj) throws SerializerException {
		FileChannel channel = null;
		try {
			
			File file = new File(folder.getAbsolutePath() + System.getProperty("file.separator") + 
					String.format(NAME_FORMAT, this.getClass().getSimpleName(), Long.toString(System.currentTimeMillis())));
			channel = new RandomAccessFile(file, "rws").getChannel();
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream so = new ObjectOutputStream(bo);
			so.writeObject(obj);
			so.flush();
			
			channel.write(ByteBuffer.wrap(bo.toByteArray()));
		} catch (Exception e) {
			throw new SerializerException(e);
		}
		finally{
			if(null != channel)
				try {channel.close();} catch (IOException e) {}
			
			channel = null;
		}
	}


	public void serialize(List<T> objs) throws SerializerException {

			for(T o:objs)
				serialize(o);
		
	}

	
	public List<T> deserialize() throws SerializerException {
		
		List<T> result = new ArrayList<T>();
		T obj = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;

		try {
			List<File> files = getFiles();
			for(File f:files){
				try {
					ois = new ObjectInputStream((fis = new FileInputStream(folder.getAbsolutePath() + System.getProperty("file.separator") + f)));
					obj = (T)ois.readObject();
						result.add(obj);
				}
				finally { 
					try {fis.close(); } catch (IOException e) { e.printStackTrace(); }
					try {ois.close(); } catch (IOException e) { e.printStackTrace(); }
					fis=null;
					ois = null;
				}
			}
			
			cleanupFiles();
			
		} catch (Exception e) {
			throw new SerializerException(e);
		} 
		return result;
	}
	
	private List<File> getFiles(){
		List<File> result = new ArrayList<File>();
		List<String> filenames = Arrays.asList(this.folder.list(new FilenameFilter(){

		
			public boolean accept(File dir, String name) {
				if(name.matches(NAME_REGEXP))
					return true;
				return false;
			}}));
		
		Collections.sort(filenames, new Comparator<String>(){
			
			public int compare(String o1, String o2) {
				
				String s_ts1 = o1.substring(o1.indexOf(".") + 1);
				String s_ts2 = o2.substring(o2.indexOf(".") + 1);
				
				long ts1 = Long.parseLong(s_ts1);
				long ts2 = Long.parseLong(s_ts2);
				
				return Long.compare(ts1, ts2);
			}});
		
		for(String s: filenames)
			result.add(new File(s));
			
		return result;
	}
	
	private void cleanupFiles() throws IOException{
		FileUtils.cleanDirectory(folder);
	}

	
	public void setFolder(File folder) {
		this.folder = folder;
	}

}

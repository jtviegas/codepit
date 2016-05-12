package org.aprestos.labs.snippets.impl.serialization.kryo;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.aprestos.labs.snippets.impl.io.serializer.exceptions.SerializerException;
import org.junit.Assert;
import org.junit.Test;

import com.javamex.classmexer.MemoryUtil;

public class KryoTest {

	private static final String TMP_FOLDER = "/tmp/";
	private static final String FILE_SUFFIX = ".obj";
	private static final int KBYTE = 1024;
	
	private void showFileSize(String file){
		 System.out.println(String.format("file %s has %d Kbytes", file, FileUtils.sizeOf(new File(file))/KBYTE ));
	}
	

	
	@Test
	public void firstSerializationTest() throws FileNotFoundException, ClassNotFoundException {
		
		String file = TMP_FOLDER + "firstSerializationTest" + FILE_SUFFIX;
		
		Mock original = Mock.createRandomMock();
		Serializer o = new Serializer();
		o.serialize(original, file);
		Mock copy = (Mock) o.deSerialize(file, Mock.class);//
		
		Assert.assertEquals(original, copy);
		showFileSize(file);
		
	}
	
	@Test
	public void firstSerializationTestGenericCompressedBytesOnly() throws Exception {

		
		Mock original = Mock.createRandomMock();
		GenericSerializer<Mock> o = new GenericSerializer<Mock>(original);
		byte[] bytes = o.obj2bytes();
		Mock copy = (Mock) o.bytes2obj(bytes);//
		
		Assert.assertEquals(original, copy);
		System.out.println(String.format("bytearray has %d Kbytes", MemoryUtil.deepMemoryUsageOf(bytes)/KBYTE ));
		
	}
	
	@Test
	public void firstSerializationTestGenericCompressed() throws IOException {
		
		String file = TMP_FOLDER + "firstSerializationTestGeneric" + FILE_SUFFIX;
		
		Mock original = Mock.createRandomMock();
		GenericSerializer<Mock> o = new GenericSerializer<Mock>(original);
		o.serialize( file);
		Mock copy = (Mock) o.deSerialize(file);//
		
		Assert.assertEquals(original, copy);
		showFileSize(file);
		
	}
	
	@Test
	public void firstSerializationGzippedTest() throws IOException {
		
		String file = TMP_FOLDER + "firstSerializationGzippedTest" + FILE_SUFFIX;
		
		Mock original = Mock.createRandomMock();
		Serializer o = new Serializer();
		o.serializeCompressedGzip(original, file);
		Mock copy = (Mock) o.deSerializeCompressedGzip(file, Mock.class);
		
		Assert.assertEquals(original, copy);
		showFileSize(file);
		
	}
	
	@Test
	public void firstSerializationJTest() throws SerializerException {
		
		String file = TMP_FOLDER + "firstSerializationJTest" + FILE_SUFFIX;
		
		Mock original = Mock.createRandomMock();
		Serializer o = new Serializer();
		o.serializeJ(original, file);
		Mock copy = (Mock) o.deserializeJ(file);
		
		Assert.assertEquals(original, copy);
		showFileSize(file);
		
	}


}

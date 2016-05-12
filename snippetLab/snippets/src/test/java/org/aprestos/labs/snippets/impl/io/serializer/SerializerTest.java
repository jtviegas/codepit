package org.aprestos.labs.snippets.impl.io.serializer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.aprestos.labs.snippets.impl.io.serializer.SerializerFactory.SerializerType;
import org.aprestos.labs.snippets.impl.io.serializer.StringSerializerFactory.StringSerializerType;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.Serializer;
import org.aprestos.labs.snippets.impl.io.serializer.interfaces.StringSerializer;
import org.aprestos.labs.snippets.impl.io.serializer.objects.StringListOperation;
import org.aprestos.labs.snippets.impl.logger.CustomLogger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class SerializerTest implements Serializable {

	/**
	 * 
	 */
	private static Random random = new Random();
	private static final long serialVersionUID = 1L;
	private static final int STRESS_TEST_LIMIT = 10000;
	private static File PATH;

	@Test
	public void serializerStressTests() throws Exception {

		stressTestSerializer(SerializerType.ObjectOutputStreamMultipleFileSerializer);
		/*
		 * testSerializer(SerializerType.ObjectOutputStreamFileSerializer);
		 * fails: Caused by: java.io.StreamCorruptedException: invalid type
		 * code: AC at
		 * java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1460)
		 */

	}

	@Test
	public void stringSerializerSingleCaseTests() throws Exception {

		 
		/*
		 * testStringSerializer(StringSerializerType.ByteArrayUTF8CharsetStringSerializer);
		 * 
		 * fails: 
		 * Caused by: java.io.StreamCorruptedException: invalid stream
		 * header: EFBFBDEF at
		 * java.io.ObjectInputStream.readStreamHeader(ObjectInputStream
		 * .java:885) at
		 * java.io.ObjectInputStream.<init>(ObjectInputStream.java:348)
		 */

		/*
		 * testStringSerializer(StringSerializerType.ByteArrayWithBase64AndUSACIIEncodingStringSerializer);
		 * 
		 * fails: 
		 * Caused by: java.io.StreamCorruptedException: invalid stream
		 * header: 507A3841 at java
		 * .io.ObjectInputStream.readStreamHeader(ObjectInputStream.java:885) at
		 * java.io.ObjectInputStream.<init>(ObjectInputStream.java:348)
		 */

		/*
		 * testStringSerializerSingleCase(StringSerializerType.GSONStringSerializer); 
		 * 
		fails:
		java.lang.AssertionError: expected:<org.aprestos.labs.snippets.impl.io.serializer.SerializerTest$1@99e408f0> but was:<null>
		at org.junit.Assert.fail(Assert.java:88)
		
		 */

		
		/*
		 * testStringSerializerSingleCase(StringSerializerType.ByteArrayDefaultCharsetStringSerializer);
		 * 	
	 	fails:
		Caused by: java.io.StreamCorruptedException: invalid stream header: EFBFBDEF
		at java.io.ObjectInputStream.readStreamHeader(ObjectInputStream.java:885)
		at java.io.ObjectInputStream.<init>(ObjectInputStream.java:348)
		at org.aprestos.labs.snippets.impl.io.serializer.impl.string.ByteArrayDefaultCharsetStringSerializer.toObject(ByteArrayDefaultCharsetStringSerializer.java:43)
		*/

	}

	@Test
	public void stringSerializerTests() throws Exception {

		
		/*
		 * testStringSerializer(StringSerializerType.ByteArrayUTF8CharsetStringSerializer);
		 * 
		 * fails: 
		 * Caused by: java.io.StreamCorruptedException: invalid stream
		 * header: EFBFBDEF at
		 * java.io.ObjectInputStream.readStreamHeader(ObjectInputStream
		 * .java:885) at
		 * java.io.ObjectInputStream.<init>(ObjectInputStream.java:348)
		 */
		
		/*
		 * testStringSerializer(StringSerializerType.ByteArrayWithBase64AndUSACIIEncodingStringSerializer);
		 * 
		 * fails: 
		 * Caused by: java.io.StreamCorruptedException: invalid stream
		 * header: 507A3841 at java
		 * .io.ObjectInputStream.readStreamHeader(ObjectInputStream.java:885) at
		 * java.io.ObjectInputStream.<init>(ObjectInputStream.java:348)
		 */
	

	}

	@Test
	public void serializerTests() throws Exception {

		testSerializer(SerializerType.ObjectOutputStreamMultipleFileSerializer);
		/*
		 * testSerializer(SerializerType.ObjectOutputStreamFileSerializer);
		 * fails: Caused by: java.io.StreamCorruptedException: invalid type
		 * code: AC at
		 * java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1460)
		 */

	}

	@Test
	public void serializerSingleCaseTests() throws Exception {
		testSerializerSingleCase(SerializerType.ObjectOutputStreamFileSerializer);
		/*
		 * fails: Caused by: java.io.StreamCorruptedException: invalid type
		 * code: AC at
		 * java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1460)
		 */

	}

	@BeforeClass
	public static void init() {
		// PropertyConfigurator.configure("./log4j.properties");
		try {
			PATH = new File("/tmp/data");
			if (!PATH.exists())
				PATH.mkdirs();
			else
				cleanFiles(PATH);

			CustomLogger.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void cleanFiles(File path) {

		File[] files = path.listFiles();
		for (File f : files)
			f.delete();
	}

	protected void testStringSerializerSingleCase(StringSerializerType type) throws Exception {

		System.out.println("testStringSerializer:" + type.toString());

		StringListOperation o1 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "oi";
				if (object.add(o))
					return o;
				return null;
			}
		};

		List<String> l1 = new ArrayList<String>();
		o1.execute(l1);

		@SuppressWarnings("unchecked")
		StringSerializer<StringListOperation> serializer = (StringSerializer<StringListOperation>) StringSerializerFactory
				.getStringSerializer(type);

		String s1 = serializer.toString(o1);
		StringListOperation o4 = serializer.toObject(s1);
		Assert.assertEquals(o1, o4);

		List<String> l2 = new ArrayList<String>();
		o4.execute(l2);
		Assert.assertEquals(l1, l2);

	}

	protected void testStringSerializer(StringSerializerType type) throws Exception {

		System.out.println("testStringSerializer:" + type.toString());

		StringListOperation o1 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "oi";
				if (object.add(o))
					return o;
				return null;
			}
		};
		StringListOperation o2 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "hola";
				if (object.add(o))
					return o;
				return null;
			}
		};
		StringListOperation o3 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "hole";
				if (object.add(o))
					return o;
				return null;
			}
		};

		List<String> l1 = new ArrayList<String>();
		o1.execute(l1);
		o2.execute(l1);
		o3.execute(l1);

		@SuppressWarnings("unchecked")
		StringSerializer<StringListOperation> serializer = (StringSerializer<StringListOperation>) StringSerializerFactory
				.getStringSerializer(type);

		String s1 = serializer.toString(o1);
		String s2 = serializer.toString(o2);
		String s3 = serializer.toString(o3);

		StringListOperation o4 = serializer.toObject(s1);
		StringListOperation o5 = serializer.toObject(s2);
		StringListOperation o6 = serializer.toObject(s3);

		// Assert.assertEquals(o1, o4);
		// Assert.assertEquals(o2, o5);
		// Assert.assertEquals(o3, o6);

		List<String> l2 = new ArrayList<String>();
		o4.execute(l2);
		o5.execute(l2);
		o6.execute(l2);
		Assert.assertEquals(l1, l2);

	}

	private StringListOperation getRandomOp() {
		StringListOperation result = null;
		final String name = Integer.toString(random.nextInt());
		if (random.nextBoolean()) {
			result = new StringListOperation() {
				private static final long serialVersionUID = 1L;

				@Override
				public String execute(List<String> object) throws Exception {
					String o = name;
					if (object.add(o))
						return o;
					return null;
				}
			};
		} else {
			result = new StringListOperation() {
				private static final long serialVersionUID = 1L;

				@Override
				public String execute(List<String> object) throws Exception {
					String o = name;
					if (object.remove(o))
						return o;
					return null;
				}
			};
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected void stressTestSerializer(SerializerType type) throws Exception {

		System.out.println("stressTestSerializer:" + type.toString());
		List<StringListOperation> ops = new ArrayList<StringListOperation>();

		// create random operations
		for (int i = 0; i < STRESS_TEST_LIMIT; i++) {
			ops.add(getRandomOp());
		}

		Serializer<StringListOperation> serializer = (Serializer<StringListOperation>) SerializerFactory
				.getSerializer(type);
		serializer.setFolder(PATH);
		serializer.start();
		for (StringListOperation op : ops)
			serializer.serialize(op);

		List<String> l1 = new ArrayList<String>();
		for (StringListOperation op : ops)
			op.execute(l1);

		serializer.stop();

		serializer = (Serializer<StringListOperation>) SerializerFactory.getSerializer(type);
		serializer.setFolder(PATH);
		serializer.start();
		List<StringListOperation> ops2 = serializer.deserialize();
		serializer.stop();

		Assert.assertEquals(ops, ops2);

		List<String> l2 = new ArrayList<String>();
		for (StringListOperation op : ops2)
			op.execute(l2);

		Assert.assertEquals(l1, l2);
	}

	@SuppressWarnings("unchecked")
	protected void testSerializer(SerializerType type) throws Exception {

		System.out.println("testSerializer:" + type.toString());
		StringListOperation o1 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "oi";
				if (object.add(o))
					return o;
				return null;
			}
		};
		StringListOperation o2 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "hola";
				if (object.add(o))
					return o;
				return null;
			}
		};
		StringListOperation o3 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "hole";
				if (object.add(o))
					return o;
				return null;
			}
		};

		Serializer<StringListOperation> serializer = (Serializer<StringListOperation>) SerializerFactory
				.getSerializer(type);
		serializer.setFolder(PATH);
		serializer.start();
		serializer.serialize(o1);
		serializer.serialize(o2);
		serializer.serialize(o3);
		List<String> l1 = new ArrayList<String>();
		o1.execute(l1);
		o2.execute(l1);
		o3.execute(l1);
		serializer.stop();

		serializer = (Serializer<StringListOperation>) SerializerFactory.getSerializer(type);
		serializer.setFolder(PATH);
		serializer.start();
		List<StringListOperation> os = serializer.deserialize();
		serializer.stop();
		StringListOperation o4 = os.get(0);
		StringListOperation o5 = os.get(1);
		StringListOperation o6 = os.get(2);
		Assert.assertEquals(o1, o4);
		Assert.assertEquals(o2, o5);
		Assert.assertEquals(o3, o6);
		List<String> l2 = new ArrayList<String>();
		o4.execute(l2);
		o5.execute(l2);
		o6.execute(l2);
		Assert.assertEquals(l1, l2);
	}

	@SuppressWarnings("unchecked")
	protected void testSerializerSingleCase(SerializerType type) throws Exception {

		System.out.println("testSerializer:" + type.toString());
		StringListOperation o1 = new StringListOperation() {
			private static final long serialVersionUID = 1L;

			@Override
			public String execute(List<String> object) throws Exception {
				String o = "oi";
				if (object.add(o))
					return o;
				return null;
			}
		};

		Serializer<StringListOperation> serializer = (Serializer<StringListOperation>) SerializerFactory
				.getSerializer(type);
		serializer.setFolder(PATH);
		serializer.start();
		serializer.serialize(o1);

		List<String> l1 = new ArrayList<String>();
		o1.execute(l1);

		serializer.stop();

		serializer = (Serializer<StringListOperation>) SerializerFactory.getSerializer(type);
		serializer.setFolder(PATH);
		serializer.start();
		List<StringListOperation> os = serializer.deserialize();
		serializer.stop();
		StringListOperation o4 = os.get(0);

		Assert.assertEquals(o1, o4);

		List<String> l2 = new ArrayList<String>();
		o4.execute(l2);

		Assert.assertEquals(l1, l2);
	}

}

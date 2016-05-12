package org.aprestos.labs.snippets.impl.caching.prevalence.test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.aprestos.labs.snippets.impl.logger.CustomLogger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class BagTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 1;
	private static File PATH;
	
	@BeforeClass
	public static void init() {
		// PropertyConfigurator.configure("./log4j.properties");
		try {
			PATH = new File("/tmp/data");
			CustomLogger.setup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cleanFiles(File path){
		
		File[] files = path.listFiles();
		for(File f:files)
			f.delete();
	}
	/**
	 * @throws Exception
	 * 
	 */
	@Test
	public void testOne() throws Exception {
		System.out.println("-----------------------------------------------------------");
		cleanFiles(PATH);
		List<String> object = new ArrayList<String>();
		Bag<List<String>, String> bag = new Bag<List<String>, String>(object, PATH.getAbsolutePath());
		bag.apply(new OpAdd("oi"));
		bag.apply(new OpAdd("ole"));

		bag.stop();
		//Thread.sleep(60000);
		Assert.assertTrue(true);
	}
	
	@Test
	public void test2() throws Exception {
		System.out.println("-----------------------------------------------------------");
		List<String> object = new ArrayList<String>();
		Bag<List<String>, String> bag = new Bag<List<String>, String>(object, "/tmp/data");

		bag.stop();
		//Thread.sleep(60000);
		Assert.assertTrue(true);
	}
	

	//@Test
	public void testThree() throws Exception {
		System.out.println("-----------------------------------------------------------");
		List<String> object = new ArrayList<String>();
		Bag<List<String>, String> bag = new Bag<List<String>, String>(object, "/tmp/data");
		bag.apply(new OpAdd("oi"));
		bag.apply(new OpAdd("ole"));
		bag.apply(new OpAdd("hello ola"));
		Thread.sleep(10000);
		Assert.assertTrue(true);
		bag.stop();
		bag = null;
		object = new ArrayList<String>();
		bag = new Bag<List<String>, String>(object, "/tmp/data");

		Thread.sleep(60000);
	}

	/*
	 * @Test public void testTwo() throws Exception {
	 * 
	 * OpAdd o1 = new OpAdd("o1"); OpAdd o2 = new OpAdd("o2"); File file = new
	 * File("/tmp/files");
	 * 
	 * file = Bag.object2File(Arrays.asList((Object)o1,(Object)o2), file);
	 * List<Object> objs = Bag.getObjectsFromFile(file);
	 * 
	 * Assert.assertEquals(o1, objs.get(0)); Assert.assertEquals(o2,
	 * objs.get(1));
	 * 
	 * }
	 */

	class OpAdd extends Operation<List<String>, String> {
		private String what2Add;

		OpAdd(String what2Add) {
			this.what2Add = what2Add;
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public String execute(List<String> object) throws Exception {
			String result = null;
			if (object.add(what2Add))
				result = what2Add;
			return result;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((what2Add == null) ? 0 : what2Add.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			OpAdd other = (OpAdd) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (what2Add == null) {
				if (other.what2Add != null)
					return false;
			} else if (!what2Add.equals(other.what2Add))
				return false;
			return true;
		}

		private BagTest getOuterType() {
			return BagTest.this;
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BagTest other = (BagTest) obj;
		if (id != other.id)
			return false;
		return true;
	}

}

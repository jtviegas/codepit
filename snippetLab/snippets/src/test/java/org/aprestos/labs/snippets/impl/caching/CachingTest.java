package org.aprestos.labs.snippets.impl.caching;

import org.apache.jcs.JCS;
import org.apache.jcs.engine.control.CompositeCacheManager;
import org.apache.jcs.engine.stats.behavior.IStats;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class CachingTest {

	private static final String JCS_CONFIG_DIR = "/org/aprestos/labs/snippets/impl/caching/";
	private static final String key = "key", value = "value";
	private static final String[] configs = new String[] { "ObjectPersistingInMemoryAndDisk_x1.ccf",
			"ObjectPersistingInMemoryAndDisk_x2.ccf", "ObjectPersistingInMemoryAndDisk_x3.ccf", "ObjectPersistingInMemoryAndDisk_x4.ccf" 
			, "ObjectPersistingInMemoryAndDisk_x5.ccf" , "ObjectPersistingInMemoryAndDisk_x6.ccf"};
	private static final String[] expecteds = new String[] { null, null, null, null, null, null };
	private static final String region = "OUR_REGION";

	/**
	 * @throws Exception
	 * 
	 */
	@Test
	public void testObjectPersistingInMemory() throws Exception {

		System.out.println("-----------------------------------------------------------");
		JCS.setConfigFilename(JCS_CONFIG_DIR + "ObjectPersistingInMemory.ccf");
		JCS cache = JCS.getInstance(region);

		cache.put(key, value);
		Thread.sleep(5000);
		Assert.assertNotNull(cache.get(key));
		Assert.assertEquals(value, cache.get(key));

		System.out.println(cache.getStats());
		CompositeCacheManager.getInstance().shutDown();

		Thread.sleep(5000);

		JCS.setConfigFilename(JCS_CONFIG_DIR + "ObjectPersistingInMemory.ccf");
		cache = JCS.getInstance(region);
		Assert.assertNull(cache.get(key));

		System.out.println(cache.getStats());
		cache.clear();
		cache.dispose();
		CompositeCacheManager.getInstance().shutDown();

	}

	@Test
	public void testSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing_1() throws Exception {

		performSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing(JCS_CONFIG_DIR + configs[0], expecteds[0]);
	}
	
	@Test
	public void testSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing_2() throws Exception {

		performSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing(JCS_CONFIG_DIR + configs[1], expecteds[1]);
	}
	
	@Test
	public void testSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing_3() throws Exception {

		performSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing(JCS_CONFIG_DIR + configs[2], expecteds[2]);
	}
	
	@Test
	public void testSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing_4() throws Exception {

		performSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing(JCS_CONFIG_DIR + configs[3], expecteds[3]);
	}
	
	@Test
	public void testSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing_5() throws Exception {

		performSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing(JCS_CONFIG_DIR + configs[4], expecteds[4]);
	}
	
	@Test
	public void testSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing_6() throws Exception {

		performSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing(JCS_CONFIG_DIR + configs[5], expecteds[5]);
	}
	
	private void performSimpleObjectPersistingInMemoryAndRetrievalAfterFreeing(String configFile, String expected) throws Exception {

		System.out.println("-----------------------------------------------------------");

		System.out.println("testing with config file:" + configFile);
		JCS.setConfigFilename(configFile);
		JCS cache = JCS.getInstance(region);
		cache.put(key, value);
		Thread.sleep(5000);
		
		Assert.assertNotNull(cache.get(key));
		Assert.assertEquals(value, cache.get(key));

		IStats stats = cache.getStatistics().getAuxiliaryCacheStats()[0];
		cache.freeMemoryElements(Integer.parseInt(stats.getStatElements()[0].getData()));
		Thread.sleep(5000);
		
		Assert.assertNotNull(cache.get(key));
		Assert.assertEquals(value, cache.get(key));
		System.out.println(cache.getStats());
	}
	
	
	
	@Test
	public void testObjectPersistingInMemoryAndDisk_1() throws Exception {

		performObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[0], expecteds[0]);
	}

	@Test
	public void testObjectPersistingInMemoryAndDisk_2() throws Exception {

		performObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[1], expecteds[1]);
	}

	@Test
	public void testObjectPersistingInMemoryAndDisk_3() throws Exception {

		performObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[2], expecteds[2]);
	}
	
	@Test
	public void testObjectPersistingInMemoryAndDisk_4() throws Exception {

		performObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[3], expecteds[3]);
	}
	
	@Test
	public void testObjectPersistingInMemoryAndDisk_5() throws Exception {

		performObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[4], expecteds[4]);
	}
	
	@Test
	public void testObjectPersistingInMemoryAndDisk_6() throws Exception {

		performObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[5], expecteds[5]);
	}

	private void performObjectPersistingInMemoryAndDisk(String configFile, String expected) throws Exception {

		System.out.println("-----------------------------------------------------------");

		System.out.println("testing with config file:" + configFile);
		JCS.setConfigFilename(configFile);
		JCS cache = JCS.getInstance(region);
		cache.put(key, value);
		Thread.sleep(5000);
		Assert.assertNotNull(cache.get(key));
		Assert.assertEquals(value, cache.get(key));

		System.out.println(cache.getStats());
		CompositeCacheManager.getInstance().shutDown();

		Thread.sleep(5000);

		JCS.setConfigFilename(configFile);
		cache = JCS.getInstance(region);
		Assert.assertEquals(expected, cache.get(key));

		System.out.println(cache.getStats());
		cache.clear();
		cache.dispose();
		CompositeCacheManager.getInstance().shutDown();
	}

	@Test
	public void testSimplerObjectPersistingInMemoryAndDisk_1() throws Exception {

		performSimplerObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[0], expecteds[0]);
	}

	@Test
	public void testSimplerObjectPersistingInMemoryAndDisk_2() throws Exception {

		performSimplerObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[1], expecteds[1]);
	}

	@Test
	public void testSimplerObjectPersistingInMemoryAndDisk_3() throws Exception {

		performSimplerObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[2], expecteds[2]);
	}
	
	@Test
	public void testSimplerObjectPersistingInMemoryAndDisk_4() throws Exception {

		performSimplerObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[3], expecteds[3]);
	}

	@Test
	public void testSimplerObjectPersistingInMemoryAndDisk_5() throws Exception {

		performSimplerObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[4], expecteds[4]);
	}
	
	@Test
	public void testSimplerObjectPersistingInMemoryAndDisk_6() throws Exception {

		performSimplerObjectPersistingInMemoryAndDisk(JCS_CONFIG_DIR + configs[5], expecteds[5]);
	}
	
	private void performSimplerObjectPersistingInMemoryAndDisk(String configFile, String expected) throws Exception {

		System.out.println("-----------------------------------------------------------");

		System.out.println("testing with config file:" + configFile);
		JCS.setConfigFilename(configFile);
		JCS cache = JCS.getInstance(region);

		cache.put(key, value);
		Thread.sleep(5000);

		Assert.assertNotNull(cache.get(key));
		Assert.assertEquals(value, cache.get(key));

		System.out.println(cache.getStats());
		CompositeCacheManager.getInstance().shutDown();

		Thread.sleep(5000);

		JCS.setConfigFilename(configFile);
		cache = JCS.getInstance(region);
		Assert.assertEquals(expected, cache.get(key));
		System.out.println(cache.getStats());
	}

}

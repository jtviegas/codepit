package org.aprestos.labs.snippets.impl.caching.jcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.jcs.engine.control.CompositeCacheManager;

//@Component("snippet")
public class SnippetImpl implements org.aprestos.labs.snippets.interfaces.Snippet {
	private static final String TMP_DIR = "tmp/";
	private static final String JCS_CONFIG_DIR = "/org/aprestos/labs/snippets/impl/caching/jcs/";
	//private static final String JCS_CONFIG_DIR = "config/";
	private static final String DATA_FILE = TMP_DIR + "data.csv";
	private State state = new State("resource", 1415895613, "metric", 10.3);

	public void go() throws Exception {

		try {
			init();
			new CacheExperiment();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void init() throws Exception {

	}

	class CacheExperiment {

		public CacheExperiment() throws Exception {
			testObjectPersisting();
		}

		private void testObjectPersisting() throws Exception {
			
			JCS.setConfigFilename(JCS_CONFIG_DIR + "one.ccf");
			JCS cache = JCS.getInstance("STATES");
			cache.clear();
			cache.put("a", 123);
			Thread.sleep(5000);
			System.out.println(cache.getStats());
			CompositeCacheManager.getInstance().shutDown();
			Thread.sleep(5000);
			JCS.setConfigFilename(JCS_CONFIG_DIR + "one.ccf");
			cache = JCS.getInstance("STATES");
			System.out.println(cache.get("a"));
			System.out.println(cache.getStats());
			CompositeCacheManager.getInstance().shutDown();
		}
		
	

		private void session2putObject() throws CacheException, InterruptedException {

			//HashMap<String, Double> map = null;

			JCS.setConfigFilename(JCS_CONFIG_DIR + "cache3.ccf");
			JCS cache = JCS.getInstance("STATES");

			cache.clear();

			//map = new HashMap<String, Double>();
			//map.put(state.getMetric(), state.getValue());
			//cache.put(state.key(), map);
			cache.put("a", 123);
			Thread.sleep(20000);
			System.out.println(cache.getStats());
			CompositeCacheManager.getInstance().shutDown();
			//map = null;
			//cache = null;

		}

		@SuppressWarnings("unchecked")
		private void session2getObject() throws CacheException, InterruptedException {

			//HashMap<String, Double> map = null;

			JCS.setConfigFilename(JCS_CONFIG_DIR + "cache3.ccf");
			JCS cache = JCS.getInstance("STATES");
	
			//map = (HashMap<String, Double>) cache.get(state.key());
			//assert (map.get(state.getMetric()).equals(state.getValue()));
			System.out.println(cache.get("a"));
			System.out.println(cache.getStats());
			CompositeCacheManager.getInstance().shutDown();
			//map = null;
			//cache = null;

		}

		private void testGettingEvictedObjectB() throws CacheException, InterruptedException {

			State[] states = new State[] { new State("resource", 1415895613, "metric", 10.3) };

			HashMap<String, Double> map = null;

			JCS.setConfigFilename(JCS_CONFIG_DIR + "cache2.ccf");
			JCS cache = JCS.getInstance("stateCache");

			for (State state : states) {
				if (null == (map = (HashMap<String, Double>) cache.get(state.key())))
					map = new HashMap<String, Double>();

				map.put(state.getMetric(), state.getValue());
				cache.put(state.key(), map);
			}

			System.out.println(cache.getStats());
			CompositeCacheManager.getInstance().shutDown();
			Thread.sleep(10000);

		}

		private void anotherTest() {
			State state = null;
			HashMap<String, Double> map = null;
			int count = 0;

			State[] states = new State[] { new State("resource", 1415895634, "metric", 1.3),
					new State("resource", 1415895635, "metric", 2.3), new State("resource", 1415895636, "metric", 3.3),
					new State("resource", 1415895637, "metric", 4.3), new State("resource", 1415895638, "metric", 5.3),
					new State("resource", 1415895639, "metric", 6.3), new State("resource", 1415895610, "metric", 7.3),
					new State("resource", 1415895611, "metric", 8.3), new State("resource", 1415895612, "metric", 9.3),
					new State("resource", 1415895613, "metric", 10.3) };

			try {
				JCS.setConfigFilename(JCS_CONFIG_DIR + "/cache.ccf");
				JCS cache = JCS.getInstance("stateCache");

				DataFileParser parser = new DataFileParser(new File(DATA_FILE));
				while (parser.hasNext()) {
					state = parser.next();
					if (null == (map = (HashMap<String, Double>) cache.get(state.key())))
						map = new HashMap<String, Double>();

					map.put(state.getMetric(), state.getValue());
					cache.put(state.key(), map);
					count++;
				}

				System.out.println(cache.getStats());
				Thread.sleep(12000);
				System.out.println(cache.getStats());

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(count);
		}

	}

	class DataFileParser {

		private BufferedReader reader;
		private String line;

		DataFileParser(File f) throws FileNotFoundException {
			reader = new BufferedReader(new FileReader(f));
		}

		boolean hasNext() throws IOException {
			return (null != (line = reader.readLine()));
		}

		State next() {
			State result = null;
			String[] fields = null;
			line = line.replaceAll("\\[", "").replaceAll("\\]", "");
			fields = line.split(",");
			result = new State(fields[7], Long.parseLong(fields[10].replaceAll("\"", "")), fields[8],
					Double.parseDouble(fields[9].replaceAll("\"", "")));
			return result;
		}

	}

}

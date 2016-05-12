package org.aprestos.labs.snippets.impl.algo.assorted.topn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TopN {
	private static final int BATCH_SIZE = 100;
	
	
	public LinkedList topN(int numOfHighValues, String file) throws Exception {
		
		BufferedReader bufferedReader = null;
		Sorter sorter = new Sorter();
		LinkedList list = null;
		//open file
		try {
			bufferedReader = new BufferedReader(new FileReader(new File(file)));
			String line = null;
			int[] items = new int[BATCH_SIZE];
			int index = 0;
			while ((line = bufferedReader.readLine()) != null) {
				
				items[index++] = Integer.parseInt(line);
				if(0 == (index % BATCH_SIZE) ){
					if(null == list)
						list = LinkedList.subSet(sorter.heapsort(items), numOfHighValues);
					else
						list = LinkedList.customMerge(list, sorter.heapsort(items), numOfHighValues);
					
					items = new int[BATCH_SIZE];
					index = 0;
				}
			}
			
			if(index > 0){
				if(null == list)
					list = LinkedList.subSet(sorter.heapsort(items), numOfHighValues);
				else
					list = LinkedList.customMerge(list, sorter.heapsort(items), numOfHighValues);
			}
		} 
		finally {
			if(null != bufferedReader)
				try { bufferedReader.close(); } catch (Exception e1) { e1.printStackTrace(); }
		}

		return list;
		
	}

}

package org.aprestos.labs.tests.caching.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.aprestos.labs.tests.caching.model.ProcessingNode;
import org.junit.Test;

public abstract class MemoryGraphCacheTestAbstract extends GraphCacheTestAbstract {

	protected static final String NODE_NAME_PATTERN = "%s|%d";
	protected static final String NODE_CONTENT_MSG = "node %s has the following children:";
	private Random random = new Random();
	private int overallChildrenCount = 0;

	public MemoryGraphCacheTestAbstract() {
		super();
	}

	private ProcessingNode handleBirths(ProcessingNode pn){
		
		int chidrenNum = randomBirthNumber();
		overallChildrenCount += chidrenNum;
		ProcessingNode child = null;
		
		for(int i = 0 ; i < chidrenNum ; i++ ){
			child = new ProcessingNode(String.format(NODE_NAME_PATTERN, pn.getName(), i));
			pn.getChildren().add(handleBirths(child));
		}
		
		return pn;
	}
	
	private int randomBirthNumber(){
		int births = 0;
		int num = random.nextInt(12);
		//shall we have births? nearly 50% chance
		if( (num%3) >= 2){
			//then we'll always have between 2 and 3 births
			births = num/3; 
		}
		return births;
	}
	
	@Override
	@Test
	public void testLoadOfPNs() throws Exception {

		List<ProcessingNode> samples = new ArrayList<ProcessingNode>();
		ProcessingNode node = null;
		long startLoadTS = 0, endLoadTS = 0, startReadTS = 0, endReadTS = 0;
		int childrenRead = 0, nodesAdded = 0;;

		cache.clear();
		overallChildrenCount = 0;

		startLoadTS = System.currentTimeMillis();
		while (feeder.hasNext()) {
			
			//assign children randomly and recursively
			node = handleBirths(feeder.next());
			if(random.nextBoolean())
				samples.add(node);
			
			cache.put(node.getName(), node);
			//System.out.println(node.toString());
			nodesAdded++;
		}
		startReadTS = endLoadTS = System.currentTimeMillis();
		for (ProcessingNode n : samples) {
			//System.out.println(String.format(NODE_CONTENT_MSG, n.getName()));
			for (ProcessingNode ch : cache.getChildren(n.getName())) {
				//System.out.println("\t" + ch.toString());
				childrenRead++;
			}
		}
		endReadTS = System.currentTimeMillis();
		
		System.out.println(String.format("nodes added    : %d with %d children | time taken: %d milliseconds", nodesAdded, overallChildrenCount, (endLoadTS - startLoadTS))); 
		System.out.println(String.format("nodes retrieved: %d with %d children | time taken: %d milliseconds", samples.size(), childrenRead,(endReadTS - startReadTS))); 
		
		
	}

}

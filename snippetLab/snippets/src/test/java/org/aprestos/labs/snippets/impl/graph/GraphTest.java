package org.aprestos.labs.snippets.impl.graph;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class GraphTest {
   
    
    
    
    @Test
    public void testGraphs2()
    {
		doTest(1, 2, true);
    }
    
	@Test
    public void testGraphs3()
    {
		doTest(1, 3, true);
    }
	
    @Test
    public void testGraphs100()
    {
		doTest(1, 100, false);
    }
    
    @Test
    public void testGraphs1000()
    {
		doTest(1, 1000, false);
    }
    
    @Test
    public void testGraphs50000()
    {
		doTest(1, 10000, false);
    }
    
	private void doTest(int childrenNum, int recursions, boolean print){
		
		System.out.println(String.format("doTest: %d children and %d recursions",childrenNum, recursions));
		
		Node zeroLL = new Node("0",0);
        Node zeroAL = new Node("0",1);
        
        long tsLLstartAdd = System.currentTimeMillis();
        Node lastLL = addChildrenRecursively2(zeroLL,childrenNum,0,recursions);
        long tsLLendAdd = System.currentTimeMillis();
        long tsALstartAdd = System.currentTimeMillis();
        Node lastAL = addChildrenRecursively2(zeroAL,childrenNum,0,recursions);
        long tsALendAdd = System.currentTimeMillis();
        
        Long tsLLstartfind = System.currentTimeMillis();
        Assert.assertNotNull(zeroLL.findChild(lastLL));
        Assert.assertEquals(lastLL, zeroLL.findChild(lastLL));
        Long tsLLendfind = System.currentTimeMillis();
        Long tsALstartfind = System.currentTimeMillis();
        Assert.assertNotNull(zeroAL.findChild(lastAL));
        Assert.assertEquals(lastAL, zeroAL.findChild(lastAL));
        Long tsALendfind = System.currentTimeMillis();
        
        if(print){
        	System.out.println("linked list: " + zeroLL);
        	System.out.println("array list: " + zeroAL);
        }
        
        System.out.println(String.format("linkedlist add took %d milliseconds", tsLLendAdd - tsLLstartAdd));
        System.out.println(String.format("arraylist add took %d milliseconds", tsALendAdd - tsALstartAdd));
        
        System.out.println(String.format("linkedlist find took %d milliseconds", tsLLendfind - tsLLstartfind));
        System.out.println(String.format("arraylist find took %d milliseconds", tsALendfind - tsALstartfind));
        
	}
	

	
	private Node addChildrenRecursively2(Node n, int numOfChilds, int nodeIndex, int maxRecursion){
    	if(nodeIndex == maxRecursion)
    		return n;
    	else {
    		Node last = null;
    		for(int i=0;i<numOfChilds;i++){
    			last = new Node(String.format("%s|%s", Integer.toString(nodeIndex),Integer.toString(i)));
    			n.addChild(last);
    		}
    		return addChildrenRecursively2(last, numOfChilds, ++nodeIndex, maxRecursion);
    	}
    }
}


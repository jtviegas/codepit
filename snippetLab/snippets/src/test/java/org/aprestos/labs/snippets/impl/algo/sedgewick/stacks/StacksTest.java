package org.aprestos.labs.snippets.impl.algo.sedgewick.stacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Assert;
import org.junit.Test;

public class StacksTest {
    

    @Test
    public void testInteger1() {

	List<Integer> items = new ArrayList<Integer>();
	List<Boolean> operations = new ArrayList<Boolean>();

	items.addAll(Arrays.asList(12,0,0,
		0, 0,11,
		0,3, 0,
		0,3,0, 
		21));
	operations.addAll(Arrays.asList(true, false, false, 
		false, false, true, 
		false, true, false, 
		true, true, false, 
		true));
		
	Stack<Integer> ll = new StackLinkedList<Integer>(); 
	Stack<Integer> ra = new StackResizingArray<Integer>(); 

	StopWatch watch = new StopWatch();
	watch.start();
	test(ll, items, operations);
	watch.stop();
	System.out.println("QueueLinkedList: " + watch.getTime());
	Assert.assertTrue(ll.size() == 2);
	watch.reset();
	watch.start();
	test(ra, items, operations);
	watch.stop();
	Assert.assertTrue(ra.size() == 2);
	System.out.println("QueueResizingArray: " + watch.getTime());
    }
    
    @Test
    public void testInteger2() {
	
	int iterations = 10 * 1000 * 1000;
	
	List<Integer> items = new ArrayList<Integer>();
	List<Boolean> operations = new ArrayList<Boolean>();

	Random r = new Random();
	int i = 0;
	while (i++ < iterations) {
	    items.add(r.nextInt(iterations));
	    operations.add(r.nextBoolean());
	    /*
	     * try { Thread.currentThread().sleep(100); } catch
	     * (InterruptedException e) { // TODO Auto-generated catch block
	     * e.printStackTrace(); }
	     */
	}
	
	Stack<Integer> ll = new StackLinkedList<Integer>(); 
	Stack<Integer> ra = new StackResizingArray<Integer>(); 
	java.util.Stack<Integer> js = new java.util.Stack<Integer>();
	
	StopWatch watch = new StopWatch();
	watch.start();
	test(ll, items, operations);
	watch.stop();
	System.out.println("StackLinkedList: " + watch.getTime());
	watch.reset();
	watch.start();
	test(ra, items, operations);
	watch.stop();
	System.out.println("StackResizingArray: " + watch.getTime());
	watch.reset();
	watch.start();
	test(js, items, operations);
	watch.stop();
	System.out.println("java.util.Stack: " + watch.getTime());
    }
    
    private void test(Stack<Integer> stack, List<Integer> items,
	    List<Boolean> operations) {
	for(int i = 0; i < operations.size(); i++){
	    Integer o = items.get(i);
	    if(operations.get(i))
		stack.push(o);
	    else
		stack.pop();
	    
	    if(0 == i % 1000000){
		System.out.println("Stack size: " + stack.size());
	    }
	}
    }
    
    private void test(java.util.Stack<Integer> stack, List<Integer> items,
	    List<Boolean> operations) {
	for(int i = 0; i < operations.size(); i++){
	    Integer o = items.get(i);
	    if(operations.get(i))
		stack.push(o);
	    else
		try {
		    stack.pop();
		} catch (EmptyStackException e) {}
	    
	    if(0 == i % 1000000){
		System.out.println("Stack size: " + stack.size());
	    }
	}
    }

}

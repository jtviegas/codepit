package org.aprestos.labs.snippets.impl.algo.sedgewick.queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

import org.apache.commons.lang.time.StopWatch;
import org.aprestos.labs.snippets.impl.algo.sedgewick.queues.Queue;
import org.aprestos.labs.snippets.impl.algo.sedgewick.queues.QueueLinkedList;
import org.aprestos.labs.snippets.impl.algo.sedgewick.queues.QueueResizingArray;
import org.junit.Assert;
import org.junit.Test;

public class QueuesTest {

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
		
	Queue<Integer> ll = new QueueLinkedList<Integer>();
	Queue<Integer> ra = new QueueResizingArray<Integer>();

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

	Queue<Integer> ll = new QueueLinkedList<Integer>();
	Queue<Integer> ra = new QueueResizingArray<Integer>();
	PriorityQueue<Integer> jq = new PriorityQueue<Integer>();

	StopWatch watch = new StopWatch();
	watch.start();
	test(ll, items, operations);
	watch.stop();
	System.out.println("QueueLinkedList: " + watch.getTime());
	watch.reset();
	watch.start();
	test(ra, items, operations);
	watch.stop();
	System.out.println("QueueResizingArray: " + watch.getTime());
	watch.reset();
	watch.start();
	test(jq, items, operations);
	watch.stop();
	System.out.println("PriorityQueue: " + watch.getTime());
    }

    private void test(Queue<Integer> queue, List<Integer> items,
	    List<Boolean> operations) {
	for (int i = 0; i < operations.size(); i++) {
	    Integer o = items.get(i);
	    if (operations.get(i))
		queue.enqueue(o);
	    else
		queue.dequeue();

	    if (0 == i % 1000000) {
		System.out.println("Queue size: " + queue.size());
	    }
	}
    }
    
    private void test(PriorityQueue<Integer> queue, List<Integer> items,
	    List<Boolean> operations) {
	for (int i = 0; i < operations.size(); i++) {
	    Integer o = items.get(i);
	    if (operations.get(i))
		queue.add(o);
	    else
		try {
		    queue.remove();
		} catch (NoSuchElementException e) { }

	    if (0 == i % 1000000) {
		System.out.println("Queue size: " + queue.size());
	    }
	}
    }

}

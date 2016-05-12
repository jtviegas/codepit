package org.aprestos.labs.snippets.impl.algo.sedgewick.queues;

import java.util.Arrays;

public class QueueResizingArray<T> implements Queue<T>{
    private static final int expandFactor = 2;
    @SuppressWarnings("unchecked")
    private T[] arr = (T[])new Object[1];
    private int tail = -1;
    private int head = -1;
    private boolean debug = false;
    
    public void enqueue(T o) {
	if((tail + 1) == arr.length){
	    //are in the threshold? then expand
	    resize(expandFactor);
	}
	arr[++tail] = o;
	if(0 == tail){
	    //initial element
	    head = 0;
	}
	
	if(debug)
	    print("after enqueue:" + getStatus());

    }
    public T dequeue() {
	
	if(-1 == head || null == arr[head])
	    return null;
	//get the desired element
	T o = (T)arr[head];
	//clean array reference
	arr[head] = null;
	if(head == tail){
	    if(0 == head){
		//back to initial stage
		head--;
		tail--;
	    }
	}
	else {
	    head++;
	}

	if(debug)
	    print("after dequeue:" + getStatus());

	return o;
    }
    
    private void print(String msg){
	System.out.println(msg);
    }
    private String getStatus(){
	StringBuffer sb = new StringBuffer();
	sb.append("\t").append(Arrays.asList(arr)).append(String.format("[head: %d]", head)).append(String.format("   [tail: %d]", tail));
	return sb.toString();
    }
    
    public int size() {
	return (tail - head + 1);
    }
    private void resize(double factor){
	@SuppressWarnings("unchecked")
	T[] newArr = (T[]) new Object[(int)( (tail - head + 1) * factor)];
	System.arraycopy(arr, head, newArr, 0, tail - head + 1 );
	
	tail = tail - head;
	head = 0;
	arr = newArr;
	if(debug)
	    print("after resize:" + getStatus());

    }
    
    public boolean isEmpty() {
	return (null == arr[head]);
    }

}

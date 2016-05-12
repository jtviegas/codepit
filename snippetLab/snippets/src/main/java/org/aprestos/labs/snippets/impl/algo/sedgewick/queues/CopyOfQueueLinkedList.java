package org.aprestos.labs.snippets.impl.algo.sedgewick.queues;

public class CopyOfQueueLinkedList<T> implements Queue<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;
    private int maxSize;
    
    public CopyOfQueueLinkedList(){}
    
    public CopyOfQueueLinkedList(int maxSize){
	this.maxSize = maxSize;
    }
    
    
    private static class Node<T> {
	T item;
	Node<T> next;
	Node(T o){
	    this.item = o; 
	}
	@SuppressWarnings("unused")
	Node(T o, Node<T> next){
	    this.item = o; 
	    this.next = next;
	}
    }
    
    public void enqueue(T o) {
	
	if(null == first){
	    first = new Node<T>(o);
	    last = first;
	}
	else {
	    Node<T> oldLast = last;
	    oldLast.next = new Node<T>(o);
	    last = oldLast.next;
	}
	size++;
	
	if((0 < maxSize) && (size > maxSize))
	    dequeue();
	
    }

    public T dequeue() {
	
	if(null == first){
	    return null;
	}
	else {  
	    Node<T> oldFirst = first;
	    first = oldFirst.next;
	    //clean the ref
	    oldFirst.next = null;
	    size--;
	    T o = oldFirst.item;
	    oldFirst = null;
	    return o;
	}
    }

    public int size() {
	return size;
    }

    public boolean isEmpty() {
	return (null == first);
    }

}

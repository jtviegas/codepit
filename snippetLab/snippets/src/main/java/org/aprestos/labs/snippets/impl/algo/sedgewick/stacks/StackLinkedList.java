package org.aprestos.labs.snippets.impl.algo.sedgewick.stacks;

public class StackLinkedList<T> implements Stack<T> {

    private Node<T> first;
    private int size;
    
    private static class Node<T> {
	@SuppressWarnings("unused")
	T item;
	@SuppressWarnings({ "rawtypes", "unused" })
	Node next;
	
	Node(T o){
	    this.item = o; 
	}
	
	Node(T o, Node<T> next){
	    this.item = o; 
	    this.next = next;
	}
	
    }
    
    public void push(T o) {
	if(null == first)
	    first = new Node<T>(o);
	else {
	    Node<T> oldFirst = first;
	    first = new Node<T>(o, oldFirst);
	}
	size++;
    }

    public T pop() {
	
	if(null != first){
	    T o = first.item;
	    
	    if(null == first.next){
		first = null;
	    }
	    else {
		Node<T> newFirst = first.next;
		first = newFirst;
	    }
	    size--;
	    return o;
	}
	else    
	    return null;
    }

    public int size() {
	return size;
    }

}

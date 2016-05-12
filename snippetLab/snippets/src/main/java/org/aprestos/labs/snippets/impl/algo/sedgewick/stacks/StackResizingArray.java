package org.aprestos.labs.snippets.impl.algo.sedgewick.stacks;

public class StackResizingArray<T> implements Stack<T>{

    private int index = -1;
    @SuppressWarnings("unchecked")
    private T[] arr = (T[])new Object[1];
    
    public void push(T o) {
	if(index == (arr.length - 1) )
	    resize(2.0);
	arr[++index] = o;
    }

    public T pop() {
	if(0 > index)
	    return null;
	
	T o = (T)arr[index--];
	if(index == (arr.length/4))
	    resize(0.5);
	
	return o;
    }

    public int size() {
	return (index+1);
    }
    
    private void resize(double factor){
	@SuppressWarnings("unchecked")
	T[] newArr = (T[])new Object[(int)(arr.length*factor)];
	for(int i = 0;  i < arr.length && i < newArr.length; i++)
	    newArr[i] = arr[i];
	
	arr = newArr;
    }

}

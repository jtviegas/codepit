package org.aprestos.labs.challenges.stacksandqueues;

public class Heap {

  private static final int SIZE=8;
  int[] data = new int[SIZE];
  int n = -1;
  
  public Heap() {}
  public Heap(int v) {
    data[++n] = v;
  }
  
  int parentIndex(int order) {
    if( order == 1 ) return (-1);
    else return  getIndex((int)( order/2 ));
  }
  
  int youngChildIndex(int order) {
    return getIndex(2*order);
  }
  
  int oldChildIndex(int order) {
    return getIndex((2*order)+1);
  }
  
  int getIndex(int order) {
    return order - 1;
  }
  
  void insert(int v){
    if(n == SIZE)
      throw new IllegalArgumentException("heap overflow");
    
    data[++n] = v;
    bubbleUp(n+1);
    
  }
  
  void swap(int idx1, int idx2) {
    int v = data[idx1];
    data[idx1] = data[idx2];
    data[idx2] = v;
  }
  
  void bubbleUp(int order) {
    int parentIndex = parentIndex(order);
    if( -1 == parentIndex ) return; //already at the root
    int index = getIndex(order);
    if( data[ parentIndex ] > data[ index ] ) {
      swap(parentIndex, index);
      bubbleUp(parentIndex+1);
    }
  }
  
  void bubbleDown(int order) {
    int index = getIndex(order);
    int young = youngChildIndex(order);
    
    if( n < young ) return;
    
    int minIndex = index;
    
    for( int i=young; i<=(young+1) && i<=n; i++ )
      if( data[minIndex] > data[i] )
        minIndex = i;
    
    if(minIndex != index) {
      swap(minIndex, index);
      bubbleDown(minIndex+1);
    }
   
  }
  
  int extractMin() {
    int r = -1;
    
    if(0 > n)
      throw new IllegalArgumentException("empty heap");
    
    r = data[0];
    data[0] = data[n];
    data[n] = 0;
    n--;
    
    bubbleDown(1);
    
    return r;
  }
  
  static Heap make(int[] values) {
    Heap r = new Heap();
    for(int i=0; i<values.length; i++)
      r.insert(values[i]);
    
    return r;
  }
  
  static int[] heapSort(int[] a) {
    
    int[] r = new int[a.length];
    Heap h = make(a);
    for(int i=0; i< a.length;i++)
      r[i] = h.extractMin();
    
    return r;
  }
  

}

package org.aprestos.labs.challenges.cci.stacksandqueues;

import org.aprestos.labs.challenges.Node;

public class Queue {
  
  Node first, last;
  
  public Queue(Integer value) {
    Node f = new Node(value);
    first = f;
    last = f;
  }
  
  void enqueue(Integer o){
    Node n = new Node(o);
    if(null == first) {
      first = n;
      last = n;
    }
    else {
      last.next = n;
      last = n;
    }
  }
  
  Integer dequeue() {
    Integer r = null;
    
    if(null != first ) {
      r = first.data;
      first = first.next;
    }
    
    return r;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((last == null) ? 0 : last.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Queue other = (Queue) obj;
    if (first == null) {
      if (other.first != null)
        return false;
    } else if (!first.equals(other.first))
      return false;
    if (last == null) {
      if (other.last != null)
        return false;
    } else if (!last.equals(other.last))
      return false;
    return true;
  }

}

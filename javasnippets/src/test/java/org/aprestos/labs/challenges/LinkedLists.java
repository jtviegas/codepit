package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkedLists {

  private Node deleteNode(Node head, int d) {
    Node n = head;

    if (n.data == d)
      return n.next;

    while (null != n.next) {
      if (n.next.data == d) {
        n.next = n.next.next;
        return head;
      }
      n = n.next;
    }

    return head;
  }

  class Node {

    Node next = null;

    int data;

    public Node(int d) {
      this.data = d;
    }

    Node appendToTail(int d) {
      Node n = new Node(d);
      Node _next = this;
      
      while (null != _next.next) {
        _next = _next.next;
      }
        
      _next.next = n;
      return this;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + getOuterType().hashCode();
      result = prime * result + data;
      result = prime * result + ((next == null) ? 0 : next.hashCode());
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
      Node other = (Node) obj;
      if (!getOuterType().equals(other.getOuterType()))
        return false;
      if (data != other.data)
        return false;
      if (next == null) {
        if (other.next != null)
          return false;
      } else if (!next.equals(other.next))
        return false;
      return true;
    }

    private LinkedLists getOuterType() {
      return LinkedLists.this;
    }
    
    

  }

  @Test
  public void test() throws Exception {

    Assert.assertEquals(new Node(1).appendToTail(2), 
        removeDuplicates_2_1(new Node(1).appendToTail(2)));
    Assert.assertEquals(new Node(1).appendToTail(2), 
        removeDuplicates_2_1(new Node(1).appendToTail(2).appendToTail(1)));
    
    Assert.assertEquals(new Node(3).appendToTail(2).appendToTail(4).appendToTail(7).appendToTail(6), 
        removeDuplicates_2_1(new Node(3).appendToTail(2).appendToTail(4).appendToTail(7).appendToTail(6).appendToTail(7)));

     Assert.assertEquals(new Node(2).appendToTail(4).appendToTail(7).appendToTail(6).appendToTail(7), 
        getNthToLast_2_2(new Node(1).appendToTail(2).appendToTail(1).appendToTail(2).appendToTail(4).appendToTail(7).appendToTail(6).appendToTail(7), 5));
     
     Assert.assertEquals(new Node(6).appendToTail(7), 
        getNthToLast_2_2(new Node(1).appendToTail(2).appendToTail(1).appendToTail(2).appendToTail(4).appendToTail(7).appendToTail(6).appendToTail(7), 2));
     
     Assert.assertEquals(new Node(8).appendToTail(0).appendToTail(8), 
        getNodesInvertedSum_2_4(new Node(3).appendToTail(1).appendToTail(5), new Node(5).appendToTail(9).appendToTail(2), 0));

  }
  
  private Node getNodesInvertedSum_2_4(Node n1, Node n2, int carry) {
    
    if(null == n1 && null == n2) return null;
    
    int r = carry;
    if(null != n1) {
      r += n1.data;
    }
    if(null != n2) {
      r += n2.data;
    }
    
    if( 9 < r) {
      carry = r/10;
      r = r%10;
    }
    else
      carry = 0;
    
    Node result = new Node(r);
    result.next = getNodesInvertedSum_2_4(n1.next,n2.next, carry);
    
    return result;
  }
  
  private Node getNthToLast_2_2(Node head, int n) {
    
    Node p1 = head;
    Node p2 = head;
    
    int _n = 0;
    while( p2 != null && ++_n < n ) {
      p2 = p2.next;
    }
    
    if( _n != n )
      throw new IllegalArgumentException("not enough length in the list");
    
    while( p2.next != null ) {
      p1 = p1.next;
      p2 = p2.next;
    }
    
    return p1;
  }
  
  
  private Node removeDuplicates_2_1(Node head) {
    if(null == head)
      throw new IllegalArgumentException("argument cannot be null");
    
    Node inspector = null;
    Node previous = head;
    Node runner = head;
    
    while( null != (runner.next) ) {
      previous = runner;
      runner = runner.next;
      
      inspector = head;
      while( inspector != runner ) {
        if( inspector.data == runner.data ) {
          previous.next = runner.next;
          runner = previous;
          break;
        }
        inspector = inspector.next;
      }
      
    }
    
    return head;
  }

}

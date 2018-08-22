package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkedLists {




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
     
        Node n = new Node(6);
    Node n2 = new Node(4);
    Node n3 = new Node(3).appendToTail(2);
    n.next = n2;
    n2.next = n3;
    Node n4 = new Node(6);
    n4.next = n3;
    deleteNodeInListWithAccessToThatOneOnly_2_3(n2);
    Assert.assertEquals(n, n4);
    
     Assert.assertEquals(new Node(8).appendToTail(0).appendToTail(8), 
        getNodesInvertedSum_2_4(new Node(3).appendToTail(1).appendToTail(5), new Node(5).appendToTail(9).appendToTail(2), 0));

  }
  
  // TODO
  @SuppressWarnings("unused")
  private void getBeginningOfLoop_2_4(Node n1) {
    
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
  
    private Node deleteNodeInListWithAccessToThatOneOnly_2_3(Node node) {

    Node next = node.next;
    node.data = next.data;
    node.next = next.next;

    return node;
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

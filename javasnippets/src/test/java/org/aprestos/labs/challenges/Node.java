package org.aprestos.labs.challenges;

public class Node {

  Node next = null;
  int data;

  public Node(int d) {
    this.data = d;
  }

  static Node deleteNode(Node head, int d) {
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
    if (data != other.data)
      return false;
    if (next == null) {
      if (other.next != null)
        return false;
    } else if (!next.equals(other.next))
      return false;
    return true;
  }
  
  

}

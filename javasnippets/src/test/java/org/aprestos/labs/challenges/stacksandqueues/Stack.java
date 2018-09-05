package org.aprestos.labs.challenges.stacksandqueues;

import org.aprestos.labs.challenges.Node;

public class Stack {

  Node top;

  public Stack(Integer val) {
    this.top = new Node(val);
  }

  Integer pop() {
    Integer r = null;
    if (null != top) {
      r = top.data;
      top = top.next;
    }
    return r;
  }

  void push(Integer value) {
    Node newTop = new Node(value);
    newTop.next = top;
    top = newTop;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((top == null) ? 0 : top.hashCode());
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
    Stack other = (Stack) obj;
    if (top == null) {
      if (other.top != null)
        return false;
    } else if (!top.equals(other.top))
      return false;
    return true;
  }

}

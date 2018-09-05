package org.aprestos.labs.challenges.stacksandqueues;

import java.util.Random;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StacksAndQueues {
 
  private static final Random rand = new Random();
  
  
  
  
  @Test
  public void testThreeStacks() throws Exception {

    ThreeStacks o = new ThreeStacks(3);
    
    
    o.push1(rand.nextInt());
    int i = rand.nextInt();
    o.push1(i);
    Assert.assertEquals(i, o.pop1());
    o.push1(rand.nextInt());
    
    o.push2(rand.nextInt());
    i = rand.nextInt();
    o.push2(i);
    o.push2(rand.nextInt());
    o.pop2();
    Assert.assertEquals(i, o.pop2());
    
    i = rand.nextInt();
    o.push3(i);
    o.push3(rand.nextInt());
    o.push3(rand.nextInt());
    try {
      o.push3(rand.nextInt());
      Assert.fail();
    } catch (IllegalArgumentException e) { }
    o.pop3();
    o.pop3();
    Assert.assertEquals(i, o.pop3());
    Assert.assertEquals(0, o.pop3());
    
    
    
    
    
  }
  
  
  @Test
  public void testHeap() throws Exception {
    
    
    Assert.assertArrayEquals(strToIntArray("1 2 3 4 5 6 7 8"), Heap.heapSort(strToIntArray("4 7 3 6 2 8 1 5")));
    Assert.assertArrayEquals(strToIntArray("0 1 5 5 8 8"), Heap.heapSort(strToIntArray("1 0 8 8 5 5")));
    Assert.assertArrayEquals(strToIntArray("0 0 0"), Heap.heapSort(strToIntArray("0 0 0")));
    Assert.assertArrayEquals(strToIntArray("1 2 3 4 5 6 7 8"), Heap.heapSort(strToIntArray("6 8 7 1 2 3 4 5")));
    
  }


  
  private int[] strToIntArray(String s) {

    String[] _s = s.split(" ");
    int[] r = new int[_s.length];
    int index = 0;
    for (String o : _s)
      r[index++] = Integer.parseInt(o.trim());

    return r;
  }

}

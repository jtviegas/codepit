package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StacksAndQueues {
 

  @Test
  public void test() throws Exception {
    
    Assert.assertEquals(true, doit());
  }
  
  private boolean doit() {
    return true;
  }

  
  
  class ThreeStacks{
    int p1=-1, p2=-1, p3=-1;
    
    int s[];
    
    ThreeStacks(int n){
      this.s = new int[n];
    }
    
    int getPointer(int index, int pointer) {
      return (s.length/3)*index-1 + pointer; 
    }
    Integer pop(int index){
      int pointer = ( 1 == index ? p1 : (2 == index ? p2 : p3)  );
      Integer r = null;
      if( -1 != pointer ) {
        r = s[getPointer(index, pointer)];
        p1--;
      }
      return r;
    }
    
    Integer pop1(){
      return pop(1);
    }
    
    
  }

}

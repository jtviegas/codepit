package org.aprestos.labs.challenges;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArraysAndStrings {

  @Test
  public void test() throws Exception {

    Assert.assertEquals(true, doesStringHasOnlyUniqueChars_1_1("asdfghjklpouytrewq"));
    Assert.assertEquals(false, doesStringHasOnlyUniqueChars_1_1("asdfghjklpouytrewqa"));
    
    Assert.assertEquals("aqwertyuoplkjhgfdsa", reverseString_1_2("asdfghjklpouytrewqa"));

  }

  private String reverseString_1_2(String s) {
    
    char[] a = s.toCharArray();
    char[] r = new char[a.length];
    
    for( int i = a.length-1, j = 0; i >= 0; i--, j++ )
      r[j] = a[i];
    
    return new String(r);
  }
  
  private boolean doesStringHasOnlyUniqueChars_1_1(String s) {
    boolean r = true;
    
    int[] scores = new int[Character.MAX_CODE_POINT-Character.MIN_CODE_POINT+1];

    for (char c: s.toCharArray()) {
      if( 0 == scores[c] )
        scores[c] = 1;
      else
        return false;
    }
    
    return r;
  }

  
}

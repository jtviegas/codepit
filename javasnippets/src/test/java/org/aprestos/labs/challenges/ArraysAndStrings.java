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

    Assert.assertEquals("asdfghjklpouytrewq", removeDuplicatesInString_1_3("asdfgghjklpouuytrewqa"));
    Assert.assertEquals("asdfghjklpouytrewq", removeDuplicatesInString2_1_3("asdfgghjklpouuytrewqa"));
    
    Assert.assertEquals(true, isAnagram_1_4("asdfgghjklpouuytrewqa", "asdwfghjklpouuytreqag"));
    Assert.assertEquals(false, isAnagram_1_4("asdfgghjklpouuytrewqa", "asdwfghjklpouuytreqagW"));
  }

  private boolean isAnagram_1_4(String s1,String s2) {
    
    int[] map1 = new int[256];
    int[] map2 = new int[256];
    
    for(char c: s1.toCharArray()) 
      map1[c]++;
    for(char c: s2.toCharArray()) 
      map2[c]++;
    
    for(int i=0;i<256;i++)
      if( map1[i] != map2[i] ) return false;
    
    return true;
  }

  private String removeDuplicatesInString2_1_3(String s) {
    
    if( null == s || 2>s.length())
      return s;
    char[] _s = s.toCharArray();

    int tail = 1;
    for(int i=1; i < _s.length; i++) {
      
      int j;
      for(j=0 ; j < tail; j++) 
        if(_s[j] == _s[i]) break; //there was a duplicate
       
      
      if( j == tail ) {
        //there were no duplicates
        //move the tail
        _s[tail] = _s[i];
        ++tail;
      }
      
    }
    
    for(int i=tail; i<_s.length;i++)
      _s[i] = 0;
    
    
    return new String(_s).trim();
  }
  
  
  private String removeDuplicatesInString_1_3(String s) {

    int[] scores = new int[256];
    char[] _s = s.toCharArray();

    for (int i = 0; i < _s.length; i++) {
      char c = _s[i];
      if (0 != c) {
        if (0 == scores[c])
          scores[c] = 1;
        else {
          for (int destinIndex = i, sourceIndex = i + 1; destinIndex < _s.length; destinIndex++, sourceIndex++)
            _s[destinIndex] = (_s.length == sourceIndex ? 0 : _s[sourceIndex]);
        }
      }

    }

    return new String(_s).trim();
  }

  private String reverseString_1_2(String s) {

    char[] a = s.toCharArray();
    char[] r = new char[a.length];

    for (int i = a.length - 1, j = 0; i >= 0; i--, j++)
      r[j] = a[i];

    return new String(r);
  }

  private boolean doesStringHasOnlyUniqueChars_1_1(String s) {
    boolean r = true;

    // int[] scores = new int[Character.MAX_CODE_POINT-Character.MIN_CODE_POINT+1];
    int[] scores = new int[256];

    for (char c : s.toCharArray()) {
      if (0 == scores[c])
        scores[c] = 1;
      else
        return false;
    }

    return r;
  }

}

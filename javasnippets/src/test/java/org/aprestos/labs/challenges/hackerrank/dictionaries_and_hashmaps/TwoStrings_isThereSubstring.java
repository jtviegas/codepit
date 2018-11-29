package org.aprestos.labs.challenges.hackerrank.dictionaries_and_hashmaps;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TwoStrings_isThereSubstring {

	@Test
	public void test1() throws Exception {
		Assert.assertEquals("YES", twoStrings("hello","world"));
		Assert.assertEquals("NO", twoStrings("hi","world"));
		
		Assert.assertEquals("YES", twoStrings("hackerrankcommunity","cdecdecdecde"));

	}
	
	@Test
  public void test2() throws Exception {

    Assert.assertEquals("YES", twoStrings("jackandjill","wentupthehill"));
  }
	
	private char[] unique(char[] c) {
    char[] arr = new char[c.length];
    char previous = Character.MIN_VALUE;
    int j = 0;
    for(int i=0; i< c.length; i++) {
      
      if( Character.MIN_VALUE == previous  ||  c[i] != previous ) {
        arr[j++] = c[i];
        previous = c[i];
      }

    }
    char[] result = new char[j];
    System.arraycopy(arr, 0, result, 0, j);
    return result;
  }
	
	public String twoStrings(String s1, String s2) {
    
    if( null == s1 || null == s2 )
      throw new RuntimeException("wrong arguments");
    

    char[] o1 = s1.toCharArray();
    char[] o2 = s2.toCharArray(); 
    Arrays.sort(o1);
    Arrays.sort(o2);
    char[] u1 = unique(o1);
    char[] u2 = unique(o2);

    int max1 = u1.length-1;
    int max2 = u2.length-1;
    
    int i = 0, j = 0;
    while( (u1[i] != u2[j]) && ( i < max1 && j < max2 )) {
      if( u1[i] < u2[j] ) 
        if (i < max1) i++; else break;
      else 
        if ( j < max2 ) j++; else break;
    }

    return u1[i] == u2[j] ? "YES": "NO";
  }

}



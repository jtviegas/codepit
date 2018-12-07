package org.aprestos.labs.challenges.hackerrank.dictionaries_and_hashmaps;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/*
 * Two strings are anagrams of each other if the letters of one string can be rearranged to form the other string. 
 * Given a string, find the number of pairs of substrings of the string that are anagrams of each other. 
 * For example , the list of all anagrammatic pairs is  at positions  respectively.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SherlockAndAnagrams {

  @Test
	public void test1() throws Exception {
		Assert.assertEquals(4, solution("abba"));
	}
  @Test
  public void test2() throws Exception {
    Assert.assertEquals(10, solution("kkkk"));
  }
  @Test
  public void test3() throws Exception {
    Assert.assertEquals(3, solution("ifailuhkqq"));
  }
  
 
  public static int solution(String s) {
    int r = 0;
    int anagramMaxLength = s.length()-1;
    for(int i=1; i<=anagramMaxLength; i++) {
      int anagramLength = i;
      for(int j=0; j+anagramLength<s.length(); j++) {
        String toMatch = s.substring(j, j+anagramLength);
        for(int k=j+1; k+anagramLength < s.length() + 1;k++) {
          String matcher = s.substring(k,k+anagramLength);
          if( hasSameChars(toMatch.toCharArray(), matcher.toCharArray()) )
            r++;
        }
      }
    }
    return r;
  }

  
  private static boolean hasSameChars(char[] a, char[] b){
    
    if(a.length!= b.length)
      return false;
    
    int[] mapA = new int[256];
    int[] mapB = new int[256];
    for(int i=0;i<a.length;i++) {
      mapA[a[i]]++;
      mapB[b[i]]++;
    }
    return Arrays.equals(mapA, mapB);
  }
  
  
  

}


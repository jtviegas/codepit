package org.aprestos.labs.challenges.hackerrank.dictionaries_and_hashmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public void test() throws Exception {
		Assert.assertEquals(4, solution("abba"));
	}
  
  public static int solution2(String s) {
    
    int result = 0;
    char[] text = s.toCharArray();
    
    final char[] repeatingChars = findRepeatingChars(text) ;
    int anagramMaxLength = s.length()-1;
    
    for(int i=0; i < anagramMaxLength; i++) {
      for(int j=0, k = s.length()-1; j < k ; j++, k--) {
        char[] left = new char[i+1];
        char[] right = new char[i+1];
        System.arraycopy(text, 0, left, 0, i+1);
        System.arraycopy(text, k, right, 0, i+1);

        if( madeOfChars(repeatingChars, left) && madeOfChars(repeatingChars, right) ) {
          result += isAnagram(left, right) ? 1 : 0;
        }
      }
    }
    
    return result;
  }
  
  public static int solution(String s) {
    
    int result = 0;
    
    bruteforce(s);
    
    return result;
  }
  
  private static boolean inArray(char c, char[] text) {
    boolean r = false;
    for(char o: text) {
      if( o == c ) {
        r = true;
        break;
      }
    }
    return r;
  }
  
  private static int bruteforce(String s) {
    int r = 0;
    char[] text = s.toCharArray();
    // get repeating chars
    final char[] repeatingChars = findRepeatingChars(text) ;
    // find sequences of repeating chars
    List<String> sequences = new ArrayList<String>();
    int pointer = -1;
    for( int i=0; i < s.length();i++ ) {
      if( inArray(text[i], repeatingChars)  ) {
        if (-1 == pointer) 
          pointer = i;
      }  
      else {
        if (-1 < pointer) {
          sequences.add(s.substring(pointer, i));
          pointer = -1;
        }
      }
        
    }
    
    if (-1 < pointer) {
      sequences.add(s.substring(pointer));
    }
    // for every sequence of repeating find all combinations of chars
    Map<Integer, Set<String>> combinations = new HashMap<>();
    for(int i=1; i<text.length; i++) {
      for(int j=0;j+i<=text.length;j++) {
        if( !combinations.containsKey(i) ) combinations.put(i, new HashSet<String>());
        combinations.get(i).add(s.substring(j, j+i));
      }
    }
    // go through the sets and find pairs of anagrams
    
   
    return r;
  }
  
  static boolean madeOfChars(char[] universe, char[] test) {
    boolean result = true;
    for(char u : test) {
      boolean found=false;
      for (char c: universe)
        if (c == u)
          found=true;
      if(!found) {
        result = false;
        break;
      }
      
    }
    return result;
  }
  

  
  static Set<String> computePermutations(char[] permutation, char[] variables, int limitLength) {
    
    final Set<String> permutations = new HashSet<String>();

    for(char o: variables) {
      String newPermutation = new String(addToCharArray(o, permutation));
      if( newPermutation.length() < limitLength) 
        permutations.addAll(computePermutations(newPermutation.toCharArray(), variables, limitLength));
      permutations.add(newPermutation);
    }

    return permutations;
  }
  
  static char[] addToCharArray(char c, char[] a) {
    char[] result = new char[a.length+1];
    System.arraycopy(a, 0, result, 0, a.length);
    result[a.length] = c;
    return result;
  }
  
  static Object[] addToArray(Object c, Object[] a) {
    Object[] result = new Object[a.length+1];
    System.arraycopy(a, 0, result, 0, a.length);
    result[a.length] = c;
    return result;
  }
  
  
  
  static private char[] findRepeatingChars(char[] chars) {
    char[] result = new char[0];
    int[] map = new int[256];

    for (char c : chars) {
      map[c]++;
      if (2 == map[c])
        result = addToCharArray(c, result);
    }
    
    return result;
  }
  
  static private boolean isAnagram(char[] s1, char[] s2) {

    int[] map1 = new int[256];
    int[] map2 = new int[256];

    for (char c : s1)
      map1[c]++;
    for (char c : s2)
      map2[c]++;

    for (int i = 0; i < 256; i++)
      if (map1[i] != map2[i])
        return false;

    return true;
  }

}


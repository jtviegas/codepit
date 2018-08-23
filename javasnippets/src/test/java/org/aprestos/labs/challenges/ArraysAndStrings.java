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

    Assert.assertEquals("%20sdfgghjklpouuytrewq%20", replace_1_5("asdfgghjklpouuytrewqa", "a", "%20"));
    Assert.assertEquals("lsdfgghjklp%20%20%20ytrewql", replace_1_5("lsdfgghjklpaaaytrewql", "a", "%20"));

    Assert.assertArrayEquals(new int[][] { { 4, 7, 1, 5 }, { 3, 6, 0, 4 }, { 2, 5, 9, 3 }, { 1, 4, 8, 2 } },
        rotateMatrix_1_6(new int[][] { { 1, 2, 3, 4 }, { 4, 5, 6, 7 }, { 8, 9, 0, 1 }, { 2, 3, 4, 5 } }));
    Assert.assertArrayEquals(new int[][] { { 3, 6, 9 }, { 2, 5, 8 }, { 1, 4, 7 } },
        rotateMatrix_1_6(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }));

    Assert.assertArrayEquals(new int[][] { { 1, 0, 3 }, { 0, 0, 0 }, { 7, 0, 9 } },
        zeroMatrixRowAndColumn_1_7(new int[][] { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 9 } }));

    Assert.assertArrayEquals(new int[][] { { 1, 2, 0, 4 }, { 4, 1, 0, 4 }, { 3, 3, 0, 2 }, { 0, 0, 0, 0 } },
        zeroMatrixRowAndColumn_1_7(new int[][] { { 1, 2, 3, 4 }, { 4, 1, 6, 4 }, { 3, 3, 3, 2 }, { 7, 8, 0, 2 } }));
    
    Assert.assertEquals(true, isSubstring_1_8("waterbottle", "erbottlewat"));
    Assert.assertEquals(false, isSubstring_1_8("waterbottle", "erbottlewadt"));
    
    Assert.assertArrayEquals(new int[] {5,1,2,3,4}, leftRotateArray(new int[] {1,2,3,4,5}, 4));
    Assert.assertArrayEquals(new int[] {77, 97, 58, 1, 86, 58, 26, 10, 86, 51, 41, 73, 89, 7, 10, 1, 59, 58, 84, 77}, 
        leftRotateArray(new int[] {41, 73, 89, 7, 10, 1, 59, 58, 84, 77, 77, 97, 58, 1, 86, 58, 26, 10, 86,51}, 10));

  }

  private int[] leftRotateArray(int[] a,int n) {
    
    if(n == a.length)
      return a;
    
    int l = a.length;
    int i = 0;
    int val = a[i];
    do {
      int newI = (i+n)%l;
      int newval = a[newI];
      a[newI] = val;
      val = newval;
      i=newI;
    }
    while(i != 0);
    
    return a;
  }
  
  private boolean isSubstring_1_8(String s1, String s2) {
    boolean r = false;

    char[] _s1 = s1.toCharArray();
    char[] _s2 = s2.toCharArray();
    int j = 0, i;
    boolean foundStart = false;

    for (i = 0; i < _s1.length; i++)
      if (_s1[i] == _s2[j]) {
        foundStart = true;
        break;
      }

    if (foundStart) {
      int matches = 0;
      while (_s1[i++] == _s2[j++]) {
        matches++;
        if(matches == _s2.length) {
          r = true;
          break;
        }
        
        if(j == _s2.length )
          break;
        if( i == _s1.length )
          i = 0;
        
      }
    }

    return r;
  }

  private int[][] zeroMatrixRowAndColumn_1_7(int[][] matrix) {

    int[] cols = new int[matrix.length], rows = new int[matrix.length];
    int numFields = matrix.length * matrix.length;

    for (int i = 0; i < numFields; i++) {
      int row = i / matrix.length;
      int col = i % matrix.length;
      if (0 == matrix[row][col]) {
        cols[col] = 1;
        rows[row] = 1;
      }
    }
    for (int index = 0; index < rows.length; index++) {
      if (1 == rows[index])
        for (int c = 0; c < matrix.length; c++)
          matrix[index][c] = 0;
    }
    for (int index = 0; index < cols.length; index++) {
      if (1 == cols[index])
        for (int r = 0; r < matrix.length; r++)
          matrix[r][index] = 0;
    }

    return matrix;
  }

  private int[][] rotateMatrix_1_6(int[][] matrix) {

    for (int layer = 0; layer < matrix.length / 2; layer++) {

      int layerHigherIndex = matrix.length - 1 - layer;
      int layerLowerIndex = layer;

      for (int i = 0; i < (layerHigherIndex - layerLowerIndex); i++) {
        int n = matrix[layerLowerIndex][layerLowerIndex + i];
        int w = matrix[layerHigherIndex - i][layerLowerIndex];
        int s = matrix[layerHigherIndex][layerHigherIndex - i];
        int e = matrix[layerLowerIndex + i][layerHigherIndex];

        matrix[layerHigherIndex - i][layerLowerIndex] = n;
        matrix[layerHigherIndex][layerHigherIndex - i] = w;
        matrix[layerLowerIndex + i][layerHigherIndex] = s;
        matrix[layerLowerIndex][layerLowerIndex + i] = e;
      }
    }

    return matrix;
  }

  private String replace_1_5(String s, String o, String n) {

    char[] r = s.toCharArray();
    int matchLength = o.length();
    int j = 0;

    char[] _o = o.toCharArray();
    char[] _n = n.toCharArray();

    for (int i = 0; i < r.length; i++) {
      if (r[i] == _o[j]) {
        j++;
      } else
        j = 0;

      if (matchLength == j) {
        int sizeIncrement = _n.length - _o.length;
        char[] _r = new char[r.length + sizeIncrement];
        int includePoint = i - j + 1;
        System.arraycopy(r, 0, _r, 0, includePoint);
        System.arraycopy(_n, 0, _r, includePoint, _n.length);
        System.arraycopy(r, i + 1, _r, includePoint + _n.length, r.length - i - 1);
        r = _r;
        j = 0;
      }

    }
    return new String(r);
  }

  private char[] include(char[] s, char[] i, int index) {
    if (null == s || null == i)
      throw new IllegalArgumentException("can't be null");
    if (0 == i.length)
      return s;
    if (0 == s.length)
      return i;

    char[] r = new char[s.length + i.length];
    for (int j = 0; j < index; j++) {
      r[j] = s[j];
    }
    for (int j = 0; j < i.length; j++) {
      r[index + j] = i[j];
    }
    for (int j = index; j < s.length; j++) {
      r[j + i.length] = s[j];
    }

    return r;
  }

  private boolean isAnagram_1_4(String s1, String s2) {

    int[] map1 = new int[256];
    int[] map2 = new int[256];

    for (char c : s1.toCharArray())
      map1[c]++;
    for (char c : s2.toCharArray())
      map2[c]++;

    for (int i = 0; i < 256; i++)
      if (map1[i] != map2[i])
        return false;

    return true;
  }

  private String removeDuplicatesInString2_1_3(String s) {

    if (null == s || 2 > s.length())
      return s;
    char[] _s = s.toCharArray();

    int tail = 1;
    for (int i = 1; i < _s.length; i++) {

      int j;
      for (j = 0; j < tail; j++)
        if (_s[j] == _s[i])
          break; // there was a duplicate

      if (j == tail) {
        // there were no duplicates
        // move the tail
        _s[tail] = _s[i];
        ++tail;
      }

    }

    for (int i = tail; i < _s.length; i++)
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

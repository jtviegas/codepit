package org.aprestos.labs.challenges;

public class Utils {

  public static int[] strToIntArray(String s) {

    String[] _s = s.split(" ");
    int[] r = new int[_s.length];
    int index = 0;
    for (String o : _s)
      r[index++] = Integer.parseInt(o.trim());

    return r;
  }
  
  public static int[][] LinesToIntMatrix(String s) {
    String[] lines = s.split(System.getProperty("line.separator"));
    int[][] r = new int[lines.length][];
    for( int i=0; i<lines.length; i++ ) {
      r[i] = strToIntArray(lines[i].trim());
    }
    return r;
  }
  
  
  public static int[] expand(int[] a, int add) {
    int[] n = new int[a.length + add];
    System.arraycopy(a, 0, n, 0, a.length);
    return n;
  }
  
  public static int[] trim(int[] a, int novalue) {
    
    int[] n = new int[a.length];
    
    int j=0;
    for(int i = 0; i< a.length; i++) {
      if( novalue != a[i] )
        n[j++] = a[i];
    }
    
    int[] n2 = new int[j];
    System.arraycopy(n, 0, n2, 0, j);
    return n2;
  }
  
  
  public static long[] intersect(long[] a, long[] b) {
    
    if( a.length != 2 && b.length != 2)
      throw new IllegalArgumentException("arrays must be the 2 elements long");
    
    long[] r = null;
    
    if( a[0] <= b[0] && a[1] >= b[0] ) {
      
      if( a[1] <= b[1]  ) {
        r = new long[]{b[0], a[1]};
      }
      else {
        r = new long[]{b[0], b[1]};
      }
    }
    else if( a[0] > b[0] && a[0] <= b[1] ) {
      if( a[1] <= b[1]  ) {
        r = new long[]{a[0], a[1]};
      }
      else {
        r = new long[]{a[0], b[1]};
      }
    }
    
    return r;
  }

}

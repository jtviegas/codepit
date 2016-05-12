/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.arraysEqualsDigressions.ArrayUtils;

/**
 *
 * @author jtviegas
 */
public class ArraysEqualsDigressions implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        
        Integer[][][] a = getTwoEqualArraysDifferentReferences();
        
        Integer[][] a1 = a[0];
        Integer[][] a2 = a[1];
        
        Integer[][][] b = getTwoEqualArraysSameReferences();
                
        Integer[][] b1 = b[0];
        Integer[][] b2 = b[1];

        Integer[][][] c = getTwoEqualArraysOfArraysWithSameReferences();
        Integer[][] c1 = c[0];
        Integer[][] c2 = c[1];
        
        Integer[][][] d = getTwoEqualArraysOfArraysWithDifferentReferences();
        Integer[][] d1 = d[0];
        Integer[][] d2 = d[1];
        

        Integer[][][] e = getTwoDifferentArraysOfArraysWithDifferentReferences();
        Integer[][] e1 = e[0];
        Integer[][] e2 = e[1];

        String[][][] f = getTwoDifferentStringArraysOfArraysWithDifferentReferences();
        String[][] f1 = f[0];
        String[][] f2 = f[1];
        

        String[][][] g = getTwoEqualStringArraysOfArraysWithDifferentReferences();
        String[][] g1 = g[0];
        String[][] g2 = g[1];
        
        
        System.out.println("\n do i find 'em, a1 and a2 with different reference but same value, equal ... -> " 
                + Arrays.equals(a1, a2));
        System.out.println("\n do i find 'em, b1 and b2 with same reference and same value, equal ... -> " 
                + Arrays.equals(b1, b2));
        
        System.out.println("\n do i find 'em, c1 and c2 with sub arrays with same reference and same values, equal ... -> " 
                + Arrays.equals(c1, c2));
        System.out.println("\n do i find 'em, d1 and d2 with sub arrays with different reference but same values, equal ... -> " 
                + Arrays.equals(d1, d2));
        
        System.out.println("\n do i find 'em, d1 and d2 with sub arrays with different reference and different values, equal ... -> " 
                + Arrays.equals(e1, e2));
        
        ArrayUtils au = new ArrayUtils();

        System.out.println("\n do ArrayUtils find 'em, a1 and a2 with different reference but same value, equal ... -> " 
                + au.shallowEquals(a1, a2));
        System.out.println("\n do ArrayUtils find 'em, b1 and b2 with same reference and same value, equal ... -> " 
                + au.shallowEquals(b1, b2));
        
        System.out.println("\n do ArrayUtils find 'em, c1 and c2 with sub arrays with same reference and same values, equal ... -> " 
                + au.shallowEquals(c1, c2));
        System.out.println("\n do ArrayUtils find 'em, d1 and d2 with sub arrays with different reference but same values, equal ... -> " 
                + au.shallowEquals(d1, d2));
        
        
        System.out.println("\n do ArrayUtils find 'em, e1 and e2 with sub arrays with different reference and different values, equal ... -> " 
                + au.shallowEquals(e1, e2));
        
        
        System.out.println("\n do ArrayUtils find 'em, f1 and f2 with sub string arrays with different reference and different values, equal ... -> " 
                + au.shallowEquals(f1, f2));
        System.out.println("\n do ArrayUtils find 'em, g1 and g2 with sub string arrays with different reference and same values, equal ... -> " 
                + au.shallowEquals(g1, g2));
    }


    
     private String[][][] getTwoDifferentStringArraysOfArraysWithDifferentReferences()
    {
        String[][][] result = new String[2][2][3];
        
        result[0][0] = new String[]{new String("a"), new String("x"), new String("c")};
        result[0][1] = new String[]{new String("x"), new String("f"), new String("g")};
        
        result[1][0] = new String[]{new String("a"), new String("b"), new String("x")};
        result[1][1] = new String[]{new String("e"), new String("d"), new String("g")};
        
        return result;
    }
      private String[][][] getTwoEqualStringArraysOfArraysWithDifferentReferences()
    {
        String[][][] result = new String[2][2][3];
        
        result[0][0] = new String[]{new String("a"), new String("b"), new String("c")};
        result[0][1] = new String[]{new String("e"), new String("f"), new String("g")};
        
        result[1][0] = new String[]{new String("a"), new String("b"), new String("c")};
        result[1][1] = new String[]{new String("e"), new String("f"), new String("g")};
        
        return result;
    }
      
      private Integer[][][] getTwoEqualArraysOfArraysWithDifferentReferences()
    {
        Integer[][][] result = new Integer[2][2][3];
        
        result[0][0] = new Integer[]{new Integer(1), new Integer(123), new Integer(79)};
        result[0][1] = new Integer[]{new Integer(0), new Integer(321), new Integer(97)};
        
        result[1][0] = new Integer[]{new Integer(1), new Integer(123), new Integer(79)};
        result[1][1] = new Integer[]{new Integer(0), new Integer(321), new Integer(97)};
        
        return result;
    }
      
    private Integer[][][] getTwoEqualArraysOfArraysWithSameReferences()
    {
        Integer[][][] result = new Integer[2][2][3];
        
        Integer[] a =  new Integer[]{new Integer(1), new Integer(123), new Integer(79)};
        Integer[] b = new Integer[]{new Integer(0), new Integer(321), new Integer(97)}; 
        
        result[0][0] = a;
        result[0][1] = b;
        
        result[1][0] = a;
        result[1][1] = b;
        
        return result;
    }
    
    private Integer[][][] getTwoDifferentArraysOfArraysWithDifferentReferences()
    {
        Integer[][][] result = new Integer[2][2][3];
        
        result[0][0] = new Integer[]{new Integer(1), new Integer(222), new Integer(79)};
        result[0][1] = new Integer[]{new Integer(0), new Integer(321), new Integer(97)};
        
        result[1][0] = new Integer[]{new Integer(1), new Integer(123), new Integer(79)};
        result[1][1] = new Integer[]{new Integer(4), new Integer(321), new Integer(97)};
        
        return result;
    }
    
    private Integer[][][] getTwoEqualArraysDifferentReferences()
    {
        Integer[][][] result = new Integer[2][2][3];
        
        result[0][0][0] = new Integer(1);
        result[0][0][1] = new Integer(123);
        result[0][0][2] = new Integer(79);
        
        result[0][1][0] = new Integer(0);
        result[0][1][1] = new Integer(321);
        result[0][1][2] = new Integer(97);
        
        result[1][0][0] = new Integer(1);
        result[1][0][1] = new Integer(123);
        result[1][0][2] = new Integer(79);
        
        result[1][1][0] = new Integer(0);
        result[1][1][1] = new Integer(321);
        result[1][1][2] = new Integer(97);

        
        return result;
    }
    
    private Integer[][][] getTwoEqualArraysSameReferences()
    {
        Integer[][][] result = new Integer[2][2][3];
        
        Integer a =  new Integer(1);
        Integer b =  new Integer(123);
        Integer c =  new Integer(79);
        
        Integer d =  new Integer(0);
        Integer e =  new Integer(321);
        Integer f =  new Integer(97);
        
        result[0][0][0] = a;
        result[0][0][1] = b;
        result[0][0][2] = c;
        
        result[0][1][0] = d;
        result[0][1][1] = e;
        result[0][1][2] = f;
        
        result[1][0][0] = a;
        result[1][0][1] = b;
        result[1][0][2] = c;
        
        result[1][1][0] = d;
        result[1][1][1] = e;
        result[1][1][2] = f;

        
        return result;
    }
    
   

    
}

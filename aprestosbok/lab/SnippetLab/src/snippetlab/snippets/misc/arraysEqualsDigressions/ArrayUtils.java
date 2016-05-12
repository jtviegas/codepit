/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.arraysEqualsDigressions;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author jtviegas
 */
public class  ArrayUtils 
{
     public static boolean shallowEquals(Object[] a , Object[] b)
    {
        boolean result = false;
        
        if(null == a && null == b)
            return true;
        else
            if(null == a || null == b)
                return false;
        
        if(a.length != b.length)
            return false;

        int i=0;
        
        while(i < a.length)
        {
            if( a[i].getClass().isArray() && b[i].getClass().isArray() )
            {
               Object[] aa = Arrays.copyOf((Object[])a[i], Array.getLength(a[i]));
               Object[] bb = Arrays.copyOf((Object[])b[i], Array.getLength(b[i]));
               
               if(!shallowEquals(aa, bb))
                   break;
            }
            else
            {
                if(!a[i].equals(b[i]))
                    break;
            }
            
            i++;
        }
        
        if(a.length == i)
            result = true;
        
        return result;
    }
}

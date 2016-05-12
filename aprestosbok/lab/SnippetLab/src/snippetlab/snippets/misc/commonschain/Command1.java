/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.commonschain;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;

/**
 *
 * @author jtviegas
 */
public class Command1 implements Filter{

    public boolean execute(Context arg0) throws Exception 
    {
        System.out.println("execute @ command1");
        return false;
    }

    public boolean postprocess(Context arg0, Exception arg1) 
    {
        boolean result = false;
        System.out.println("postprocess @ command1");
        
//         if(null != arg1)
//        {
//            System.out.println("postprocess at command 1 caught an exception -> " + arg1.getMessage());
//            result = true;
//        }
        
        return result;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.commonschain;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 *
 * @author jtviegas
 */
public class Command3 implements Command{

    public boolean execute(Context arg0) throws Exception 
    {
        System.out.println("execute @ command3");
        if(true)
            throw new Exception("command 3 threw an exception!");
        
        return true;
    }

//    public boolean postprocess(Context arg0, Exception arg1) 
//    {
//        boolean result = false;
//        System.out.println("postprocess @ command3");
//        
//         if(null != arg1)
//        {
//            System.out.println("postprocess at command 3 caught an exception -> " + arg1.getMessage());
//            result = true;
//        }
//        
//        return result;
//    }
    
}

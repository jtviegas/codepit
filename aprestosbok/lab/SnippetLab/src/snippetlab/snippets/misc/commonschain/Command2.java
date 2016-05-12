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
public class Command2 implements Command{

    public boolean execute(Context arg0) throws Exception 
    {
        System.out.println("execute @ command2");
        
//        if(true)
//            throw new Exception("command 2 threw an exception!");
        
        return false;
    }

//    public boolean postprocess(Context arg0, Exception arg1) 
//    {
//        boolean result = false;
//        
//        System.out.println("postprocess @ command2");
//        
//            
//        if(null != arg1)
//        {
//            System.out.println("postprocess at command 2 caught an exception -> " + arg1.getMessage());
//            result = true;
//        }
//        
//        return result;
//    }
}

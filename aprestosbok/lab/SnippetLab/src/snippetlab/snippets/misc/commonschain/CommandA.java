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
public class CommandA implements Filter
{

    public boolean execute(Context arg0) throws Exception 
    {
        System.out.println("execute CommandA");
        return false;
    }

    public boolean postprocess(Context arg0, Exception arg1) 
    {
        boolean result = false;
        System.out.println("postprocess CommandA");
        
        if(null != arg1)
            result=true;
        
        return result;
    }

}

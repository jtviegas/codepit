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
public class CommandC implements Command
{

    public boolean execute(Context arg0) throws Exception 
    {
        System.out.println("execute CommandC");
        if(true)
            throw new Exception("CommandB thrown an exception!");
        
        return false;
    }

}
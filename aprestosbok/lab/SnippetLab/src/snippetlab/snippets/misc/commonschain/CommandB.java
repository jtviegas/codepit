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
public class CommandB implements Command
{

    public boolean execute(Context arg0) throws Exception 
    {
        System.out.println("execute CommandB");
        return false;
    }

}
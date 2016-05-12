/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.assorted.CommonsChain.o;
import snippetlab.snippets.misc.commonschain.Command1;
import snippetlab.snippets.misc.commonschain.Command2;
import snippetlab.snippets.misc.commonschain.Command3;

/**
 *
 * @author jtviegas
 */
public class CommonsChain implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
       boolean result=false;
        Chain chain = new ChainBase();
        chain.addCommand(new Command1());
        chain.addCommand(new Command2());
        chain.addCommand(new Command3());
        
        Context ctx = new ContextBase();
        ctx.put(o.a.toString(), o.a);
        
        
        try
        {
             result = chain.execute(new ContextBase());
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        
        System.out.println("out of chain");
        
        
        
        
    }
    
    public static enum o{a, b, c}
}

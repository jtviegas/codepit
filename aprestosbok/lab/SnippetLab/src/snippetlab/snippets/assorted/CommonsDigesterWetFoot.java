/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.commonsDigesterWetFoot.RoleDsc;
import snippetlab.snippets.misc.commonsDigesterWetFoot.RolesParser;

/**
 *
 * @author jtviegas
 */
public class CommonsDigesterWetFoot implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        Command o = new RolesParser();
        
        Context c = new ContextBase();
        c.put(RolesParser.ContextKeys.FILE, new String("conf/entities.xml"));
        try
        {
            o.execute(c);    
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        List<RoleDsc> list = (List<RoleDsc>)c.get(RolesParser.ContextKeys.LIST);
        System.out.println(list.size());
        
    }

}

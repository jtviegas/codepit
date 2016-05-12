/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.commonsDigesterWetFoot;

import java.io.File;
import java.util.List;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.digester.Digester;

/**
 *
 * @author jtviegas
 */
public class RolesParser implements Command
{
    public static enum ContextKeys{FILE, LIST}

    public boolean execute(Context arg0) throws Exception 
    {
        File file = new File((String)arg0.get(ContextKeys.FILE));
        
        try
        {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.addObjectCreate("entities", "java.util.ArrayList");
            digester.addObjectCreate("entities/role", "snippetlab.snippets.misc.commonsDigesterWetFoot.RoleDsc");
            digester.addSetProperties("entities/role");
            digester.addSetNext("entities/role", "add", "snippetlab.snippets.misc.commonsDigesterWetFoot.RoleDsc");
            List<RoleDsc> list = (List<RoleDsc>)digester.parse(file);
        
            arg0.put(ContextKeys.LIST, list);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        
        return false;
    }

    
    
}

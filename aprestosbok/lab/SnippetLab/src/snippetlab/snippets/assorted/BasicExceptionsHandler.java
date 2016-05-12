/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.basicExceptionsHandler.AException;
import snippetlab.snippets.misc.basicExceptionsHandler.BException;
import snippetlab.snippets.misc.basicExceptionsHandler.BasicExceptionHandler;

/**
 *
 * @author jtviegas
 */
public class BasicExceptionsHandler implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        
        BasicExceptionHandler eh = new BasicExceptionHandler() ;
        

        try
        {
            throw new Exception();
        }
        catch(Exception x)
        {
           eh.handle(x); 
        }
        try
        {
            throw new AException();
        }
        catch(Exception x)
        {
           eh.handle(x); 
        }
        try
        {
            throw new BException();
        }
        catch(Exception x)
        {
           eh.handle(x); 
        }
        
    }

}

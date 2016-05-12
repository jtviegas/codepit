/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class ExceptionStackTrace implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        try
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        catch(Exception x)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            x.printStackTrace(new PrintStream(baos));
            System.out.println(new String(baos.toByteArray()));
        }
    }
    

}

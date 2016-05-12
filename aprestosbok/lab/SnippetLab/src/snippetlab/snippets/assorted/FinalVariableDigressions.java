/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class FinalVariableDigressions implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        final Object o = null;
        
//        if(o instanceof Object)
//            System.out.println("is not instance");
        if(null == o)
            System.out.println("is null");
        
        
    }

}

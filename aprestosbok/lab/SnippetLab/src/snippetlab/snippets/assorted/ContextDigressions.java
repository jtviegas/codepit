/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.contextdigressions.basiccontext.BasicContext;
import snippetlab.snippets.misc.contextdigressions.basiccontext.interfaces.BasicContextInterface;

/**
 *
 * @author jtviegas
 */
public class ContextDigressions implements SnippetInterface
{

    enum A{a, aa, aaa,}
    enum B{b, bb, bbb}
    
    class AA{}
    class BB{}
    
    public void go(JPanel panel, JFrame frame) 
    {
     
        BasicContextInterface c = new BasicContext();
        
        c.put(A.a, frame);
        c.put(B.b, panel);
        
        c.put("a", frame);
        c.put("b", panel);
        
        c.put(new AA(), frame);
        c.put(new BB(), panel);
        
        
        System.out.println(c.toString());
        
        
    }
    
}

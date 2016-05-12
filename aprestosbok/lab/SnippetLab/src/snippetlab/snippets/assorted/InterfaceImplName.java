/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.InterfaceImplName.A;
import snippetlab.snippets.misc.InterfaceImplName.A1;
import snippetlab.snippets.misc.InterfaceImplName.A2;

/**
 *
 * @author jtviegas
 */
public class InterfaceImplName implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) {
        
        List<A> as = Arrays.asList(new A1(), new A2());
        
        for(A a:as)
        {
            System.out.println(a.getClass().getName() + "-" + a.getA());
        }
        
    }

}

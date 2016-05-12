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
public class ProtectedFields implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
       A a = new A();
       AA aa = new AA();
       
       System.out.println(a.x);
       System.out.println(a.y);
       
       System.out.println(aa.x);
       System.out.println(aa.y);
       
       System.out.println(((A)aa).x);
       System.out.println(((A)aa).y);
       
    }

    
    private class A
    {
        protected final String x="a";
        protected static final String y="z";
    }
    
    private class AA extends A
    {
        protected final String x="aa";
        protected static final String y="zz";
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.threadJoinDigression.CommanderThread;

/**
 *
 * @author jmv
 */
public class ThreadJoinDigression implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        
        
        
        Thread t = new Thread(new CommanderThread(frame));
        t.start();
        
        

        
    }

}

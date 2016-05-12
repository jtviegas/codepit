/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions;

import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 *
 * @author jtviegas
 */
public class SelectionCommand implements Command
{
    
    public boolean execute(Context arg0) throws Exception 
    {
        boolean result = false;
        System.out.println("+ executing selection command" + 
                (SwingUtilities.isEventDispatchThread()? " at the swing thread":""));
        final JLabel label = (JLabel)arg0.get("label");
        final DefaultMutableTreeNode node =(DefaultMutableTreeNode)arg0.get("node");
        
        System.out.println("+ selection command node - " + node.toString());
        
        Thread.sleep((new Random()).nextInt(6) * 1000);
//        Thread.sleep(10 * 1000);
        if(Thread.interrupted())
        {
            System.out.print("we were interrupted");
            return false;
        }
        SwingUtilities.invokeLater(new Runnable()
        {

            public void run() 
            {
               System.out.println("+ going to set label - " + node.toString() + " - selected" + 
                (SwingUtilities.isEventDispatchThread()? " at the swing thread":""));
               label.setText(node.toString() + " selected"); 
            }
            
        });
        result=true;
        System.out.println("+ executed selection command node - " + node.toString() + 
                (SwingUtilities.isEventDispatchThread()? " - at the swing thread":""));
        
        return result;
    }

}

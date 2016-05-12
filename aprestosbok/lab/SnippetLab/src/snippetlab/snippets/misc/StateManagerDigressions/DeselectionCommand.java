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
public class DeselectionCommand implements Command
{

    public boolean execute(Context arg0) throws Exception 
    {
        boolean result = false;
        System.out.println("= executing deselection command" + 
                (SwingUtilities.isEventDispatchThread()? " at the swing thread":""));
        final JLabel label = (JLabel)arg0.get("label");
        final DefaultMutableTreeNode node =(DefaultMutableTreeNode)arg0.get("node");
        
        
         SwingUtilities.invokeLater(new Runnable()
        {

            public void run() 
            {
                System.out.println("= going to set label deselected" + 
                (SwingUtilities.isEventDispatchThread()? " at the swing thread":""));
               label.setText(node.toString() + " deselected");
            }
            
        });
        
        result=true;
        System.out.println("= executed deselection command" + 
                (SwingUtilities.isEventDispatchThread()? " at the swing thread":""));
        return result;
    }
        
}

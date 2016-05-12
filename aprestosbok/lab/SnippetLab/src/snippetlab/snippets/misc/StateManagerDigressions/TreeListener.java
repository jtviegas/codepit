/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author jtviegas
 */
public class TreeListener implements TreeSelectionListener
{
    PropertyChangeSupport pcs = new PropertyChangeSupport((this));
    
    public void valueChanged(TreeSelectionEvent e) 
    {
        System.out.println("# tree has just changed");
        TreePath[] paths = e.getPaths();

        // Iterate through all affected nodes
        for (int i = 0; i < paths.length; i++)
        {
            TreePath path = e.getPaths()[i];
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)path.getLastPathComponent();
            
            if (e.isAddedPath(i))
            {
                System.out.println("# node selected");
                pcs.firePropertyChange(new PropertyChangeEvent(this,"selection",null,node));
            }
            else
            {
                System.out.println("# node deselected");
                pcs.firePropertyChange(new PropertyChangeEvent(this,"deselection",null,node));
            }

        }

    }
        
    public void addListener(PropertyChangeListener listener)
    {
        pcs.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener)
    {
        pcs.removePropertyChangeListener(listener);
    }
        
        
}

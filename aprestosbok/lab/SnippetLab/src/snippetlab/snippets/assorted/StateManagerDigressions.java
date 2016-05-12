/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.misc.StateManagerDigressions.StateManager2;
import snippetlab.snippets.misc.StateManagerDigressions.TreeListener;
import snippetlab.snippets.misc.StateManagerDigressions.opm.OperationsProgressManager;
import snippetlab.snippets.misc.swingworkerdigressions.GuiGlassPane;

/**
 *
 * @author jtviegas
 */
public class StateManagerDigressions extends AbstractSnippet
{
    StateManager2 manager;
    
    @Override
    public void init() 
    {
        super.init();
        try
        {
            OperationsProgressManager.initialize();
        
        JScrollPane sp = new JScrollPane();
        sp.setPreferredSize(new Dimension(200,400));
        JTree tree = new JTree(new DefaultMutableTreeNode("root"));
        loadTree(tree);
        sp.setViewportView(tree);
        panel.add(sp, BorderLayout.WEST);
        frame.setGlassPane(new GuiGlassPane());
        
        JLabel label = new JLabel("oi");
        panel.add(label, BorderLayout.CENTER);

        manager= new StateManager2(label, frame);
        TreeListener listener = new TreeListener();

        tree.addTreeSelectionListener(listener);
        listener.addListener(manager);
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        
    }

    
    private void loadTree(JTree t)
    {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)t.getModel().getRoot();
        
        List<String> childs = Arrays.asList("a","b","c","d","e","f");
        List<String> innerchilds = Arrays.asList("1","2","3","4","5","6");
        
        for(String s:childs)
        {
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(s);
            
            for(String i:innerchilds)
                child.add(new DefaultMutableTreeNode(i));
                
            root.add(child);
        }
    }
    
    @Override
    public void method() 
    {
        
        
        
    }

    
}

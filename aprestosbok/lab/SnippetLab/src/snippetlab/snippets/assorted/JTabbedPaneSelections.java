/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class JTabbedPaneSelections implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) {
        JTabbedPane t = new JTabbedPane();
        panel.add(t, BorderLayout.CENTER);
        t.addTab("a", new JPanel());
        t.addTab("b", new JPanel());
        t.addTab("c", new JPanel());
        t.addChangeListener(new TabListener());
    }

    
    private class TabListener implements ChangeListener
    {

        public void stateChanged(ChangeEvent e) 
        {
        System.out.println("selectedPane module -> " + 
                ((JTabbedPane)e.getSource()).getSelectedIndex() + "\n" + 
                ((JTabbedPane)e.getSource()).getSelectedComponent().toString()
               );    
        }
        
    }
    
}



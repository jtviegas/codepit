/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class JFileChooserDigressions implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        
        panel.add(new JButton(new AbstractAction(){

            public void actionPerformed(ActionEvent e) 
            {
                
            }
        }), BorderLayout.CENTER);
        
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class TestSnippetlab implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        panel.add(new JLabel("Test Done!"), BorderLayout.CENTER);
    }
    
    
    
}

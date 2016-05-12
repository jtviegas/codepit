/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public abstract class AbstractSnippet implements SnippetInterface
{
    protected JPanel panel;
    protected JFrame frame;
    
    public void go(JPanel panel, JFrame frame) 
    {
        this.frame = frame;
        this.panel = panel;

        init();
    }
    
    public void init()
    {
       JButton button = new JButton("action");
        button.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                method();
            }
            
        });
        
        panel.add(button, BorderLayout.SOUTH); 
    }
    
    public abstract void method();
    
}
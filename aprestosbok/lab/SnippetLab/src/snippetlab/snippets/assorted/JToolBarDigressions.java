/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import snippetlab.interfaces.SnippetInterface;


/**
 *
 * @author jtviegas
 */
public class JToolBarDigressions implements SnippetInterface
{
    private Action aa;
    private Action ab;
    private Action ac;
    private JToolBar tool;
    
    public void go(JPanel panel, JFrame frame) 
    {
        tool = new JToolBar("Still draggable");
        aa = new ActionA();
        ab=new ActionB();
        ac=new ActionC();
        
        tool.add(aa);
        tool.add(ab);
        
        
        int position;
        
        JButton button = new JButton(ac);
				button.setText(null);

				button.setMnemonic(KeyEvent.VK_UNDEFINED);

				button.setToolTipText(null);
			
				position = 0;
		
		

        tool.add(button, position);
        
        panel.setPreferredSize(new Dimension(450, 330));
        panel.add(tool, BorderLayout.PAGE_START);
    }
    
    class ActionA extends AbstractAction
    {
        public ActionA()
        {
            super("A", new ImageIcon("png/doc.png"));
        }
        public void actionPerformed(ActionEvent e) 
        {
            System.out.println("a");

        }
        
    }
    
    class ActionB extends AbstractAction
    {
        public ActionB()
        {
            super("B", new ImageIcon("png/doc.png"));
        }
        public void actionPerformed(ActionEvent e) 
        {
            System.out.println("b");
        }
        
    }
    
    class ActionC extends AbstractAction
    {
        public ActionC()
        {
            super("C", new ImageIcon("png/moz.png"));
        }
        public void actionPerformed(ActionEvent e) 
        {
            System.out.println("c");
        }
        
    }
}

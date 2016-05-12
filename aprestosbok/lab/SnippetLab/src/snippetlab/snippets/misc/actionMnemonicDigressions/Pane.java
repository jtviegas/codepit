/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.actionMnemonicDigressions;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 *
 * @author jmv
 */
public class Pane extends JPanel
{
    HelperWidget hw = new HelperWidget();
    JButton right;
    
    public Pane()
    {
        super();
        init();
    }
    
    protected void init()
    {
        
        setLayout(new FormLayout(
                "50dlu,fill:100dlu:grow,50dlu"
                ,
                "fill:200dlu:grow,50dlu"
                ));
        
        CellConstraints cc = new CellConstraints();
            
        add(new JTextPane(),cc.xy(2, 1));
        
        right = new JButton(new RightAction(hw));
        add(right, cc.xy(3, 2));
        
        JButton left=new JButton("change");
        left.addActionListener(new ButtonListener());
        
        add(left, cc.xy(1, 2)); 
        
    }
    
    class ButtonListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e) 
        {
            if(right.getAction().getValue(AbstractAction.NAME).equals(hw.getLeftName()))
            {
                right.getAction().putValue(AbstractAction.NAME, hw.getRightName());
                right.getAction().putValue(AbstractAction.SMALL_ICON, hw.getRightIcon());
                //right.getAction().putValue(AbstractAction.ACCELERATOR_KEY, hw.getRightKey());
                right.setMnemonic('r');
                
            }
            else
            {
                right.getAction().putValue(AbstractAction.NAME, hw.getLeftName());
                right.getAction().putValue(AbstractAction.SMALL_ICON, hw.getLeftIcon());
                //right.getAction().putValue(AbstractAction.ACCELERATOR_KEY, hw.getLeftKey());
                right.setMnemonic("l".toCharArray()[0]);
            }
           
        }
        
    }
    
}

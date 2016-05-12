/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.actionMnemonicDigressions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author jmv
 */
public class RightAction extends AbstractAction
{

    private HelperWidget hw;
    
    public RightAction(HelperWidget hw)
    {
        super(hw.getLeftName(), hw.getLeftIcon());
        putValue(AbstractAction.ACCELERATOR_KEY, hw.getLeftKey());
        this.hw=hw;
                
    }

    public void actionPerformed(ActionEvent e) 
    {
        
        if(this.getValue(AbstractAction.NAME).equals(hw.getLeftName()))
        {
            System.out.println("++++++left");
        }
        else
        {
            System.out.println("++++++right");
        }
        
        
    }

}

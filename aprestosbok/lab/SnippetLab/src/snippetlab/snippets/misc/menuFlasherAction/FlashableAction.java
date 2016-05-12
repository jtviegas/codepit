/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 *
 * @author jtviegas
 */
public class FlashableAction extends AbstractAction
{
    
    public FlashableAction()
    {
        super("moz", new ImageIcon("png/moz.png"));
    }

    
    public void actionPerformed(ActionEvent e) 
    {
        System.out.println("oi cara!");
        
    }

  
}

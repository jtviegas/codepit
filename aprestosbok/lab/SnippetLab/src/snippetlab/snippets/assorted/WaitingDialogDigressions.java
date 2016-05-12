/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.SwingUtilities;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.misc.WaitingDialogDigressions.WaitingDialog;

/**
 *
 * @author jtviegas
 */
public class WaitingDialogDigressions extends AbstractSnippet
{

    @Override
    public void method() 
    {
        System.out.println("WaitingDialogDigressions - dialog is going to open");

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run() 
            {
                WaitingDialog d = new WaitingDialog(frame,false);
                d.init();
                d.setVisible(true);
            }
        });
        
        System.out.println("WaitingDialogDigressions - dialog is open");
    }

    
    
    

}

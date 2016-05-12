/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.threadJoinDigression;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author jmv
 */
public class CommanderThread implements Runnable
{
    private ProgressDialog pd;
    private JFrame owner;
    
    public CommanderThread(JFrame owner)
    {
        this.owner = owner;
    }
    
    public void run() 
    {
        
        showProgress(1);
        
    }
    
    private void showProgress(final int i)
    {
        
        if(null == pd)
        {
            pd = new ProgressDialog(this.owner);
            SwingUtilities.invokeLater(new Runnable(){

                public void run() {
                    pd.pack();
                    pd.setVisible(true);
                }
            });
        }
        
        SwingUtilities.invokeLater(new Runnable(){

                public void run() 
                {
                    pd.setProgress(i);
                    if(i==100)
                        pd.setVisible(false);
                }
            });
        
        
    }

}

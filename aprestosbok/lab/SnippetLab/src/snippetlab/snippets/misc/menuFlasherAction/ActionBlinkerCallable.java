/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.concurrent.Callable;
import javax.swing.Action;
import javax.swing.SwingUtilities;

/**
 *
 * @author jtviegas
 */
public class ActionBlinkerCallable<Icon> implements Callable<Icon>
{

        private Action action;
        private Icon originalIcon;
        
        public ActionBlinkerCallable(Action action)
        {
            this.action = action;
            this.originalIcon = (Icon)action.getValue(Action.SMALL_ICON);
            action.putValue("ORIGINAL_ICON", this.originalIcon);
        }
        
        public Icon call() throws Exception 
        {   

        
            if(null != this.action.getValue(Action.SMALL_ICON))
            {
                
                setIcon(null);
            }
            else
            {
                
                setIcon(this.originalIcon);
            }
            
            return originalIcon;
  
        }
        
        private void setIcon(final Icon ico)
    {
        if(SwingUtilities.isEventDispatchThread())
        {
            this.action.putValue(Action.SMALL_ICON, ico);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable(){

                public void run() 
                {
                    action.putValue(Action.SMALL_ICON, ico);
                }

            });
        }
    }

    
    
}

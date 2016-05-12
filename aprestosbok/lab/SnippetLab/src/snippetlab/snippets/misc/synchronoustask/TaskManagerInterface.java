/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.synchronoustask;

/**
 *
 * @author jtviegas
 */
public interface TaskManagerInterface 
{
    public void go();
    public boolean isOn();
    
    public void setRunnable(Runnable runnable);
    public void setDescription(String description);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import javax.swing.Action;

/**
 *
 * @author jtviegas
 */
public class ActionBlinkerTask<Icon> extends FutureTask<Icon>
{
    private static final String THREAD_PREFIX = "BLINKER_THREAD_";
    
    public ActionBlinkerTask(Action action) 
    {
        super((Callable<Icon>)new ActionBlinkerCallable(action));
    }

     public static String createBlinkerThreadName(Action action)
    {
        return THREAD_PREFIX + action.getClass().getName();
    }
}

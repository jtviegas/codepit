/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

/**
 *
 * @author jtviegas
 */
public interface BlinkableActionInterface extends Runnable
{
    public void blink(boolean flag);
    public boolean isBlinking();
}

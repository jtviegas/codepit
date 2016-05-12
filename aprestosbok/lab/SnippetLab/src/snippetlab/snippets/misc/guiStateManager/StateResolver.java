/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.guiStateManager;

import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIEventInterface;
import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIStateInterface;
import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIStateResolverInterface;

/**
 *
 * @author jtviegas
 */
public class StateResolver implements GUIStateResolverInterface
{

    public GUIStateInterface getState(GUIEventInterface event) 
    {
       GUIStateInterface result = null;
       
       if(event instanceof Event2A)
            result = new StateA();
       else if(event instanceof Event2B)
            result = new StateB();
       
       return result;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.guiStateManager;

import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIEventInterface;
import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIInterface;
import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIStateManagerInterface;
import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIStateResolverInterface;

/**
 *
 * @author jtviegas
 */
public class ThePanelStateManager implements GUIStateManagerInterface
{
    private GUIInterface gui;
    private GUIStateResolverInterface stateResolver;
    
    public ThePanelStateManager(GUIInterface gui, GUIStateResolverInterface stateResolver)
    {
        this.gui = gui;
        this.stateResolver = stateResolver;
    }
    
    public void process(GUIEventInterface event) 
    {
        stateResolver.getState(event).implement(gui);
    }

}

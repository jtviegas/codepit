/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.guiStateManager;

import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIInterface;
import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIStateInterface;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author jtviegas
 */
public class StateB implements GUIStateInterface
{

    public void implement(GUIInterface gui) 
    {
    
        JTextField tf_1 = (JTextField)gui.getGUIComponent(ThePanelComponents.TF_1);
        JTextField tf_2 = (JTextField)gui.getGUIComponent(ThePanelComponents.TF_2);
        JTextPane tp = (JTextPane)gui.getGUIComponent(ThePanelComponents.TP);
        JButton btA = (JButton)gui.getGUIComponent(ThePanelComponents.BT_A);
        JButton btB = (JButton)gui.getGUIComponent(ThePanelComponents.BT_B);
        
        tf_1.setText("state B.");
        tf_2.setText("really, state B!");
        tp.setText("we are in state B, fellows!");
        btB.setEnabled(false);
        btA.setEnabled(true);
        
    }

}

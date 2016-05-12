/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.guiStateManager.ThePanel;

/**
 *
 * @author jtviegas
 */
public class GuiStateManager implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        panel.add(new ThePanel(), BorderLayout.CENTER);
    }

}

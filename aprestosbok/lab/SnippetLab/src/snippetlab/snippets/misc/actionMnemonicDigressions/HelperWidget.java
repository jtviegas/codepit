/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.actionMnemonicDigressions;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

/**
 *
 * @author jmv
 */
public class HelperWidget 
{
    private String leftName = "left";
    private String rightName = "right";
    
    private Icon leftIcon = new ImageIcon("png/doc.png");
    private Icon rightIcon = new ImageIcon("png/moz.png");
    
    private KeyStroke leftKey = KeyStroke.getKeyStroke("alt L");
    private KeyStroke rightKey = KeyStroke.getKeyStroke("alt R");

    public Icon getLeftIcon() {
        return leftIcon;
    }

    public KeyStroke getLeftKey() {
        return leftKey;
    }

    public String getLeftName() {
        return leftName;
    }

    public Icon getRightIcon() {
        return rightIcon;
    }

    public KeyStroke getRightKey() {
        return rightKey;
    }

    public String getRightName() {
        return rightName;
    }

}

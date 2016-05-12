/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.guiStateManager;

import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIInterface;
import snippetlab.snippets.misc.guiStateManager.GuiStateManager.guistatemanagement.interfaces.GUIStateManagerInterface;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author jtviegas
 */
public class ThePanel extends JPanel implements GUIInterface
{
    private JTextField tf_1 = new JTextField("state A"),tf_2 = new JTextField("state A");
    private JTextPane tp = new JTextPane();
    private JButton bt_2stateA = new JButton("A"), bt_2stateB = new JButton("B");
    private GUIStateManagerInterface  stateManager;
            
    public ThePanel()
    {
        init();
    }
    
    private void init()
    {
        this.setLayout(new FormLayout(
                "75dlu,10dlu,100dlu"
                , 
                "pref,5dlu,pref,5dlu,fill:max(60dlu;pref),25dlu,25dlu"));
         
        CellConstraints cc=new CellConstraints();
        tp.setText("state A");
        
        add(new JLabel("first field"), cc.xy(1, 1));
        add(tf_1, cc.xy(3, 1));
        add(new JLabel("second field"), cc.xy(1, 3));
        add(tf_2, cc.xy(3, 3));
        add(new JLabel("the pane"), cc.xy(1, 5));
        add(tp, cc.xy(3, 5));
        add(bt_2stateA, cc.xy(1, 7));
        add(bt_2stateB, cc.xy(3, 7));
    
        stateManager = new ThePanelStateManager(this, new StateResolver());
        
        bt_2stateA.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                stateManager.process(new Event2A());
            }
            
        });
        bt_2stateB.addActionListener(new ActionListener()
        {

            public void actionPerformed(ActionEvent e) 
            {
                stateManager.process(new Event2B());
            }
            
        });
        
    }

    public JComponent getGUIComponent(Enum<?> component) 
    {
        JComponent result = null;
        
        if(component.equals(ThePanelComponents.TF_1))
            result = this.tf_1;
        else if (component.equals(ThePanelComponents.TF_2))
            result = this.tf_2;
        else if (component.equals(ThePanelComponents.TP))
            result = this.tp;
        else if (component.equals(ThePanelComponents.BT_A))
            result = this.bt_2stateA;
        else if (component.equals(ThePanelComponents.BT_B))
            result = this.bt_2stateB;
        
        return result;
    }
    
    
    
    
}

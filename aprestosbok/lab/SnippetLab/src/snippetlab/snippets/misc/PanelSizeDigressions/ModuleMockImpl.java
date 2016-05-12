/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.PanelSizeDigressions;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author jtviegas
 */
public class ModuleMockImpl implements ModuleMockInterface
{
    private String gap="5dlu";
    private String col="50dlu";
    private String row="20dlu";
    
    
    JTabbedPane tab = new JTabbedPane();
    
    public ModuleMockImpl()
    {
        
        tab.addTab("A", getMePanelA());
        tab.addTab("B", getMePanelB());
        
    }
    public void start(GuiContainerInterface gci) 
    {
        gci.add(tab);
    }
    
    
    private JPanel getMePanelA()
    {
        JPanel panel=new JPanel();
        CellConstraints cc = new CellConstraints();
        panel.setLayout(new FormLayout(
                gap + ",fill:" + col + "," + gap + ",fill:" + col + ":grow," + gap + ",fill:" + col + ":grow," + gap
                ,
                gap + ",fill:" + row + "," + gap + ",fill:" + row + "," + gap + ",fill:" + row + "," + gap
                ));
        
        panel.add(new JLabel("label 1"), cc.xy(2, 2));
        panel.add(new JTextField("text 1"), cc.xy(4, 2));
        panel.add(new JLabel("label 2"), cc.xy(2, 4));
        panel.add(new JTextField("text 2"), cc.xy(4, 4));
        panel.add(new JLabel("label 3"), cc.xy(6, 2));
        panel.add(new JTextPane(), cc.xywh(6, 4,1,3));
        panel.add(new JComboBox(new Object[]{"one","two","three"}), cc.xywh(2, 6,3,1));
        return panel;
    }
    private JPanel getMePanelB()
    {
        JPanel panel=new JPanel();
        CellConstraints cc = new CellConstraints();
        panel.setLayout(new FormLayout(
                gap + ",fill:" + col + "," + gap + ",fill:" + col + ":grow," + gap + ",fill:" + col + ":grow," + gap
                ,
                gap + ",fill:" + row + "," + gap + ",fill:" + row + "," + gap + ",fill:" + row + "," + gap
                ));
        
        panel.add(new JLabel("label 1"), cc.xy(2, 2));
        panel.add(new JTextField("text 1"), cc.xy(4, 2));
        panel.add(new JLabel("label 2"), cc.xy(2, 4));
        panel.add(new JTextField("text 2"), cc.xy(4, 4));
        panel.add(new JLabel("label 3"), cc.xy(6, 2));
        panel.add(new JTextPane(), cc.xywh(6, 4,1,3));
        panel.add(new JComboBox(new Object[]{"one","two","three"}), cc.xywh(2, 6,3,1));
        return panel;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class SplitpanePanels implements SnippetInterface{

    private JSplitPane split;
    private List<JPanel> panels = new ArrayList<JPanel>();
    private JScrollPane leftpane,rightpane;
    private Random random;
    
    public void go(JPanel panel, JFrame frame) 
    {
        random = new Random();
        panel.setLayout(new BorderLayout());
        leftpane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rightpane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, leftpane, rightpane);
        loadPanels();
        panel.add(split, BorderLayout.CENTER);
        panel.add(new JButton(new LeftAction()), BorderLayout.WEST);
        panel.add(new JButton(new RightAction()), BorderLayout.EAST);
        frame.setSize(new Dimension(600,400));
        
        rightpane.setViewportView(new Panel1());
        
    }

    private void loadPanels()
    {
        panels.add(getMeAPanel(Color.BLACK, new Dimension(200,200)));
        panels.add(getMeAPanel(Color.BLUE, new Dimension(300,300)));
        panels.add(getMeAPanel(Color.CYAN, new Dimension(400,400)));
        panels.add(getMeAPanel(Color.ORANGE, new Dimension(400,300)));
        panels.add(getMeAPanel(Color.GRAY, new Dimension(400,200)));
        panels.add(getMeAPanel(Color.GREEN, new Dimension(400,100)));
        panels.add(getMeAPanel(Color.MAGENTA, new Dimension(400,50)));
        panels.add(getMeAPanel(Color.PINK, new Dimension(800,200)));
        
    }
    
    private JPanel getMeAPanel(Color color, Dimension size)
    {
        JPanel result = new JPanel();
        result.setBackground(color);
        result.setSize(size);
        
        return result;
    }
    private class LeftAction extends AbstractAction
    {

        public void actionPerformed(ActionEvent e) 
        {
            rightpane.setViewportView(null);
            rightpane.revalidate();
        }
        
    }
    private class RightAction extends AbstractAction
    {

        public void actionPerformed(ActionEvent e) 
        {
            rightpane.setViewportView(panels.get(random.nextInt(7)));
            rightpane.revalidate();
        }
        
    }
    
    private class Panel1 extends JPanel
    {
        public Panel1()
        {
            
            FormLayout l = new FormLayout("10dlu,100dlu,10dlu,300dlu,10dlu,100dlu,10dlu",
                    "10dlu,100dlu,10dlu,300dlu,10dlu,100dlu,10dlu");
            this.setLayout(l);
            CellConstraints cc = new CellConstraints();
            this.add(new JLabel("aaa"), cc.xy(6, 6));
            
            
            
        }
    }
    
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.threadJoinDigression;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author jmv
 */
public class ProgressDialog extends JDialog
{
    
    private JProgressBar bar;
    
    public ProgressDialog(JFrame owner) 
    {
        super(owner,false);
        init();
    }
    
    private void init()
    {
        bar=new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.setContentPane(panel);
        panel.add(bar, BorderLayout.CENTER);
        setSize(new Dimension(300,200));
    }
    
    public synchronized void setProgress(int i)
    {
        bar.setValue(i);
    }

}

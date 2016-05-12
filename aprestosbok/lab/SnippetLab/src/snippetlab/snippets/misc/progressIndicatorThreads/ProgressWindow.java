/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.progressIndicatorThreads;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author jtviegas
 */
public class ProgressWindow extends JDialog implements Runnable
{
    private JProgressBar bar=new JProgressBar(JProgressBar.HORIZONTAL);;
    private boolean semaphor=true;
    
    public ProgressWindow(JFrame owner)
    {
        super(owner, false);
    }

    public synchronized void setSemaphor(boolean b)
    {
        this.semaphor=b;
    }
    
    private void init()
    {
        System.out.println("pw init in");
        bar.setMaximum(100);
        bar.setMinimum(0);
        bar.setValue(0);
        bar.setPreferredSize(new Dimension(100,50));
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(bar,BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(300,300));
        this.setContentPane(panel);
        pack();
        System.out.println("pw init out");
    }
    
    public void go()
    {
         System.out.println("pw go in");
        long start = Calendar.getInstance().getTimeInMillis();
        
        while(semaphor)
        {
            
            long now = Calendar.getInstance().getTimeInMillis();
            long diff = now - start;
           
            if(diff > 1000 && diff <= 3000)
                setValue(20);
            else if(diff > 3000 && diff <= 6000)
                setValue(50);
            else if(diff > 6000)
                setValue(80);
            
        }
        
        setVisible(false);
        System.out.println("pw go out");
    }

    private void setValue(final int val)
    {
        SwingUtilities.invokeLater(new Runnable(){

            public void run() 
            {
                bar.setValue(val);
            }
        });
    }
    
    public void run() 
    {
        SwingUtilities.invokeLater(new Runnable(){

            public void run() 
            {
                System.out.println("pw run in");
                init();
                setVisible(true);
            }
        });
        
        go();
        System.out.println("pw run out");
        
    }
    
}

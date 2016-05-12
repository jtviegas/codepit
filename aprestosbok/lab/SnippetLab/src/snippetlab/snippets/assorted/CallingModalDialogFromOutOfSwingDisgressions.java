/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import snippetlab.snippets.AbstractSnippet;

/**
 *
 * @author jtviegas
 */
public class CallingModalDialogFromOutOfSwingDisgressions extends AbstractSnippet
{
    @Override
    public void method() 
    {
        System.out.println("going to call another thing. @EDT?" +  SwingUtilities.isEventDispatchThread() );
        callAnotherThing();
        System.out.println("called another thing - going home now. @EDT?" +  SwingUtilities.isEventDispatchThread() );
    }
    
    
    private void callAnotherThingFroAnotherThread()
    {

        
    }
            
    
    private void callAnotherThing()
    {
        SwingWorker<Void,Void> s = new SwingWorker<Void,Void>()
        {

            @Override
            protected Void doInBackground() throws Exception 
            {
                Void x=null;
                System.out.println("going to open dialog in the edt. @EDT?" +  SwingUtilities.isEventDispatchThread() );
                SwingUtilities.invokeAndWait(new Runnable()
                {

                    public void run() 
                    {
                        System.out.println("setting dialog visible. @EDT?" +  SwingUtilities.isEventDispatchThread() );
                        (new D(null)).setVisible(true);
                        System.out.println("setting dialog not visible. @EDT?" +  SwingUtilities.isEventDispatchThread() );
                    }
                });
                
                System.out.println("dialog closed. @EDT?" +  SwingUtilities.isEventDispatchThread() );
                return x;
            }

            @Override
            protected void done() 
            {
                System.out.println("dialog closed. @EDT?" +  SwingUtilities.isEventDispatchThread() );
            }

        };
        
        s.execute();
    }
    
    
    private class D extends JDialog
    {

        public D(Frame owner) 
        {
            super(owner);
            setModal(true);
            
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());
            JButton b=new JButton("clickme2go");
            p.add(b);
            getContentPane().add(p);
            p.setPreferredSize(new Dimension(200,100));
            b.addActionListener(new ActionListener(){

                public void actionPerformed(ActionEvent e) 
                {
                    System.out.println("button clicked");
                    D.this.setVisible(false);
                }
            });
            p.validate();
            
            
        }
        
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.synchronoustask.Runner;
import snippetlab.snippets.misc.synchronoustask.TaskManagerImpl;

/**
 *
 * @author jtviegas
 */
public class SynchronousTask implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        JButton b = new JButton("do task");
        b.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) 
            {
                TaskManagerImpl tm = new TaskManagerImpl();
                tm.setDescription("doing runner");
                tm.setRunnable(new Runner());
                tm.go();
                System.out.println("SynchronousTask - ...tm go done");
                while(tm.isOn())
                {
                   System.out.println("SynchronousTask - task is on"); 
                }
                
                System.out.println("SynchronousTask - task is off");
                System.out.println("SynchronousTask - ended button action");
            }
        
        });
        panel.add(b, BorderLayout.SOUTH);
    }
    
    
    private class Workit extends SwingWorker<Boolean,Void>
    {

        @Override
        protected Boolean doInBackground() throws Exception 
        {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void done() 
        {
            super.done();
        }
        
    }

}

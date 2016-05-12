/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.synchronoustask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.SwingUtilities;
import snippetlab.snippets.misc.synchronoustask.gui.ProgressDialog;



/**
 *
 * @author jtviegas
 */
public class TaskManagerImpl implements TaskManagerInterface
{
    
    protected boolean on = false;
    protected ProgressDialog pd = new ProgressDialog();
    protected Listener listener = new Listener();
    protected Task task;
    
    protected Runnable runnable;
    protected String description;
    
    public TaskManagerImpl(){}
    
    public TaskManagerImpl(Runnable runnable, String description)
    {
        this.runnable = runnable;
        this.description = description;
    }
    
    public void setRunnable(Runnable runnable)
    {
        this.runnable = runnable;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public void go() 
    {
        setIsON(true);
        task=new Task(runnable);
        task.addPropertyChangeListener(listener);
        System.out.println("task manager - going to start task");
        task.start();
        System.out.println("task manager - started task, falling out of go");
    }
    
    protected void setIsON(boolean state) 
    {
        synchronized(this)
        {
            System.out.println("task manager - set on = " + state);
            setState(state);
            notify();
        }
    }

    public boolean isOn() 
    {
        synchronized(this)
        {
            boolean result = false;
            
            while(on)
            {
                try
                {
                    wait();
                }
                catch(InterruptedException e)
                {       
                    result = false;
                }
                catch(Exception e)
                {
                    result = false;
                }
            }
            
            return result;
        }
    }

    protected void setState(final boolean state)
    {
        if(on == state)
            return;
        
        on=state;

        SwingUtilities.invokeLater(new Runnable()
            {
                public void run() 
                {
                    pd.setVisible(state);
                }
            });

    }

    private class Listener implements PropertyChangeListener
    {

        public void propertyChange(PropertyChangeEvent evt) 
        {

            if(evt instanceof Task.TaskEndedEvent)
            {
                System.out.println("task manager listener - received TaskEndedEvent");
                setIsON(false);
                task.removePropertyChangeListener(this);
            }
                
        }
        
    }
    
}

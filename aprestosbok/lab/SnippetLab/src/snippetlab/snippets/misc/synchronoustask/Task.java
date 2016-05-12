/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.synchronoustask;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author jtviegas
 */
public class Task extends Thread
{
    private PropertyChangeSupport changeSupport= new PropertyChangeSupport(this);
    private Runnable runnable;
    
    
    public Task(Runnable runnable)
    {
        super();
        this.runnable = runnable;
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        changeSupport.removePropertyChangeListener(listener);
    }
    
    @Override
    public void run() 
    {
        try
        {
            System.out.println("Task - going to start runnable");
            runnable.run();
            System.out.println("Task - runnable ended execution");
            
        }
        catch(Exception x)
        {
            System.out.println("Task - OOOPPPSS!!!");
            x.printStackTrace();
        }
        finally
        {
            System.out.println("Task - going to put task manager off");
            changeSupport.firePropertyChange(new TaskEndedEvent());
        }
    }
    
    public class TaskEndedEvent extends PropertyChangeEvent
    {

        public TaskEndedEvent() 
        {
            super(Task.this, "on", true, false);
        }
        
    }


}

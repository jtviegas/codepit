/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.SwingUtilities;

/**
 *
 * @author jtviegas
 */
public class ActionBlinker implements RunnableFuture<Icon>
{
    private Action action;
    private Icon originalIcon;
    private static final String THREAD_PREFIX = "BLINKER_THREAD_";
    private volatile boolean done;
    private volatile boolean cancelled;
    private volatile boolean mayInterrupt;
    private volatile Exception thrown;
    
    
    public ActionBlinker(Action action)
    {
        
        this.action = action;
        originalIcon = (Icon)action.getValue(Action.SMALL_ICON);
        
    }


    private void setIcon(final Icon ico)
    {
        
        if(SwingUtilities.isEventDispatchThread())
        {
            action.putValue(Action.SMALL_ICON, ico);
        }
        else
        {
            SwingUtilities.invokeLater(new Runnable(){

                public void run() 
                {
                    action.putValue(Action.SMALL_ICON, ico);
                }

            });
        }
        
    }

    
    public static String createBlinkerThreadName(Action act)
    {
        
        return THREAD_PREFIX + act.getClass().getName();
        
    }

    
    public void run() 
    {
        
        try
        {
            System.out.println("~~~~~~run start");
        if(cancelled)
            return;
        
         if(null != action.getValue(Action.SMALL_ICON))
            {
                if(cancelled && mayInterrupt)
                    return;
                
                setIcon(null);
            }
            else
            {
                if(cancelled && mayInterrupt)
                    return;
                
                setIcon(this.originalIcon);
            }
        
         done=true;
         System.out.println("~~~~~~run ended");
        }
        catch(Exception x)
        {
            x.printStackTrace();
            thrown = x;
        }
        
        
    }

    
    public synchronized boolean cancel(boolean mayInterruptIfRunning) 
    {
        
        mayInterrupt = mayInterruptIfRunning;
        System.out.println("~~~~~~~cancel start");
        if((!done) && (null==thrown) && (!cancelled))
            cancelled = true;
        
        System.out.println("~~~~~~~cancel ended with " + cancelled);
        return cancelled;
        
    }

    
    public synchronized boolean isCancelled() 
    {
        
        return cancelled;
        
    }

    
    public synchronized boolean isDone() 
    {
        
        return ((done) || (null != thrown) || (cancelled)); 
        
    }

    
    public synchronized Icon get() throws InterruptedException, ExecutionException 
    {
        
        while(!isDone())
        {
            if(cancelled)
                throw new InterruptedException();
            
            if(null!=thrown)
                throw new ExecutionException(thrown);
            
        }
        //always return original icon
        return originalIcon;

    }

    
    public synchronized Icon get(long timeout, TimeUnit unit) throws InterruptedException, 
            ExecutionException, TimeoutException 
    {
        
        long start = System.nanoTime();
        
        while(!(getElapsedTime(start, unit) >= 0L))
        {
            if(cancelled)
                throw new InterruptedException();
            
            if(null!=thrown)
                throw new ExecutionException(thrown);
            
            if(done)
               return originalIcon; 
        }
        
        throw new TimeoutException();
        
    }

    
    private long getElapsedTime(long start, TimeUnit unit)
    {
           long now = System.nanoTime();
           long elapsednanos = now - start;
           long elapsed = 0;
           
           switch(unit)
           {
               case MICROSECONDS:
                   elapsed = elapsednanos/1000L;
                   break;
               case MILLISECONDS:
                   elapsed = elapsednanos/1000000L;
                   break;
               case SECONDS:
                   elapsed = elapsednanos/1000000000L;
                   break;
               case MINUTES:
                   elapsed = elapsednanos/(60*1000000000L);
                   break;
               case HOURS:
                   elapsed = elapsednanos/(60*60*1000000000L);
                   break;
               case DAYS:
                   elapsed = elapsednanos/(24*60*60*1000000000L);
                   break;
               case NANOSECONDS:
                   elapsed = elapsednanos;
                   break;
           }
           
           return elapsed;
           
           
    }
    
    
   }

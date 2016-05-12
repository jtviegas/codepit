/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions;

import snippetlab.snippets.misc.StateManagerDigressions.opm.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import snippetlab.snippets.misc.StateManagerDigressions.opm.OperationsProgressManager;
import snippetlab.snippets.misc.StateManagerDigressions.opm.ProgressManagerListener;

/**
 *
 * @author jtviegas
 */
public class StateManager2 implements PropertyChangeListener
{
    private JLabel label;
    private JFrame frame;
    private BasicQueueInterface theQueue= new BasicQueueImpl();

//    private boolean cancelCalled = false;
    
    public StateManager2(JLabel l,JFrame frame)throws Exception
    {
        this.label = l;
        this.frame = frame;

    }
    
   
    
    public synchronized void propertyChange(PropertyChangeEvent evt) 
    {
        
        
        if(evt.getNewValue() instanceof DefaultMutableTreeNode)
        {
            
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)evt.getNewValue();
            Context c = new ContextBase();
            c.put("node", node);
            c.put("label", this.label);
            w("A* propertychange from a  node:" + node.toString() + "(at EDT[" + SwingUtilities.isEventDispatchThread() + "])");
            
            CallableCommand callablecom = null;
        
            if(evt.getPropertyName().equals("selection"))
            {
                w("A* it's a selection on node:" + node.toString());
                callablecom=new CallableCommand(new DeselectionCommand(), c);
                dispatchEvent(callablecom);
                
                callablecom=new CallableCommand(new SelectionCommand(), c);
                dispatchEvent(callablecom);
                w("A* dispatched callable:" + callablecom.number);
            }
            
        }
        else
        {
            w("A* propertychanges from listening to SwingWorker " + 
                    evt.getPropertyName() + " -> " + evt.getNewValue().toString());
            if(evt.getNewValue() instanceof SwingWorker.StateValue)
            {
               if(evt.getNewValue().equals(SwingWorker.StateValue.DONE))
               {
                   frame.getGlassPane().setVisible(false);
                   System.out.println("* glasspane is off");
               }
            }
        }
        

        
    }
    
    private void dispatchEvent(CallableCommand c)
    {
        boolean result = false;
                
        frame.getGlassPane().setVisible(true);
        w("B+ glasspane on for callable:" + c.number);
        
        w("B+ dispatching event (at EDT[" + SwingUtilities.isEventDispatchThread() + "]) for callable:" + c.number);
        try
        {
            
            
            SW s=new SW("id" + c.number , c, theQueue);
            s.addPropertyChangeListener(this);
            w("B+ going to execute SW for callable:" + c.number);
            s.execute();
            w("B+ executed SW for callable:" + c.number);
            
	}
	catch(Exception x)
	{
        	w("Z+ !!! exception at DispatchEvent !!!");
                frame.getGlassPane().setVisible(false);
                System.out.println("* glasspane off for callable:" + c.number);
	}
        finally
        {
           w("B+ gan yam for callable:" + c.number); 
        }
    }

    public interface BasicQueueInterface
    {
      public void setFree(boolean flag);
      public boolean isFree() ;
    } 
    
    public class BasicQueueImpl implements BasicQueueInterface
    {
        private boolean free = true;
        
        public void setFree(boolean flag) 
            {
            synchronized(this)
            {
                this.free = flag;
                notify();
            }
        }

        public boolean isFree() 
        {
            synchronized(this)
            {
                boolean result = true;

                while(!free)
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
        
    }
    
    
    
    private class SW extends SwingWorker<Boolean, Void> implements ProgressManagerListener
    {
        
        private CallableCommand callable;
        private Future<Boolean> future;
        private OperationsProgressManager o;
        private String opid;
        ExecutorService exec = Executors.newFixedThreadPool(1);
        private BasicQueueInterface queue;
        
        public SW(String opid, CallableCommand callable, BasicQueueInterface queue)throws Exception
        {
            this.opid = opid;
            this.callable = callable;
            this.o = OperationsProgressManager.getInstance();
            this.queue = queue;
            o.addListener(this);
        }
        
        @Override
        protected Boolean doInBackground() throws Exception 
        {
            boolean result=true;
            
            if(!queue.isFree())
                return false;
            
            queue.setFree(false);
            
            
            w("W» in background (at EDT[" + SwingUtilities.isEventDispatchThread() + "]) - for callable:" + this.callable.number);
            w("W» in background - going to show frame for callable:" + this.callable.number);
            showFrame();
            w("W» in background - frame shown for callable:" + this.callable.number);
            w("W» in background - going to submit callable:" + this.callable.number + " for execution");
            future = exec.submit(this.callable);
            w("W» in background - submited callable:" + this.callable.number + " for execution");
            w("W» in background - going to call get() for callable:" + this.callable.number);
            result = future.get();
            w("W» in background - get() for callable:" + this.callable.number + " returned " + result);
            
            
            return result;
        }

        @Override
        protected void done() 
        {
            w("W» in done at EDT(" + SwingUtilities.isEventDispatchThread() + ") - going to hide frame for callable:" + this.callable.number);
            
            hideFrame();
            
            w("W» in done -  frame hidden for callable:" + this.callable.number);
            queue.setFree(true);
        }
 
        public void operationInProgress(boolean isInProgress) 
        {
            if(!isInProgress)
            {
                if(null != future && (!future.isDone()))
                {
                    w("X» in operationInProgress (at EDT[" + SwingUtilities.isEventDispatchThread() + "]) -  frame asked for callable :" + this.callable.number + " cancellation");
                    future.cancel(true);
                    w("X» in operationInProgress -  callable :" + this.callable.number + " cancelled");
                }
                
            }
        }
        
        private void showFrame()throws Exception
        {
            SwingUtilities.invokeAndWait(new Runnable()
            {

                public void run() 
                {
                    w("C'» in showFrame at EDT(" + SwingUtilities.isEventDispatchThread() + ") - starting frame operation for callable :" + callable.number);
                    o.startOperation(opid, "...");
                    w("C'» in showFrame at EDT - started frame operation for callable :" + callable.number);
                }
            
            });

            
        }
        private void hideFrame()
        {
             w("D'» in hideFrame at EDT(" + SwingUtilities.isEventDispatchThread() + ") - stopping frame operation for callable :" + callable.number);
            o.endOperation(opid);
            w("D'» in showFrame at EDT - stopped frame operation for callable :" + callable.number);
        }
        
    }
    
    public void w(String s)
    {
        System.out.println("..." + s);
    }
    
        
    
    
}

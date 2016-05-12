/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.Callable;
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
public class StateManager implements PropertyChangeListener
{
    private JLabel label;
    private JFrame frame;

//    private boolean cancelCalled = false;
    
    public StateManager(JLabel l,JFrame frame)throws Exception
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
        
       CallableCommand callable = null;
        
        if(evt.getPropertyName().equals("selection"))
        {
            System.out.println("* it's a selection");
            callable=new CallableCommand(new DeselectionCommand(), c);
            dispatchEvent(callable);
            callable=new CallableCommand(new SelectionCommand(), c);
            dispatchEvent(callable);
        }
            
        }
        else
        {
            System.out.println("* property changes at state manager \n" + 
                    evt.getPropertyName() + " -> " + evt.getNewValue().toString());
            if(evt.getNewValue() instanceof SwingWorker.StateValue)
            {
               if(evt.getNewValue().equals(SwingWorker.StateValue.DONE))
               {
                   frame.getGlassPane().setVisible(false);
                   System.out.println("* glasspane off");
               }
            }
        }
        

        
    }
    
    private void dispatchEvent(CallableCommand c)
    {
        boolean result = false;
                
        frame.getGlassPane().setVisible(true);
        System.out.println("* glasspane on");
        
        System.out.println("* dispatching event for callable " + c.number);
        try
        {
            SW s=new SW("id" + c.number , c);
            s.execute();
            s.addPropertyChangeListener(this);
	}
	catch(Exception x)
	{
        	System.out.println("* !!! exception at DispatchEvent !!!");
                frame.getGlassPane().setVisible(false);
                System.out.println("* glasspane off");
	}
        
        System.out.println("* dispatch event for callable " + c.number + " returned " + result);
    }


    
    private class WorkingDialog extends Thread
    {
        private OperationsProgressManager o;
        private String opid;
        private ProgressManagerListener pml;
        
        public WorkingDialog(String opid, ProgressManagerListener pml) throws Exception
        {
            this.opid = opid;
            this.o = OperationsProgressManager.getInstance();
            this.pml = pml;
            this.o.addListener(this.pml);
        }
        
        @Override
        public void run() 
        {
            try
            {
                 show();
            while(!isInterrupted())
            {
                try
                {
                    Thread.sleep(1000);    
                }
                catch(InterruptedException x)
                {
                    System.out.println("working dialog - thread interrupted");
                    break;
                }
            }
            
            }
            catch(Exception x)
            {
                System.out.println("working dialog - exception in thread");
            }
            finally
            {
                hide();  
                this.o.removeListener(pml);
            }
           
        }
        
        private void show()throws Exception
        {
            SwingUtilities.invokeAndWait(new Runnable()
            {

                public void run() 
                {
                    o.startOperation(opid, "...");
                }
            
            });

            
        }
        private void hide()
        {
            
             SwingUtilities.invokeLater(new Runnable()
            {

                public void run() 
                {
                    o.endOperation(opid);
                }
            
            });
            
        }
        
    }

    private class SW extends SwingWorker<Boolean, Void> implements ProgressManagerListener
    {
        
        private WorkingDialog wd;
        private Callable<Boolean> callable;
        private Future<Boolean> future;
        
        ExecutorService exec = Executors.newFixedThreadPool(1);
        
        public SW(String opid, Callable<Boolean> callable)throws Exception
        {
            this.wd = new WorkingDialog(opid, this);
            this.callable = callable;
        }
        
        @Override
        protected Boolean doInBackground() throws Exception 
        {
            boolean result=true;
            
            wd.start();
            
            future = exec.submit(this.callable);
            result = future.get();
            
            return result;
        }

        @Override
        protected void done() 
        {
            wd.interrupt();
        }
 
        public void operationInProgress(boolean isInProgress) 
        {
            if(!isInProgress && (!future.isDone()))
            {
                System.out.println("**** going to cancel future");
                future.cancel(true);
            }
        }
        
    }
    
    
    
    
}

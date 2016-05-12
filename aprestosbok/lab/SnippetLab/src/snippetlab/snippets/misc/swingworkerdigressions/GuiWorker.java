/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.swingworkerdigressions;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.RootPaneContainer;
import javax.swing.SwingWorker;

/**
 *
 * @author jtviegas
 */
public class GuiWorker<T,E> extends SwingWorker<T,E>
{
 
    protected Callable<T> callable;
    protected RootPaneContainer rootpane;
    
    public GuiWorker(Callable<T> callable, RootPaneContainer rootpane)
    {
        this.callable = callable;
        this.rootpane = rootpane;
    }
    
    @Override
    protected T doInBackground() throws Exception 
    {
        System.out.println("GuiWorker - started doInBackground");
        T result = null;
        
        ExecutorService exe = Executors.newSingleThreadExecutor();
        
        Future<T> future = exe.submit(callable);
        try
        {
            System.out.println("GuiWorker - doInBackground - going to call get() from callable");
            result = future.get();
            System.out.println("GuiWorker - doInBackground - got " + result + " from callable get()");
        }
        catch(CancellationException x)
        {
            x.printStackTrace();
        }
        catch(ExecutionException x)
        {
            x.printStackTrace();
        }
        catch(InterruptedException x)
        {
            x.printStackTrace();
        }
        System.out.println("GuiWorker - ended doInBackground");
        return result;
    }

    @Override
    protected void done() 
    {
        System.out.println("GuiWorker - started done");
        System.out.println("GuiWorker - going to unset glass pane");
        this.rootpane.getGlassPane().setVisible(false);
        System.out.println("GuiWorker - glass pane was unset");
        System.out.println("GuiWorker - ended done");
    }
    
    
}

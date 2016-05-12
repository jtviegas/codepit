/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.SynchronousTimedWorker;

import java.awt.Window;
import snippetlab.snippets.misc.swingworkerdigressions.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 *
 * @author jtviegas
 */
public class GuiSynchronousWorker<T,E,V extends Window> extends SwingWorker<T,E>
{
 
    protected Callable<T> callable;
    protected V rootpane;
    protected IsWorkingWindow window;
    
    public GuiSynchronousWorker(Callable<T> callable, V rootpane)
    {
        this.callable = callable;
        this.rootpane = rootpane;
        window = new IsWorkingWindow(this.rootpane, true);
        window.init();
    }
    
    @Override
    protected T doInBackground() throws Exception 
    {
        System.out.println("GuiWorker - started doInBackground");
        T result = null;
        
        doWorkingNotice(true);
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
        doWorkingNotice(false);
        System.out.println("GuiWorker - glass pane was unset");
        System.out.println("GuiWorker - ended done");
    }
    
    private void doWorkingNotice(final boolean flag)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run() 
            {
                if(rootpane instanceof JDialog)
                    ((JDialog)rootpane).getGlassPane().setVisible(flag);
                 
                if(rootpane instanceof JFrame)
                    ((JFrame)rootpane).getGlassPane().setVisible(flag);
                
                window.setVisible(flag);
                
            }
        });
        
    }
    
}

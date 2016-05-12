/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import snippetlab.snippets.AbstractSnippet;

/**
 *
 * @author jtviegas
 */
public class RunnableFutureDigressions extends AbstractSnippet
{

    @Override
    public void method() 
    {
       //runnit(true);
       runnit(false);
    }
    
    
    
    private void runnit(boolean onEDT)
    {
        
        if(onEDT)
        {
            System.out.println("going to edt to find some answers");
            SwingUtilities.invokeLater(new Runnable(){

                public void run() 
                {
                    try
                    {
                        System.out.println("what am i? " + whatAmI());
                    }
                    catch(Exception x)
                    {
                        x.printStackTrace();
                    }
                }
            });
        }
        else
        {
            System.out.println("going out from edt to find some answers");
            SwingWorker<Void,Void> s = new SwingWorker<Void, Void>() {

                @Override
                protected Void doInBackground() throws Exception 
                {
                    Void s = null;
                    System.out.println("what am i? " + whatAmI());
                    return s;
                }
            };
            
            s.execute();
        }
        
    }
    
    private String whatAmI()throws Exception
    {
        String result=null;
        
        Callable<String> callable= new Callable<String>()
        {

            public String call() throws Exception 
            {
                System.out.println("i'm meditating at the edt?" + SwingUtilities.isEventDispatchThread());
                Thread.sleep(3000);
                return "i'm a jitterbug boy";
            }
            
        };
        FutureTask<String> t = new FutureTask<String>(callable);
        
        if(SwingUtilities.isEventDispatchThread())
        {
            System.out.println("going to find what am i on edt");
            t.run();
        }
        else
        {
            System.out.println("going to find what am i out of edt");
            SwingUtilities.invokeAndWait(t);
        }
        
        
        result = t.get();
        
        return result;
    }

}

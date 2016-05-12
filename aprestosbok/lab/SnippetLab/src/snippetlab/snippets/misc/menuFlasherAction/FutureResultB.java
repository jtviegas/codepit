/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 * @author jmv
 */
public class FutureResultB<V> 
{
    private volatile V result;
    private volatile Exception thrown;
    private final FutureTask<V> task;

    public FutureResultB() 
    {
        Callable<V> resultReturner = new Callable<V>() {
            public V call() throws Exception 
            {
                if (thrown != null) 
                {
                    throw thrown;
                } else 
                {
                    return result;
                }
            }
        };
        task = new FutureTask<V>(resultReturner);
    }

    public void set(V result) 
    {
        this.result = result;
        task.run();
    }

    public void setException(Exception problem) 
    {
        this.thrown = problem;
        task.run();
    }

    public V get() throws InterruptedException, ExecutionException 
    {
        return task.get();
    }
}

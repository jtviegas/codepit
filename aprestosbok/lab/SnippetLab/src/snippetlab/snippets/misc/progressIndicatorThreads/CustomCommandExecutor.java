/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.progressIndicatorThreads;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 *
 * @author jtviegas
 */
public class CustomCommandExecutor implements Observer
{
    private static final String WINDOW_PREFIX = "WINDOW_TASK_";
    private static CustomCommandExecutor object;
    
    private ExecutorService executor;
    Random random = new Random();
    
    private Map<String,Future> tasks = new HashMap<String,Future>();
    
    private CustomCommandExecutor()
    {
        executor = Executors.newFixedThreadPool(3);
    }
    
    public synchronized static CustomCommandExecutor getInstance()
    {
        if(null==object)
            object= new CustomCommandExecutor();
        
        return object;
    }
    
    
    public synchronized String executeCommand(Command command, Context context, String name) throws Exception
    {
        
        String taskId = String.valueOf(random.nextInt());
                
        CommandRunnableWrapper runnable = 
                new CommandRunnableWrapper(command, context, taskId);
        ProgressWindow window = new ProgressWindow(null);
        
        runnable.addObserver(this);
        
        tasks.put(WINDOW_PREFIX + taskId, executor.submit(window));
        tasks.put(taskId, executor.submit(runnable));

        
        return taskId;
    }

    public synchronized void update(Observable o, Object arg) 
    {
        if(arg instanceof CommandRunnableWrapper)
        {
            CommandRunnableWrapper x = (CommandRunnableWrapper)arg;
            
            String id = x.getId();
            String wxid = WINDOW_PREFIX + id;
            
            Future<?> t = tasks.remove(id);
            Future<?> w = tasks.remove(wxid);
            
            t.cancel(true);
            w.cancel(true);
            
        }
    }
    
    
    
    
}

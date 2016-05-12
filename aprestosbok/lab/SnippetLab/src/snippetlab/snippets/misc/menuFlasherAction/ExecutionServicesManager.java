/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author jtviegas
 */
public class ExecutionServicesManager 
{

//    private static EidLogger logger = (EidLogger)EidLogger.getInstance(RelativeTimeScheduler.class.getName());
        
        private static ExecutionServicesManager instance;
        private ScheduledExecutorService scheduler;

        private Map<String, ScheduledFuture<?>> periodicfutures;
        
        private ExecutionServicesManager()
        {
            periodicfutures = new HashMap<String, ScheduledFuture<?>>();
        }
        
        public static ExecutionServicesManager getInstance()
        {
            if(null == instance)
                instance = new ExecutionServicesManager();
            
            return instance;
        }
        
        private ScheduledExecutorService getScheduledExecutor()
        {
            if(null == scheduler)
                scheduler = Executors.newScheduledThreadPool(3);
            
            return scheduler;
        }

        
        public synchronized ScheduledFuture<?> unschedulePeriodicRun(String taskname)
        {
            ScheduledFuture<?> result= null;

            
            if(periodicfutures.containsKey(taskname))
            {
                result = (ScheduledFuture<?>)periodicfutures.get(taskname);
                result.cancel(false);
                periodicfutures.remove(taskname);
            }
            return result;
        }
        
        public boolean isRunningPeriodically(String taskname)
        {
            boolean result = false;
            
            if(null != periodicfutures.get(taskname))
                result=true;
            
            return result;
        }
        

	
        public synchronized void schedulePeriodicRun(Callable<Object> callable,String name,  
			                                     long initialDelayInMillis, 
			                                     long periodInMillis)
	{
            
//		logger.enter("schedulePeriodicRun");

            FutureResult<Object> future = new FutureResult<Object>();
            
            Runnable command = future.setter(callable);

            ScheduledFuture<?> sf = getScheduledExecutor().scheduleAtFixedRate(
                        command, initialDelayInMillis, periodInMillis, 
                        TimeUnit.MILLISECONDS);

            periodicfutures.put(name, sf);
            
//		logger.leave("schedulePeriodicRun");

	}
        

                
	public synchronized void shutdown()
	{
//		logger.enter("shutdown");
                
            for(String taskname : periodicfutures.keySet())
                   unschedulePeriodicRun(taskname);
                
                periodicfutures.clear();
		getScheduledExecutor().shutdown();	
                
//		logger.leave("shutdown");
	}

        
}

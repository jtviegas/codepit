/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 *
 * @author jtviegas
 */
public class CustomThread  extends Thread 
{
    
//    private static final EidLogger logger = (EidLogger) EidLogger.getLogger(CustomThread.class);	
	
	public CustomThread(Runnable r)
	{
		super(r);
	}
	
	public CustomThread(Runnable r, ThreadNameGenerator ng)
	{
		super(r, ng.getNewName());
		setUncaughtExceptionHandler(
			new Thread.UncaughtExceptionHandler()
			{
				public void uncaughtException(Thread t, Throwable e)
				{
//					logger.error("Uncaught exception killed thread " + t.getName(), e);
                                    System.out.println("Uncaught exception killed thread " + t.getName());
                                    e.printStackTrace();
				}
			}
		);
	}
        
        public CustomThread(Runnable r, String name)
	{
		super(r, name);
		setUncaughtExceptionHandler(
			new Thread.UncaughtExceptionHandler()
			{
				public void uncaughtException(Thread t, Throwable e)
				{
//					logger.error("Uncaught exception killed thread " + t.getName(), e);
                                    System.out.println("Uncaught exception killed thread " + t.getName());
                                    e.printStackTrace();
				}
			}
		);
	}

        public CustomThread(Callable<?> r, String name)
	{
		super(new FutureTask(r), name);
		setUncaughtExceptionHandler(
			new Thread.UncaughtExceptionHandler()
			{
				public void uncaughtException(Thread t, Throwable e)
				{
//					logger.error("Uncaught exception killed thread " + t.getName(), e);
                                    System.out.println("Uncaught exception killed thread " + t.getName());
                                    e.printStackTrace();
				}
			}
		);
	}

}

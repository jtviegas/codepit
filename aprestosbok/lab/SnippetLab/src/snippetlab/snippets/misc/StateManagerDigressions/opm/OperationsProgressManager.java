/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions.opm;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

/**
 *
 * @author jtviegas
 */
public class OperationsProgressManager 
{

	private final static long SHOW_DIALOG_TIME_LIMIT = 3000L;
	
	private static OperationsProgressManager instance = null;

	/**
	 * Initialize the singleton. This must be the first method to be called.
	 * @param sb	- status bar where to display the progress bar
	 */
	public static synchronized void initialize()
	throws Exception
	{
		if(instance != null) throw new Exception("OperationsProgressManager");

		instance = new OperationsProgressManager();
	}
	
	public static synchronized void shutdown()
	throws Exception
	{
		if(instance == null) throw new Exception("OperationsProgressManager");
		instance.shutdownManager();
		instance = null;
	}
	
	/**
	 * Return the singleton instance. Method initialize() must have been
	 * called before this is called.
	 * @return	the singleton instance
	 */
	public static synchronized OperationsProgressManager getInstance()
	throws Exception
	{
		if(instance == null) throw new Exception("OperationsProgressManager");
		
		return instance;
	}
	

	private ScheduledExecutorService dialogProgressExecutor;


	private Set<String> registeredOperations;
	private Set<ProgressManagerListener> listeners;
	
	

	/**
	 * Manager constructor.
	 * @param sb	- status bar where to show the progress bar
	 */
	private OperationsProgressManager()
	{


//		dialogProgressExecutor = Executors.newSingleThreadScheduledExecutor();
		dialogProgressExecutor = Executors.newScheduledThreadPool(5);
		registeredOperations = new HashSet<String>();
		listeners = new HashSet<ProgressManagerListener>();
	}
	
	/**
	 * Signalize that a new operation started running.
	 * @param operationKey	- a unique operation identifier
	 * @param operationText	- text to display about the operation
	 */
	public synchronized void startOperation(String opKey, String dialogText)
	{


		
		registeredOperations.add(opKey);
		try
		{

			DialogProgressRunnable diagProgRunnable = new DialogProgressRunnable(opKey, dialogText, this);
			dialogProgressExecutor.schedule(diagProgRunnable, SHOW_DIALOG_TIME_LIMIT, TimeUnit.MILLISECONDS);
			
			if(registeredOperations.size() == 1) fireEvent(true);
		}
		catch(Exception e)
		{
			System.out.println("startOperation: unexpected exception - " + e);
			registeredOperations.remove(opKey);
		}
		

		System.out.println("startOperation: dialog progress to be shown in " + SHOW_DIALOG_TIME_LIMIT + " miliseconds");
		System.out.println("startOperation");
	}
	
	/**
	 * Provides a way for a thread to check whether a given operation is
	 * still running or if it is already done.
	 * @param opKey	- unique operation identifier of the operation to check
	 * @return	whether the operation is still running (true) or whether it
	 * is done (false) 
	 */
	synchronized boolean isRunning(String opKey)
	{
		return registeredOperations.contains(opKey);
	}
	
	/**
	 * Signalizes that a previously running operation stopped running.
	 * @param operationKey	- the unique operation identifier
	 */
	public synchronized void endOperation(String operationKey)
	{
		System.out.println("endOperation");
		if(!registeredOperations.isEmpty())
		{
			registeredOperations.remove(operationKey);
			if(registeredOperations.isEmpty()) fireEvent(false);
		}
		System.out.println("endOperation");		
	}
	
	public synchronized void addListener(ProgressManagerListener l)
	{
		listeners.add(l);
	}
	
	public synchronized void removeListener(ProgressManagerListener l)
	{
		listeners.remove(l);
	}
	
	private void fireEvent(final boolean value)
	{
		if(SwingUtilities.isEventDispatchThread())
		{
			fireEventForAll(value);
		}
		else
		{
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					fireEventForAll(value);					
				}
			});
		}
	}
	
	private void fireEventForAll(boolean value)
	{
		for(ProgressManagerListener listener: listeners)
		{
			listener.operationInProgress(value);
		}		
	}
	
	/**
	 * Stop all executors and clear all queues.
	 */
	private synchronized void shutdownManager()
	{
		listeners.clear();
		registeredOperations.clear();
		dialogProgressExecutor.shutdown();
	}
	

    
}

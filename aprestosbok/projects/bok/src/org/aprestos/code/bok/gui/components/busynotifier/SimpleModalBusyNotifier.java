/*
 * BusyNotifier.java
 */
package org.aprestos.code.bok.gui.components.busynotifier;

import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;


class SimpleModalBusyNotifier extends BusyNotifierFactory
{
	
	private final static long SHOW_BAR_TIME_LIMIT = 500L;
	private final static long SHOW_DIALOG_TIME_LIMIT = 2500L;
	
	public static final String PROPERTY_NAME="ISBUSY";
	private JPanel statusBar;
	private boolean busy;
	private ScheduledExecutorService barExecutor;
	private ScheduledExecutorService dialogExecutor;
	private Window guiOwner;
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	
	/**
	 * Manager constructor.
	 * @param sb	- status bar where to show the progress bar
	 */
	public SimpleModalBusyNotifier(Window guiOwner, JPanel statusBar)
	{
		if(null != statusBar)
		{
			this.statusBar = statusBar;
			barExecutor = Executors.newSingleThreadScheduledExecutor();
		}
		this.guiOwner = guiOwner;
		dialogExecutor = Executors.newScheduledThreadPool(1);
	}
	
	
	/**
	 * @see snippetlab.snippets.swing.BusyNotifier.BusyNotifierInterface#isBusy()
	 */
	public boolean isBusy()
	{
		return busy;
	}


	public void setBusy(boolean busy)
	{
		this.busy = busy;
	}


	/**
	 * @see snippetlab.snippets.swing.BusyNotifier.BusyNotifierInterface#stop()
	 */
	public synchronized void stop()
	{
		PropertyChangeListener[] listeners = 
			changeSupport.getPropertyChangeListeners();
		for (PropertyChangeListener o:listeners)
			changeSupport.removePropertyChangeListener(o);

		dialogExecutor.shutdown();
		
		if(null != statusBar)
			barExecutor.shutdown();
		
		this.busy = false;
		fireEvent(false);
	}
	
	/**
	 * @see snippetlab.snippets.swing.BusyNotifier.BusyNotifierInterface#start(java.lang.String, java.lang.String, java.lang.String)
	 */
	public synchronized void start(String barText, String dialogText, String dialogTitle)
	{
			this.busy = true;
			
			if(null != statusBar)
			{
				StatusbarNotifier barRunnable = new StatusbarNotifier(this, barText,  statusBar);
				barExecutor.schedule(barRunnable, SHOW_BAR_TIME_LIMIT, TimeUnit.MILLISECONDS);
			}
			
			DialogNotifier dialogRunnable = new DialogNotifier(this, this.guiOwner, dialogText, dialogTitle);
			dialogExecutor.schedule(dialogRunnable, SHOW_DIALOG_TIME_LIMIT, TimeUnit.MILLISECONDS);
			fireEvent(true);

	}

	/**
	 * @see snippetlab.snippets.swing.BusyNotifier.BusyNotifierInterface#addListener(java.beans.PropertyChangeListener)
	 */
	public synchronized void addListener(PropertyChangeListener l)
	{
		changeSupport.addPropertyChangeListener(l);
	}
	
	/**
	 * @see snippetlab.snippets.swing.BusyNotifier.BusyNotifierInterface#removeListener(java.beans.PropertyChangeListener)
	 */
	public synchronized void removeListener(PropertyChangeListener l)
	{
		changeSupport.removePropertyChangeListener(l);
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
			for(PropertyChangeListener o: changeSupport.getPropertyChangeListeners())
			{
				o.propertyChange(new PropertyChangeEvent(this,PROPERTY_NAME,value,null));
			}
	}

}

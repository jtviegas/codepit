/*
 * BusyNotifierInterface.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.BusyNotifier;

import java.beans.PropertyChangeListener;

public interface BusyNotifierInterface
{

	public boolean isBusy();

	public void stop();

	/**
	 * Signalize that a new operation started running.
	 * @param barText	
	 * 		text to display in the status bar during the operation
	 * @param dialogText
	 * 		text to display in the dialog window
	 * @param dilaogTitle
	 * 		dialog caption title
	 * 
	 */
	public void start(String barText, String dialogText, String dialogTitle);

	public void addListener(PropertyChangeListener l);

	public void removeListener(PropertyChangeListener l);

}

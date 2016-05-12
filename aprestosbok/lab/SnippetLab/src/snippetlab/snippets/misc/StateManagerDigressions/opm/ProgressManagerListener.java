/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions.opm;

/**
 *
 * @author jtviegas
 */
public interface ProgressManagerListener 
{
	/**
	 * Called when there is no operation in progress and
	 * suddenly a new operation is registered (with param
	 * true) or vice-versa (with false). Note that this is
	 * not called if an operation is registered and there
	 * is already one in progress.
	 */
	public void operationInProgress(boolean isInProgress);
}

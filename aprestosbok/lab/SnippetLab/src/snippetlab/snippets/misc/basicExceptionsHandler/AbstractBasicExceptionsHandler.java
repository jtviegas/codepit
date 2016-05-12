/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.basicExceptionsHandler;

import javax.swing.JOptionPane;

/**
 *
 * @author jtviegas
 */
public abstract class AbstractBasicExceptionsHandler
{


	public abstract void handle(Throwable x);

	protected void showExceptionMessage(String msg)
	{
            JOptionPane.showMessageDialog(null,msg , "EXCEPTION", JOptionPane.ERROR_MESSAGE);
	}
        
}

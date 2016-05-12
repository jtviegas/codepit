/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.StateManagerDigressions;

import java.util.concurrent.Callable;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 *
 * @author jtviegas
 */
public class CallableCommand implements Callable<Boolean>
{
	private Command command;
	private Context context;
	
        private static int counter = 0;
        public int number;
        
	public CallableCommand()
        {
            number = ++counter;
        }
	
	public CallableCommand(Command command, Context context)
	{
            this();
		this.command = command;
		this.context = context;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(Command command)
	{
		this.command = command;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context)
	{
		this.context = context;
	}

	@Override
	public java.lang.Boolean call() throws Exception
	{
            System.out.println("£ command " + number + " started execution");
            boolean result = this.command.execute(this.context);
            System.out.println("£ command " + number + " ended execution");
            return result;
	}

}

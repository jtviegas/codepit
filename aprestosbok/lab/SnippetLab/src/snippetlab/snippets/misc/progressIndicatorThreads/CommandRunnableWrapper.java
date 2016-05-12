/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.progressIndicatorThreads;

import java.util.Observable;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

/**
 *
 * @author jtviegas
 */
public class CommandRunnableWrapper extends Observable implements Runnable
{
    private Command command;
    private Context context;
    private String id;
    
    private Exception thrown;
    
    public CommandRunnableWrapper(){}

    public CommandRunnableWrapper(Command command, Context context, String id)
    {   
        this.command=command;
        this.context = context;
        this.id = id;
    }
    
    public void setCommand(Command command) {
        this.command = command;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean wasExceptionThrown() {
        return (null != thrown);
    }
    
    public Exception getException()
    {
        return thrown;
    }
    
    public void run() 
    {
        
        try
        {
            command.execute(context);
        }
        catch(Exception ex)
        {
            thrown=ex;
        }
        finally
        {
            setChanged();
            notifyObservers(this);    
        }

    }

}

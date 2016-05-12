/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.basicExceptionsHandler;

/**
 *
 * @author jtviegas
 */
public class BasicExceptionHandler extends AbstractBasicExceptionsHandler
{

    @Override
    public void handle(Throwable x) 
    {
        
        if(x instanceof AException)
        {
            showExceptionMessage(x.getClass().getName());
            return;
        }
        if(x instanceof AException)
        {
            showExceptionMessage(x.getClass().getName());
            return;
        }
        if(x instanceof Exception)
        {
            showExceptionMessage(x.getClass().getName());
        }
        
    }
    

}

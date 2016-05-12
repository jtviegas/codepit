/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadFactory;

/**
 *
 * @author jtviegas
 */
public class CustomThreadFactory  implements ThreadFactory 
{
	private final ThreadNameGenerator nameGenerator;
	
	public CustomThreadFactory(String t)
	{
		nameGenerator = new ThreadNameGenerator(t);
	}
	
	@Override
	public Thread newThread(Runnable runnable) 
	{
		return new CustomThread(runnable, nameGenerator);
	}
        
        public Thread newThread(Runnable runnable, String name) 
	{
		return new CustomThread(runnable, name);
	}
        
        public Thread newThread(Callable<?> callable, String name) 
	{
            return new CustomThread(callable, name);
	}
        
}

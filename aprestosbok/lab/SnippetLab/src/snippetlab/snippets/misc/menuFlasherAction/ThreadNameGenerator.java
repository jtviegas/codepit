/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.menuFlasherAction;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author jtviegas
 */
public class ThreadNameGenerator 
{
    
    	private final String text;
	private final AtomicInteger counter;
	
	public ThreadNameGenerator(String t)
	{
		text = t + "-";
		counter = new AtomicInteger(0);
	}
	
	public String getNewName()
	{
		return text + counter.incrementAndGet();
	}

}

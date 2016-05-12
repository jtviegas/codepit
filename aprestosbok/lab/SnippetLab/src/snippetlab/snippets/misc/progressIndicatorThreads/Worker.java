/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.progressIndicatorThreads;

import java.util.Calendar;
import java.util.Observable;

/**
 *
 * @author jtviegas
 */
public class Worker extends Observable implements Runnable
{

    public void run() 
    {
        System.out.println("worker run in");
        long diff=0;
        long start = Calendar.getInstance().getTimeInMillis();
        
        while(diff < 10000)
        {
            long now = Calendar.getInstance().getTimeInMillis();
            diff = now - start;    
        }
        
        setChanged();
        notifyObservers();
        
        System.out.println("worker run out");
    }


}

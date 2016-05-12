/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.synchronoustask;

import java.util.Random;

/**
 *
 * @author jtviegas
 */
public class Runner implements Runnable
{

    public void run() 
    {
        System.out.println("started runner");
        try
        {
            Thread.sleep((new Random()).nextInt(100) * 100);
        }
        catch(Exception x)
        {
            x.printStackTrace();
        }
        System.out.println("ended runner");
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.swingworkerdigressions;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 * @author jtviegas
 */
public class CallableImpl implements Callable<Boolean>
{

    public Boolean call() throws Exception 
    {
        System.out.println("CallableImpl - started call");
        Thread.sleep((new Random()).nextInt(100) * 100);
        System.out.println("CallableImpl - ended call");
        return true;
    }

}

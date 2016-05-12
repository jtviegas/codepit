/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab;

import snippetlab.boot.SwingBoot;

/**
 *
 * @author jtviegas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
	{
		try
		{
			new SwingBoot();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}

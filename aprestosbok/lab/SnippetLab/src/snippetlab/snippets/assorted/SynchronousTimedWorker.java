/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.JFrame;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.misc.SynchronousTimedWorker.CallableImpl;
import snippetlab.snippets.misc.SynchronousTimedWorker.GuiSynchronousWorker;

/**
 *
 * @author jtviegas
 */
public class SynchronousTimedWorker extends AbstractSnippet
{

    @Override
    public void method() 
    {
         System.out.println("SwingWorkerDigressions - going to set glasspane");
        frame.getGlassPane().setVisible(true);
        System.out.println("SwingWorkerDigressions - glasspane was set");
        GuiSynchronousWorker<Boolean,Void,JFrame> w = 
                new GuiSynchronousWorker<Boolean,Void,JFrame>(new CallableImpl(), frame);
        System.out.println("SwingWorkerDigressions - going to start gui worker");
        w.execute();
        System.out.println("SwingWorkerDigressions - gui worker started and i'm going home");
    }
    
}

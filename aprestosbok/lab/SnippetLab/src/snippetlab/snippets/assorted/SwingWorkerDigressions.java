/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.misc.swingworkerdigressions.CallableImpl;
import snippetlab.snippets.misc.swingworkerdigressions.GuiGlassPane;
import snippetlab.snippets.misc.swingworkerdigressions.GuiWorker;

/**
 *
 * @author jtviegas
 */
public class SwingWorkerDigressions extends AbstractSnippet
{
    
    @Override
    public void init()
    {
        super.init();
        frame.getRootPane().setGlassPane(new GuiGlassPane());
    }
    
    @Override
    public void method() 
    {
        System.out.println("SwingWorkerDigressions - going to set glasspane");
        frame.getGlassPane().setVisible(true);
        System.out.println("SwingWorkerDigressions - glasspane was set");
        GuiWorker<Boolean,Void> w = new GuiWorker<Boolean,Void>(new CallableImpl(), frame);
        System.out.println("SwingWorkerDigressions - going to start gui worker");
        w.execute();
        System.out.println("SwingWorkerDigressions - gui worker started and i'm going home");
    }


    
}

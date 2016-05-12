/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.progressIndicatorThreads.ProgressWindow;
import snippetlab.snippets.misc.progressIndicatorThreads.Worker;

/**
 *
 * @author jtviegas
 */
public class ProgressIndicatorThreads implements SnippetInterface, Observer
{
    ProgressWindow pw;
    public void doIt(JFrame frame, JPanel panel)
    {   
        System.out.println("doIt in");
        Worker w=new Worker();
        w.addObserver(this);
        
        pw = new ProgressWindow(frame);
        
        Executor ex = Executors.newFixedThreadPool(3);
        ex.execute(pw);
        ex.execute(w);

        System.out.println("doIt out");
    }

    public void go(JPanel panel, JFrame frame) 
    {
        doIt(frame, panel);   
    }

    public void update(Observable o, Object arg) 
    {
        pw.setSemaphor(false);
        System.out.println("set pw semaphor to false");
    }
    
}

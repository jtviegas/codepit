/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.concurrent.ScheduledFuture;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import snippetlab.interfaces.SnippetInterface;


import snippetlab.snippets.misc.menuFlasherAction.ActionBlinker;
import snippetlab.snippets.misc.menuFlasherAction.ActionBlinkerCallable;
import snippetlab.snippets.misc.menuFlasherAction.ExecutionServicesManager;
import snippetlab.snippets.misc.menuFlasherAction.FlashableAction;

/**
 *
 * @author jtviegas
 */
public class MenuFlasherAction implements SnippetInterface
{
    private FlashableAction f_action;
    
    public void go(JPanel panel, JFrame frame) 
    {
        
        JToolBar tb = new JToolBar();

        JMenuBar menubar=new JMenuBar();
        JMenu menu=new JMenu("menu");
        menubar.add(menu);
        f_action = new FlashableAction();
        
        JMenuItem item=new JMenuItem(f_action);
        tb.add(f_action);
        menu.add(item);
        
        frame.setJMenuBar(menubar);
        menubar.add(tb);
        
        panel.add(new JButton(new Blinker()), BorderLayout.EAST);
        panel.add(new JButton(new Unblinker()), BorderLayout.WEST);
        
    }
    
    private class Blinker extends AbstractAction
    {
        
        public Blinker()
        {
            super("blink");
        }
        
        public void actionPerformed(ActionEvent e) 
        {
            if(ExecutionServicesManager.getInstance().isRunningPeriodically(
                    ActionBlinker.createBlinkerThreadName(f_action)))
                return;

                
                ExecutionServicesManager.getInstance().schedulePeriodicRun(
                        new ActionBlinkerCallable(f_action),
                        ActionBlinker.createBlinkerThreadName(f_action),200,200);       
            

                
        }

  
        
		
        
    }
        
    private class Unblinker extends AbstractAction
    {
        private String taskname = null;
        
        public Unblinker()
        {
            super("unblink");
        }
        public void actionPerformed(ActionEvent e) 
        {
            if(!ExecutionServicesManager.getInstance().isRunningPeriodically(
                    ActionBlinker.createBlinkerThreadName(f_action)))
                return;

                ScheduledFuture<?> future = 
                        ExecutionServicesManager.getInstance().unschedulePeriodicRun(
                        ActionBlinker.createBlinkerThreadName(f_action));
                
                try
                {
//                    if(!future.isDone())
//                        wait();
                        
                    f_action.putValue(Action.SMALL_ICON, (Icon)f_action.getValue("ORIGINAL_ICON"));
                    
                }
                catch(Exception x)
                {
                    x.printStackTrace();
                }
            
        }
        
    }
    
}

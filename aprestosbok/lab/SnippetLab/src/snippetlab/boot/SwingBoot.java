/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.boot;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.aprestos.code.bok.logger.LoggerFactory;

import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.swingworkerdigressions.GuiGlassPane;

/**
 *
 * @author jtviegas
 */
public class SwingBoot
{
	private JFrame frame;
	private JPanel panel;
	private final String DEFAULT_SNIPPET_CLASS_PROPERTY = "snippetlab.snippets.class";
	/**
	 * @param args
	 */
	
	
	public SwingBoot()throws Exception
	{
	    	InputStream is = SwingBoot.class.getResourceAsStream("snippetlab.properties");
	    	Properties p = new Properties();
	    	p.load(is);
	    	
		LoggerFactory.initialize(p);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			panel = new JPanel();

			panel.setLayout(new BorderLayout());
			

			// find descriptor based on system property
			{
				String className = System.getProperty(
						DEFAULT_SNIPPET_CLASS_PROPERTY);
				Class<?> builderClass = SwingBoot.class.getClassLoader()
						.loadClass(className);
				Constructor<?> builderConstructor = builderClass
						.getConstructor(new Class[] {});
				SnippetInterface snippet = (SnippetInterface) builderConstructor
						.newInstance(new Object[] {});
				snippet.go(panel,frame);
			}

			frame.getContentPane().add(panel);
            frame.setGlassPane(new GuiGlassPane());
            
                        SwingUtilities.invokeLater(new Runnable(){
                            public void run() 
                            {
                                frame.pack();
                                frame.setSize(800, 600);
                                frame.setVisible(true);
                            }
                        });
			
			
	}
}

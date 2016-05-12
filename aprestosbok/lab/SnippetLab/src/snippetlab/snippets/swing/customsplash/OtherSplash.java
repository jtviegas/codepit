/*
 * OtherSplash.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.customsplash;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class OtherSplash
{

	 static JWindow splash = new JWindow(); 
	 
	  static void load(ImageIcon image) {  

		  int width = image.getIconWidth();  
		  int height = image.getIconHeight();  
		  JLabel label = new JLabel(image);  
//		  label.setForeground(Color.white);  
//		  splash.getContentPane().setBackground(Color.red);  
		  splash.getContentPane().add(label);  
		  splash.setSize(width, height);  
		  splash.setLocationRelativeTo(null);  
		  splash.setAlwaysOnTop(true);  
		  splash.setVisible(true);  
		  }
	  
	    static void unload() 
	    {  
		    splash.dispose();  
		}
}

/*
 * SplashScreen.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.customsplash;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 * 
 */
public class CustomSplashScreen extends JWindow
{
	private static final long serialVersionUID = 1L;

	private static CustomSplashScreen instance;

	public static void initialize(ImageIcon image)
	{
		instance = new CustomSplashScreen(image);
	}

	public static CustomSplashScreen getInstance()
	{
		return instance;
	}

	public void load()
	{
		setScreenVisible(true);
	}
	
	public void unload()
	{
		setScreenVisible(false);
	}
	
	private CustomSplashScreen(ImageIcon imageIcon)
	{
		this.getContentPane().setLayout(new FlowLayout());
		JLabel label = new JLabel(imageIcon);
//		label.setBorder(null);
		this.getContentPane().add(label);

		this.pack();
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
	}

	
	private void setScreenVisible(boolean b)
	{
		final boolean boo = b;
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				setVisible(boo);
			}
		});
	}

}

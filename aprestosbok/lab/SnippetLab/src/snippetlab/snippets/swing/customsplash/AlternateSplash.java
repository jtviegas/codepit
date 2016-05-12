/*
 * ShadowedSplash.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.customsplash;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JWindow;

public class AlternateSplash extends JWindow
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage splash = null;
	private static AlternateSplash instance;
	
	public static void initialize(ImageIcon image)
	{
		instance = new AlternateSplash(image);
	}
		
	public static AlternateSplash getInstance()
	{
		return instance;
	}
	
    public AlternateSplash(ImageIcon image) 
    {
    	int width = image.getIconWidth();
        int height = image.getIconHeight();

 
        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);
 
        splash = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) splash.getGraphics();

        g2.drawImage(image.getImage(), 0, 0, this);
    }
 
    public void paint(Graphics g) 
    {
        if (splash != null) {
            g.drawImage(splash, 0, 0, null);
        }
    }
    
    public void load()
    {
    	setVisible(true);
    }
    
    public void unload()
    {
    	setVisible(false);
    }

}

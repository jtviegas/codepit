/*
 * CustomSplashScreen.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.customsplash;

import javax.swing.ImageIcon;

import snippetlab.snippets.AbstractSnippet;

public class CustomSplashDigression extends AbstractSnippet
{
	private int i = 0;
	
	private ImageIcon image;
	
	public CustomSplashDigression()
	{
		//AlternateSplash.initialize(new ImageIcon("png/splash.jpg"));
		image =  new ImageIcon("png/splash.jpg");
	}

	@Override
	public void method()
	{
		i++;
		if( i % 2 > 0)
		{
			OtherSplash.load(image);
		}
		else
		{
			OtherSplash.unload();
		}

	}

	
	
}

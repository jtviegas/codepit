/*
 * AnimatedCursor.java
 */
/**
 * 
 */
package org.aprestos.code.bok.gui.misc;

import java.awt.Component;
import java.awt.Cursor;

/**
 * 
 */
public class AnimatedCursor
{
	private static final int DEFAULT_FRAME_DELAY=100;
	private int frameDelay;
	
	private boolean animating; 
	private Cursor[] cursors;
	private Component component;
	
	
	public AnimatedCursor(Component component, Cursor[] cursors) 
	{
		animating = false;
		this.component = component;
		this.cursors = cursors;
	}
	
	public AnimatedCursor(Component component, Cursor[] cursors, int frameDelay) 
	{
		this(component,cursors);
		this.frameDelay = frameDelay;
	}
	
	
	
	private int getFrameDelay()
	{
		int result = DEFAULT_FRAME_DELAY;
		
		if(frameDelay > 0)
			result = frameDelay;
		
		return result;
	}
	
	public void animate() 
	{
		if(animating) 
		{
			animating = false;
		} 
		else 
		{
			animating = true;
			Runnable runnable = new Runnable()
			{	@Override
				public void run() 
				{
					int count = 0;
					while(animating) 
					{
						try 
						{
							Thread.sleep(getFrameDelay());
						} 
						catch (Exception ex) { }
						component.setCursor(cursors[count % cursors.length]);
						count++;
					}
					component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			};
			new Thread(runnable).start();
		}
		
	}

}

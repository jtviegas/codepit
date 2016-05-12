package snippetlab.snippets.concurrency.jthreads2ed.one;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;

public class OurApplet extends Applet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image[] images = new Image[10];
	private Image[] images2 = new Image[10];
	
	private int index = 0;
	private ViewUpdater viewUpdater;

	public OurApplet() throws HeadlessException
	{
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init()
	{
		System.out.println("   @OurApplet.init");
		ImageLoader _loader = new ImageLoader(this.images,this);
		ImageLoader _loader2 = new ImageLoader(this.images2,this);
		viewUpdater = new ViewUpdater(this);
		
		_loader.start();
		_loader2.start();
		
		
		
		try
		{
			_loader.join();
			_loader2.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		viewUpdater.start();
		
		System.out.println("   OurApplet.init@");
	}
	
	public void paint(Graphics g)
	{
		if(null == this.images[index])
		{
			 g.setColor(Color.white);
		     g.fillRect(0,0, 200, 200);
		     g.setColor(Color.black);
		     g.drawString ("loading...",20,20);
		}
		else
		{
			if(0 == index % 2)
				g.drawImage(this.images[index++], 0, 0, this);
			else
				g.drawImage(this.images2[index++], 0, 0, this);
			
			if(index == this.images.length)
				index=0;
		}
		
		
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void stop()
	{
		viewUpdater.interrupt();
	}
	
	
	
	
	

}

package snippetlab.snippets.concurrency.jthreads2ed.one;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

public class ImageLoader extends Thread
{
	private Image[] images;
	private Component component;
	
	public ImageLoader(Image[] _images, Component _comp)
	{
		this.images = _images;
		this.component = _comp;
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		MediaTracker tracker = new MediaTracker(this.component);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		
		for(int _i=0 ; _i < this.images.length ; _i++)
		{
			
			this.images[_i] = toolkit.getImage("files/" + new Integer(_i).toString()+".jpg");
			tracker.addImage(this.images[_i],_i);
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
			tracker.waitForAll();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}

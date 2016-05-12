package snippetlab.snippets.concurrency.jthreads2ed.two;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

public class Animate extends Applet
{
	
	/**
	 * snippetlab.snippets.concurrency.jthreads2ed.two.Animate
	 */
	private static final long serialVersionUID = 1L;
	int count, lastcount;
	Image pictures[];
	TimerThread timer;
	
	public Animate() throws HeadlessException
	{
		// TODO Auto-generated constructor stub
	}
	
	public void init()
	{
		System.out.println(Thread.currentThread().getId() + " | " + "   @Animate.init");
		lastcount=10;
		count=0;
		
		try
		{
			pictures=new Image[10];
			MediaTracker tracker = new MediaTracker(this);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			for(int a=0; a<lastcount;a++)
			{
				
				pictures[a] = toolkit.getImage("files/" + new Integer(a).toString()+".jpg");
				//pictures[a] = getImage(new URL("./files"), new Integer(a).toString()+".jpg");
				tracker.addImage(pictures[a],a);
			}
			tracker.checkAll();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getId() + " | " + "   Animate.init@");
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#start()
	 */
	@Override
	public void start()
	{
		timer = new TimerThread(this,1000);
		timer.start();
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void stop()
	{
		timer.shouldRun=false;
		timer=null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		System.out.println(Thread.currentThread().getId() + " | " + "   @Animate.paint");
		g.drawImage(pictures[count++], 0, 0, null);
		
		if(count==lastcount)
			count=0;
		
		System.out.println(Thread.currentThread().getId() + " | " + "   Animate.paint@");
	}
	
	

}

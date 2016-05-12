package snippetlab.snippets.concurrency.jthreads2ed.one;

import java.awt.Component;

public class ViewUpdater extends Thread
{
	private Component component;
	
	public ViewUpdater(Component _comp)
	{
		this.component = _comp;
	}


	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{

		 for (;;)
         {
             // Repaint the images
             this.component.repaint();

             try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }

	}

}

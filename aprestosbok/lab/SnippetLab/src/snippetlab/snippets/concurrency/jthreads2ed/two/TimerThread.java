package snippetlab.snippets.concurrency.jthreads2ed.two;

import java.awt.Component;

public class TimerThread extends Thread
{

	Component comp;
	int timediff;
	volatile boolean shouldRun;
	
	public TimerThread(Component _comp, int _timediff)
	{
		this.comp=_comp;
		this.timediff = _timediff;
		this.shouldRun = true;
	}
	
	public TimerThread()
	{
		// TODO Auto-generated constructor stub
	}

	public TimerThread(Runnable target)
	{
		super(target);
		// TODO Auto-generated constructor stub
	}

	public TimerThread(String name)
	{
		super(name);
		// TODO Auto-generated constructor stub
	}

	public TimerThread(ThreadGroup group, Runnable target)
	{
		super(group, target);
		// TODO Auto-generated constructor stub
	}

	public TimerThread(ThreadGroup group, String name)
	{
		super(group, name);
		// TODO Auto-generated constructor stub
	}

	public TimerThread(Runnable target, String name)
	{
		super(target, name);
		// TODO Auto-generated constructor stub
	}

	public TimerThread(ThreadGroup group, Runnable target, String name)
	{
		super(group, target, name);
		// TODO Auto-generated constructor stub
	}

	public TimerThread(ThreadGroup group, Runnable target, String name,
			long stackSize)
	{
		super(group, target, name, stackSize);
		// TODO Auto-generated constructor stub
	}

	public void run()
	{
		System.out.println(Thread.currentThread().getId() + " | " + "   @TimerThread.run");
		while(shouldRun)
		{
			try
			{
				System.out.println("...repainting...");
				comp.repaint();
				sleep(timediff);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getId() + " | " + "   TimerThread.run@");
	}
	
}

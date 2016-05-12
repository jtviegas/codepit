package snippetlab.snippets.concurrency;

import java.io.IOException;
import java.util.Arrays;

import snippetlab.snippets.AbstractSnippet;

public class Concur extends AbstractSnippet
{

	@Override
	public void method()
	{
		Thread _t =new Thread(new ThreadRunner2());
		_t.setDaemon(true);
		_t.start();

	}

	
	
	

}

class ThreadRunner2 implements Runnable
{
	@Override
	public void run()
	{
		Thread _first = new Worker(new int[]{1,3,5,7}, "odds");
		Thread _second = new Worker(new int[]{2,4,6,8}, "evens");

		_first.start();
		_second.start();
		
		System.out.println("press enter when you've had enough of it...");
		try
		{
			System.in.read();
			System.out.println("enter pressed");
			
			//interrupt the threads
			_first.interrupt();
			_second.interrupt();

		} 
		catch (IOException e)
		{
			System.out.println(e);
		}
	
		
	}
}


class ThreadRunner implements Runnable
{
	@Override
	public void run()
	{
		Thread _first = new TryThread("ONE", "one!", 1000);
		Thread _second = new TryThread("TWO", "two!", 1000);
		Thread _third = new TryThread("THREE", "three!", 1000);


		System.out.println("press enter when you've had enough of it...");

		_first.start();
		_second.start();
		_third.start();

		try
		{
			System.in.read();
			System.out.println("enter pressed");
			
			//interrupt the threads
			_first.interrupt();
			_second.interrupt();
			_third.interrupt();
		} 
		catch (IOException e)
		{
			System.out.println(e);
		}
		
	}
}

class TryThread extends Thread
{
	private String fname, sname;
	private long aWhile;

	public TryThread(String _fname, String _sname, long _delay)
	{
		this.fname = _fname;
		this.sname = _sname;
		this.aWhile = _delay;
		this.setDaemon(true);
	}

	public void run()
	{
		try
		{
			while (true)
			{
				System.out.println(fname);
				sleep(aWhile);
				System.out.println(sname);
			}

		} 
		catch (InterruptedException e)
		{
			System.out.println(fname + "-" + sname + ":" + e.getMessage());
		}
	}
	
	
}




class LocalSingleton
{
	private static LocalSingleton singleton = null;
	
	private int currentStep;
	
	private LocalSingleton()
	{
		currentStep++;
	}
	
	public static LocalSingleton getInstance()
	{
		if(null == singleton)
			singleton = new LocalSingleton();
		
		return singleton;
	}
	
	public synchronized int getStep()
	{
		return this.currentStep;
	}
	
	public synchronized void setStep(int _step)
	{
		this.currentStep=_step;
	}
}

class Worker extends Thread
{
	private int[] steps;
	private String name;

	public Worker( int[] _steps, String _name)
	{
		this.steps = _steps;
		this.name = _name;
	}

	private void doit()
	{
		System.out.println("[" + this.name + "] worker starting step " + LocalSingleton.getInstance().getStep());
		System.out.println("[" + this.name + "] worker finishing step " + LocalSingleton.getInstance().getStep());
		LocalSingleton.getInstance().setStep(LocalSingleton.getInstance().getStep() + 1);
		
	}
	
	public synchronized void run()
	{
		try
		{
			while(true)
			{

				System.out.println("[" + this.name + "] current step: " + LocalSingleton.getInstance().getStep());
				
				if(0 > Arrays.binarySearch(this.steps,LocalSingleton.getInstance().getStep()))
				{
					System.out.println("[" + this.name + "] i don't have this step");
					wait();
				}
				else
				{
					System.out.println("[" + this.name + "] i have this step");
					doit();
					notifyAll();
				}
			}
		} 
		catch (InterruptedException e)
		{
			System.out.println("[" + this.name + "] step - " + LocalSingleton.getInstance().getStep() + ":" + e.getMessage());
		}
	}
	
	
}


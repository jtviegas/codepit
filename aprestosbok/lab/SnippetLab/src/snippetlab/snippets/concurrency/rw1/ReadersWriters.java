package snippetlab.snippets.concurrency.rw1;

public class ReadersWriters
{
	private int readers = 0;
	private int waitingWriters = 0;
	private boolean writing = false;
	
	public ReadersWriters()
	{
		
	}
	
	public synchronized void startWrite() throws InterruptedException
	{
		
		//Wait until is ok to write
		while(readers > 0 || writing)
		{
			waitingWriters++;
			wait();
			waitingWriters--;
		}
		
		writing = true;
		
	}
	
	public synchronized void stopWrite()
	{
		writing = false;
		notifyAll();
	}

	public synchronized void startRead() throws InterruptedException
	{
		//wait until is ok to read
		while(writing || (waitingWriters > 0))
			wait();
		
		readers++;
	}
	
	public synchronized void stopRead()
	{
		readers--;
		if(readers == 0) notifyAll();
	}
}

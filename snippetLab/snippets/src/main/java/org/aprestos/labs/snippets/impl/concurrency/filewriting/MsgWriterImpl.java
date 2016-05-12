package org.aprestos.labs.snippets.impl.concurrency.filewriting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MsgWriterImpl implements MsgWriter {

	private FileWriter writer;
	private static String filenamePattern = "/tmp/snippets.impl.concurrency.filewriting.MsgWriter_%s";
	private static String tempSuffix = ".tmp", finalSuffix = ".csv";
	private String filename;
	private final ScheduledExecutorService scheduler;
	private static int NUM_THREADS = 1;
	private static int initialDelay = 1, delay = 10;
	private static final String logMsgPattern = "[Time:%s;Thread:%s] %s";
	
	
	public MsgWriterImpl() { 
		scheduler = Executors.newScheduledThreadPool(NUM_THREADS);
		ScheduledFuture<?> resetFileFuture = scheduler.scheduleWithFixedDelay(
			      new ResetFileTask(), initialDelay, delay, TimeUnit.SECONDS
			    );
		Runtime.getRuntime().addShutdownHook(new Thread(new CloseFileTask()));
		
	}
	
	private void log (String msg){
		
		System.out.println(String.format(logMsgPattern, Long.toString(new Date().getTime()), Thread.currentThread().getId(), msg));
		
	}
	
	/* (non-Javadoc)
	 * @see org.aprestos.labs.snippets.impl.concurrency.filewriting.MsgWriter#write(java.lang.String)
	 */
	public synchronized void write (String msg) throws IOException{
		
		log("@writing");
		
		if(null == writer)
			resetFile();
		
		writer.write(msg);
		writer.flush();
		
		log("writing@");
	}
	
	private synchronized void renameFile() throws IOException{
		
		
			
			writer.close();
			
			File tmpFile = new File(this.filename + tempSuffix);
			File csvFile = new File(this.filename + finalSuffix);

			if (csvFile.exists()) {
				csvFile.delete();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}

			tmpFile.renameTo(csvFile);
		
		
	}
	
	protected synchronized void resetFile() throws IOException{
		
		log("@resetFile");
		
		if(null != writer)
			renameFile();
			
		this.filename = String.format(filenamePattern, Long.toString((new Date().getTime())));
		writer = new FileWriter(this.filename + tempSuffix);
		
		log("@resetFile");
	}
	
	private final class ResetFileTask implements Runnable {
	    public void run() {
	    	
	    	try {
	    		
				resetFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }

	  }
	
	private final class CloseFileTask implements Runnable {
	    public void run() {
	    	
	    	try {
	    		try {
	    			scheduler.shutdown();
					scheduler.awaitTermination(30, TimeUnit.SECONDS);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		renameFile();
	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }

	  }
	
	
}

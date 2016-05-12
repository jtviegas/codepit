package org.aprestos.labs.snippets.impl.io.messages;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class FileProvider {
	
	//using linked list for all entries are added to the end of the list
	private List<IntervalWriterImpl> writers = new LinkedList<IntervalWriterImpl>();
	private final ScheduledExecutorService scheduler;
	private ScheduledFuture<?> closeFilesFutureTask;
	
	private String outFolderPath;
	private int intervalInMillis;
	private int intervalTolerance;
	private String fileHeader;
	private String filenamePattern;
	
	
	private static int NUM_THREADS = 1;
	private static final String TMP_SUFFIX=".tmp", FINAL_SUFFIX=".csv";
	
	public FileProvider(String outFolderPath, int intervalInMillis, int intervalTolerance, 
			String fileHeader, String filenamePattern){
		this.outFolderPath = outFolderPath;
		this.intervalInMillis = intervalInMillis;
		this.intervalTolerance = intervalTolerance;
		this.fileHeader = fileHeader;
		this.filenamePattern = filenamePattern;
		
		scheduler = Executors.newScheduledThreadPool(NUM_THREADS);
	}
	
	
	public void setOutFolderPath(String outFolderPath) {
		this.outFolderPath = outFolderPath;
	}

	public void setInterval(int interval) {
		this.intervalInMillis = interval;
	}
	
	public void closeWriters() throws IOException {
		for(int i = writers.size()-1 ; i >= 0 ; i--)
			closeWriter(i);
		
	}
	
	public synchronized void closeWriter(int index) {
		
		IntervalWriterImpl intWriter = writers.remove(index);
		
		try {
			intWriter.writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			intWriter.writer = null;
			renameFile(intWriter.fileName);
			intWriter = null;
		}
		
	}
	
	private void renameFile(String filename){
		
		File tmpFile = new File(filename);
		File finalFile = new File(filename.substring(0, filename.length()-4) + FINAL_SUFFIX);

		if(tmpFile.exists()){
			tmpFile.renameTo(finalFile);
			/*if(result)
				setupFilePermissions(Constants.OUT_PATH_URI_SCHEME, csvFile);	*/
		}
	}
	
	public synchronized FileWriter getWriter(long timestamp) throws IOException{
		
		FileWriter result = null;
		IntervalWriterImpl iw = null;
		
		//search from the end of the list, which is the newest one
		boolean isLate = false;
		for(int i = writers.size()-1 ; i >= 0 ; i--){
			
			iw = writers.get(i);
			if(iw.startTimestamp > timestamp){
				//this will always happen on the first iterations
				//for it's the newest interval, so once is late, is late
				isLate = true;
				continue;
			}
			else if(iw.startTimestamp + intervalInMillis > timestamp){
				result = iw.writer;
				break;
			}
		}
		
		if(null == result){
			System.out.println("no writer for ts->" + timestamp);
			if(isLate){
				//too late -> discard
				return null;
			}
			
			//create new
			result = createWriter(timestamp);

		}
		
		return result;
	}
	
	protected void recoverOnRestart() {

		String fileName = null;
		long timestamp = -1;
		// when server restarted, there could be lost TMP files
		try {
			File files[] = new File(this.outFolderPath).listFiles(new FilenameFilter(){
				
				public boolean accept(File dir, String name) {
					if(name.endsWith(TMP_SUFFIX))					
						return true;
					else
						return false;
				}
			});
			for (File f : files) {
				fileName = f.getAbsolutePath();
				timestamp = Long.parseLong(fileName.substring(fileName.length()-13), fileName.length() - 4);
				writers.add(new IntervalWriterImpl(fileName, timestamp, new FileWriter(fileName, true)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	protected synchronized FileWriter createWriter(long timestamp) throws IOException {

		boolean shouldWriteHeader = false;
		FileWriter result = null;
		String outFileName = outFolderPath + "/" + this.filenamePattern + timestamp + TMP_SUFFIX;

		File file = new File(outFileName);
		if(!file.exists())
			shouldWriteHeader = true;

		result = new FileWriter(file, true);
		writers.add(new IntervalWriterImpl(outFileName,timestamp, result));
		
		if(shouldWriteHeader)
			result.write(this.fileHeader);
		
		if (null == closeFilesFutureTask)
			setupTasks();

		return result;
	}

	private void setupTasks() {
		
		closeFilesFutureTask = scheduler.scheduleWithFixedDelay(new CloseFilesTask(), 
				this.intervalTolerance, this.intervalInMillis, TimeUnit.MILLISECONDS);
		Runtime.getRuntime().addShutdownHook(new Thread(new CloseTask()));
	}
	
	private final class CloseFilesTask implements Runnable {
		
		public void run() {
			try {
				/*
				 * we are going to close files when its startTimestamp was
				 * more than fileLifeSpanInMillis ago
				 */
				List<Integer> oldWritersIndex = new ArrayList<Integer>();
				long checkpointTimestamp = System.currentTimeMillis();
				
				for(int i=0; i < writers.size() ; i++){
					long writerAgeLimit = writers.get(i).startTimestamp + intervalTolerance + intervalInMillis;
					if( checkpointTimestamp > writerAgeLimit ) 
						oldWritersIndex.add(i);
				}
				
				for(int index:oldWritersIndex){
					closeWriter(index);
					System.out.println("closing file at " + checkpointTimestamp);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	
	private final class CloseTask implements Runnable {
		public void run() {
			stop();
		}

	}
	
	public void stop() {

		try {
			try {
				scheduler.shutdown();
				scheduler.awaitTermination(10, TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			closeWriters();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

 	private class IntervalWriterImpl {
		
		IntervalWriterImpl(String fileName, long startTimestamp, FileWriter writer){
			this.fileName=fileName;
			this.startTimestamp=startTimestamp;
			this.writer=writer;

		}
		
		String fileName;
		long startTimestamp;
		FileWriter writer;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((fileName == null) ? 0 : fileName.hashCode());
			result = prime * result
					+ (int) (startTimestamp ^ (startTimestamp >>> 32));
			result = prime * result
					+ ((writer == null) ? 0 : writer.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			IntervalWriterImpl other = (IntervalWriterImpl) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (fileName == null) {
				if (other.fileName != null)
					return false;
			} else if (!fileName.equals(other.fileName))
				return false;
			if (startTimestamp != other.startTimestamp)
				return false;
			if (writer == null) {
				if (other.writer != null)
					return false;
			} else if (!writer.equals(other.writer))
				return false;
			return true;
		}
		private FileProvider getOuterType() {
			return FileProvider.this;
		}

		
		
	}
	
}

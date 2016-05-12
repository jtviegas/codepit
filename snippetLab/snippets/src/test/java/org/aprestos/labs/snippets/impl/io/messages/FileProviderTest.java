package org.aprestos.labs.snippets.impl.io.messages;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileProviderTest {

	private static final String OUT_FOLDER = "/tmp/fileprovidertest";

	private static final String FILE_HEADER = "#HEADER ";
	private static final String FILENAME_PATTERN = "fileprovidertest_";
	private static final String MESSAGE = System.getProperty("line.separator")
			+ "...";

	// send message every second
	private static final int MESSAGE_CREATION_INTERVAL = 1000;
	private static final int INTERVAL = 1 * 15 * 1000;
	private static final int INTERVAL_FILE_LIFE_TOLERANCE = INTERVAL/5;
	private static final int FILE_LIFE_SPAN = INTERVAL + INTERVAL_FILE_LIFE_TOLERANCE;

	private static final int BEFORE_INTERVAL_FILE_EOL = INTERVAL + (INTERVAL_FILE_LIFE_TOLERANCE/2);
	private static final int AFTER_INTERVAL_FILE_EOL = FILE_LIFE_SPAN + 3*(INTERVAL_FILE_LIFE_TOLERANCE);

	private FileProvider o;
	private int expected, actual;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@Before
	public void setUp() throws Exception {
		cleanOutFolder();
		o = new FileProvider(OUT_FOLDER, INTERVAL, INTERVAL_FILE_LIFE_TOLERANCE, FILE_HEADER,
				FILENAME_PATTERN);
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * send messages during three intervals but when writing the second one send
	 * one late message before the 1st interval file end of life and other one
	 * message after the 1st interval file end of life.
	 * Then stop before the end of the 2nd interval and resume in the time
	 * belongin to the 3rd interval
	 * 
	 * @throws IOException
	 */
	@Test
	public void twoIntervalsOfDataWithLateMessageAndTooLateMessageAndPauseFromThe2ndToThe3rd()
			throws IOException {

		long startTime = getAlignedTimestamp(System.currentTimeMillis(),
				INTERVAL);
		int numOfIntervals = 3, messagesCreated = 0;
		long endTime = startTime + (numOfIntervals * INTERVAL);
		long time = startTime;
		FileWriter writer = null;
		long msgTs = startTime + 1000;
		boolean lateSubmitted = false;
		boolean tooLateMsgSubmitted = false;

		while (time < endTime) {
			
			o.getWriter(time).write(MESSAGE);
			messagesCreated++;
			
			try {
				Thread.sleep(MESSAGE_CREATION_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//send late message before the first interval file EOL
			if((!lateSubmitted) && time >= (startTime + BEFORE_INTERVAL_FILE_EOL)){
				o.getWriter(msgTs).write(MESSAGE);
				messagesCreated++;
				System.out.println("created late message at " + time);
				lateSubmitted = true;
			}
			
			//send too late message after the first interval file EOL
			if ((!tooLateMsgSubmitted) && time > (startTime + AFTER_INTERVAL_FILE_EOL)) {
				if(null != (writer = o.getWriter(msgTs))){
					writer.write(MESSAGE);
					messagesCreated++;
					System.out.println("created too late message at " + time);
				}
				else {
					System.out.println("no writer for too late message at " + time);
				}
				tooLateMsgSubmitted = true;
				
				//wait until the next interval
				try {
					Thread.sleep(INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}

			//get a message ts in the first interval
			time = System.currentTimeMillis();
		
		}

		try {
			Thread.sleep(2*FILE_LIFE_SPAN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// we should have one file
		expected = numOfIntervals;
		actual = getNumberOfCreatedFiles();
		Assert.assertEquals(expected, actual);

		// with a specific number of lines (don't forget the header)
		expected = messagesCreated + numOfIntervals;
		actual = getNumberOfLinesInFiles();
		Assert.assertEquals(expected, actual);

	}
	
	/**
	 * send messages during two intervals but when writing the second one send
	 * one late message before the 1st interval file end of life and other one
	 * message after the 1st interval file end of life
	 * 
	 * @throws IOException
	 */
	//@Test
	public void twoIntervalsOfDataWithLateMessageAndTooLateMessage()
			throws IOException {

		long startTime = getAlignedTimestamp(System.currentTimeMillis(),
				INTERVAL);
		int numOfIntervals = 2, messagesCreated = 0;
		long endTime = startTime + (numOfIntervals * INTERVAL);
		long time = startTime;
		FileWriter writer = null;
		long msgTs = startTime + 1000;
		boolean lateSubmitted = false;
		boolean tooLateMsgSubmitted = false;

		while (time < endTime) {
			
			o.getWriter(time).write(MESSAGE);
			messagesCreated++;
			
			try {
				Thread.sleep(MESSAGE_CREATION_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//send late message before the first interval file EOL
			if((!lateSubmitted) && time >= (startTime + BEFORE_INTERVAL_FILE_EOL)){
				o.getWriter(msgTs).write(MESSAGE);
				messagesCreated++;
				System.out.println("created late message at " + time);
				lateSubmitted = true;
			}
			
			//send too late message after the first interval file EOL
			if ((!tooLateMsgSubmitted) && time > (startTime + AFTER_INTERVAL_FILE_EOL)) {
				if(null != (writer = o.getWriter(msgTs))){
					writer.write(MESSAGE);
					messagesCreated++;
					System.out.println("created too late message at " + time);
				}
				else {
					System.out.println("no writer for too late message at " + time);
				}
				tooLateMsgSubmitted = true;
			}

			//get a message ts in the first interval
			time = System.currentTimeMillis();
		
		}

		try {
			Thread.sleep(2*FILE_LIFE_SPAN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// we should have one file
		expected = 2;
		actual = getNumberOfCreatedFiles();
		Assert.assertEquals(expected, actual);

		// with a specific number of lines (don't forget the header)
		expected = messagesCreated + 2;
		actual = getNumberOfLinesInFiles();
		Assert.assertEquals(expected, actual);

	}

	/**
	 * send messages during two intervals but when writing the second one send
	 * one late message before the 1st interval file end of life
	 * 
	 * @throws IOException
	 */
	//@Test
	public void twoIntervalsOfDataWithLateMessage() throws IOException {

		long startTime = getAlignedTimestamp(System.currentTimeMillis(),
				INTERVAL);
		int numOfIntervals = 2, messagesCreated = 0;
		long endTime = startTime + (numOfIntervals * INTERVAL);
		long time = startTime;

		long msgTs = 0;
		boolean lateSubmitted = false;


		while (time < endTime) {
			
			o.getWriter(time).write(MESSAGE);
			messagesCreated++;
			
			try {
				Thread.sleep(MESSAGE_CREATION_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//send late message before the first interval file EOL
			if((!lateSubmitted) && time >= (startTime + BEFORE_INTERVAL_FILE_EOL)){
				o.getWriter(msgTs).write(MESSAGE);
				messagesCreated++;
				lateSubmitted = true;
			}

			//get a message ts in the first interval
			time = System.currentTimeMillis();
			if(0 == msgTs){
				msgTs = time;
			}
			
		}

		try {
			Thread.sleep(FILE_LIFE_SPAN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// we should have one file
		expected = 2;
		actual = getNumberOfCreatedFiles();
		Assert.assertEquals(expected, actual);

		// with a specific number of lines (don't forget the header)
		expected = messagesCreated + 2;
		actual = getNumberOfLinesInFiles();
		Assert.assertEquals(expected, actual);


	}

	/**
	 * loop through one interval generating messages and wait for the file to be
	 * closed and check the number of files and number of lines in files
	 * 
	 * @throws IOException
	 */
	//@Test
	public void oneIntervalOfData() throws IOException {

		long startTime = getAlignedTimestamp(System.currentTimeMillis(),
				INTERVAL);
		int numOfIntervals = 1, messagesCreated = 0;
		long endTime = startTime + (numOfIntervals * INTERVAL);
		long time = startTime;
		
		while (time < endTime) {
			o.getWriter(time).write(MESSAGE);
			messagesCreated++;
			try {
				Thread.sleep(MESSAGE_CREATION_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time = System.currentTimeMillis();
		}
		try {
			Thread.sleep(FILE_LIFE_SPAN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// we should have one file
		expected = 1;
		actual = getNumberOfCreatedFiles();
		Assert.assertEquals(expected, actual);

		// with a specific number of lines (don't forget the header)
		expected = messagesCreated + 1;
		actual = getNumberOfLinesInFiles();
		Assert.assertEquals(expected, actual);
	}

	private static int getNumberOfCreatedFiles() {
		return new File(OUT_FOLDER).listFiles().length;
	}

	private static int getNumberOfLinesInFiles() throws IOException {
		int result = 0;
		LineNumberReader lnr = null;

		File[] files = new File(OUT_FOLDER).listFiles();
		for (File f : files) {
			lnr = new LineNumberReader(new FileReader(f));
			lnr.skip(Long.MAX_VALUE);
			result += lnr.getLineNumber() + 1;// Add 1 because line index starts
												// at 0
		}
		lnr.close();

		return result;
	}

	private static long getAlignedTimestamp(long timeToAlign, long interval) {
		if (interval == 0)
			return timeToAlign;

		long numOfIntervals = 0;

		if (timeToAlign % interval != 0)
			numOfIntervals = (long) Math.ceil(1.0 * timeToAlign / interval) - 1;
		else
			numOfIntervals = 1 + ((long) timeToAlign / interval);

		long aligned = interval * numOfIntervals;

		// corner case
		if (aligned < 0)
			return Long.MAX_VALUE;
		return aligned;
	}

	private void cleanOutFolder() {
		File folder = new File(OUT_FOLDER);
		if (folder.exists()) {
			File files[] = folder.listFiles();
			for (File f : files) {
				f.delete();
			}
			folder.delete();
		}
		folder.mkdirs();
	}
}

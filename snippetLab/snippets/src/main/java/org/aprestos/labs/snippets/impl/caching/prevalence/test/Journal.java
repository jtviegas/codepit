package org.aprestos.labs.snippets.impl.caching.prevalence.test;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Journal<T> implements Runnable {

	private static Logger logger = Logger.getLogger(Journal.class.getName());
	protected BlockingQueue<T> queue;
	protected File file;
	protected FileChannel channel;

	public Journal(File file, BlockingQueue<T> queue) {
		this.file = file;
		this.queue = queue;
	}

	public List<T> deserialize() throws ClassNotFoundException, IOException {
		logger.entering(this.getClass().getName(), "deserialize");

		List<T> result = getObjectsFromFile(file);

		logger.exiting(this.getClass().getName(), "deserialize", result);
		return result;
	}

	public void run() {
		logger.entering(this.getClass().getName(), "run");
		T object = null;
		try {

			try {
				channel = new RandomAccessFile(file, "rws").getChannel();
			} catch (Exception e1) {
				// if we can't setup channel correctly
				// then we can't do anything else here
				throw new BagWriterSetupException(e1);
			}
			logger.logp(Level.FINEST, this.getClass().getName(), "run", "waiting for an object from the queue");
			while (null != (object = queue.take())) {
				logger.logp(Level.FINEST, this.getClass().getName(), "run", "got one object from the queue");
				try {
					serializeObject(object);
					logger.logp(Level.FINEST, this.getClass().getName(), "run",
							"serialized the object to the journal file");
				} catch (Exception e) { // if an exception here, we can //
										// always
					// resume the loop (??? can we, really ???) and log the
					// error
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					logger.severe(String.format(
							"problem trying to write object:" + System.getProperty("line.separator") + " %s"
									+ System.getProperty("line.separator") + "%s", object.toString(), sw.toString()));
				}
			}
		} catch (BagWriterSetupException e) {
			// we don't want any more to save ops
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(String.format("couldn't setup writer channel to save entries in the journal", sw.toString()));
		} catch (InterruptedException e) {
			// we don't want any more to save ops
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(String.format("InterruptedException when waiting for entries from the queue", sw.toString()));
		} catch (Exception e) {
			// we don't want any more to save ops
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(String.format("when looping to save entries in journal", sw.toString()));
		} finally {
			if (null != channel) {
				logger.logp(Level.FINEST, this.getClass().getName(), "run", "going to close the file channel");
				try {
					channel.close();
				} catch (IOException e) {
					logger.severe(String.format("when trying to close the channel on the way out", e.getMessage()));
				}
				logger.logp(Level.FINEST, this.getClass().getName(), "run", "closed the file channel");
				channel = null;
			}
		}
		logger.exiting(this.getClass().getName(), "run");
	}

	protected void serializeObject(T obj) throws IOException {
		logger.entering(this.getClass().getName(), "serializeObject", obj);

		ByteBuffer bb = ByteBuffer.wrap(object2Bytes(obj));
		logger.logp(Level.FINEST, this.getClass().getName(), "run", "going to serialize the operation to the file");
		while (bb.hasRemaining())
			channel.write(bb);// write byte array to file

		logger.exiting(this.getClass().getName(), "serializeObject");
	}

	protected byte[] object2Bytes(T obj) throws IOException {
		logger.entering(this.getClass().getName(), "object2Bytes", obj);
		byte[] result = null;
		ByteArrayOutputStream bos = null;
		AppendableObjectOutputStream oos = null;

		if (null == bos)
			bos = new ByteArrayOutputStream();

		if (null == oos)
			oos = new AppendableObjectOutputStream(bos);

		// write object to stream
		oos.writeObject(obj);
		oos.flush();

		result = bos.toByteArray();
		logger.exiting(this.getClass().getName(), "object2Bytes", result);
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List<T> getObjectsFromFile(File file) {
		logger.entering(this.getClass().getName(), "getObjectsFromFile", file);
		List<T> result = new ArrayList<T>();
		Object o = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			try {
				while (null != (o = ois.readObject()))
					result.add((T) o);
			} catch (EOFException e) {
				logger.info("ended reading objects from file");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != ois)
				try {
					ois.close();
				} catch (IOException e) {
				}
			if (null != fis)
				try {
					fis.close();
				} catch (IOException e) {
				}
		}

		logger.exiting(this.getClass().getName(), "getObjectsFromFile", result);
		return result;
	}

	public UncaughtExceptionHandler createUncaughtExceptionHandler() {
		return new UncaughtExceptionHandler() {

			public void uncaughtException(Thread thread, Throwable throwable) {
				logger.entering(this.getClass().getName(), "uncaughtException", new Object[] { thread, throwable });
				StringWriter sw = new StringWriter();
				throwable.printStackTrace(new PrintWriter(sw));

				if (null != channel)
					try {
						channel.close();
					} catch (IOException e) {
						logger.severe(String.format("when trying to close the channel on the way out", e.getMessage()));
					}
				channel = null;
				logger.severe(String.format("Journal uncaughtException", sw.toString()));
				logger.exiting(this.getClass().getName(), "uncaughtException");
			}

		};
	}

}



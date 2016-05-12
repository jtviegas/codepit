package org.aprestos.labs.snippets.impl.caching.prevalence.test;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * You have an object that is encapsulated in a type of bag on which you apply
 * commands. That bag then, persists/serializes each of those commands and
 * executes it. Some transaction feature must be in place here for the
 * persisting/serializing of the commands to match the state of the bag. Here we
 * might submit threaded commands to an executor service with a limited tread
 * pool and then log any exception that might occur.
 * 
 * This same bag, when initialized with a folder parameter, will load a set of
 * commands from journal files representing the objects that have been
 * persisted/serialized previously.
 * 
 * @author joaovieg
 *
 */
public class Bag<T, E> {

	private static Logger logger = Logger.getLogger(Bag.class.getName());
	private static final int DEFAULT_THREAD_POOL_SIZE = 6;
	private static final int QUEUE_SIZE = 100;
	private static final String JOURNAL_FILE_NAME_SUFFIX = ".journal";
	// <path>/<classSimpleName>-<time milliseconds>.journal
	private static final String JOURNAL_FILE_NAME_PATTERN = "%s" + System.getProperty("file.separator")
			+ Bag.class.getSimpleName() + "-%s" + JOURNAL_FILE_NAME_SUFFIX;
	private static final String JOURNAL_FILE_NAME_REGEX_PATTERN = "(\\w-)(\\d+)(" + JOURNAL_FILE_NAME_SUFFIX + ")";

	private int threadPoolSize;
	private String folder;
	private T object;

	private ExecutorService executor;

	protected BlockingQueue<Operation<T, E>> queue = new ArrayBlockingQueue<Operation<T, E>>(QUEUE_SIZE, true);

	private Bag() {
		logger.setLevel(Level.FINEST);
	}

	public Bag(T o) {
		this();
		logger.entering(this.getClass().getName(), "Bag", o);
		init(o, DEFAULT_THREAD_POOL_SIZE);
		logger.exiting(this.getClass().getName(), "Bag");
	}

	public Bag(T o, String folder) throws BagException {
		this();
		logger.entering(this.getClass().getName(), "Bag", new Object[] { o, folder });
		init(o, folder, DEFAULT_THREAD_POOL_SIZE);
		logger.exiting(this.getClass().getName(), "Bag");
	}

	public Bag(T o, String folder, int threadPoolSize) throws BagException {
		this();
		logger.entering(this.getClass().getName(), "Bag", new Object[] { o, folder, threadPoolSize });
		init(o, folder, threadPoolSize);
		logger.exiting(this.getClass().getName(), "Bag");
	}

	public void init(T o, int threadPoolSize) {
		logger.entering(this.getClass().getName(), "init", new Object[] { o, threadPoolSize });
		this.object = o;
		this.threadPoolSize = threadPoolSize;
		logger.exiting(this.getClass().getName(), "init");
	}

	private void init(T o, String folder, int threadPoolSize) throws BagException {
		logger.entering(this.getClass().getName(), "init", new Object[] { o, folder, threadPoolSize });
		this.object = o;
		this.threadPoolSize = threadPoolSize;
		this.folder = folder;
		File file = null;
		boolean mustDeserialize = false;
		Journal<Operation<T, E>> journal = null;

		// we've been asked to create a journal file
		File path = new File(folder);

		if (!path.exists())
			path.mkdirs();

		if (null != (file = getLatestJournal(path)))
			mustDeserialize = true;
		else
			file = new File(String.format(JOURNAL_FILE_NAME_PATTERN, path, Long.toString(System.currentTimeMillis())));

		journal = new Journal<Operation<T, E>>(file, queue);
		if (mustDeserialize) {
			try {
				List<Operation<T, E>> list = journal.deserialize();
				for (Operation<T, E> obj : list)
					apply(obj, true);
			} catch (Exception e) {
				throw new DeserializeException("problem when deserializig the objects in the latest journal", e);
			}
		}

		Thread journalThread = new Thread(journal);
		journalThread.setUncaughtExceptionHandler(journal.createUncaughtExceptionHandler());
		getExecutor().submit(journalThread);

		logger.exiting(this.getClass().getName(), "init");
	}

	public void stop() {
		logger.entering(this.getClass().getName(), "stop");
		if (null != executor) {
			executor.shutdownNow();
			// wait for any resource to be closed by interrupted exception
			// handlers
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			}
		}
		logger.exiting(this.getClass().getName(), "stop");
	}

	public void apply(final Operation<T, E> operation) {
		apply(operation, false);
	}

	protected void apply(final Operation<T, E> operation, boolean isRestoring) {
		logger.entering(this.getClass().getName(), "apply", operation);

		operation.setObject(this.object);
		final Future<E> future = getExecutor().submit(operation);
		logger.logp(Level.FINEST, this.getClass().getName(), "apply", "submitted operation to executor");
		// if we have a folder, then lets serialize the operation
		if ((!isRestoring) && (null != folder)) {
			getExecutor().submit(new Runnable() {
				public void run() {
					logger.entering(this.getClass().getName(), "run");
					boolean executed = true;
					try {
						// wait for the operation to finish
						future.get();
						logger.logp(Level.FINEST, this.getClass().getName(), "run", "operation executed");
					} catch (Exception e) {
						// something was wrong with the operation
						// we won't serialize it
						executed = false;
						StringWriter sw = new StringWriter();
						e.printStackTrace(new PrintWriter(sw));
						logger.severe(String.format(
								"problem trying to execute operation %s:" + System.getProperty("line.separator") + "%s",
								operation.getId(), sw.toString()));
					}
					logger.logp(Level.FINEST, this.getClass().getName(), "run", "shall we serialize operation? "
							+ Boolean.toString(executed));
					if (executed) {
						logger.logp(Level.FINEST, this.getClass().getName(), "run",
								"going to add operation to queue to be serialized");
						queue.add(operation);
						logger.logp(Level.FINEST, this.getClass().getName(), "run",
								"added operation to queue to be serialized");
					}
					logger.exiting(this.getClass().getName(), "run");
				}
			});
		}
		logger.exiting(this.getClass().getName(), "apply");
	}

	private File getLatestJournal(File path) {

		long maxTs = 0, ts = 0;
		File latest = null;

		File[] listOfFiles = path.listFiles(new FilenameFilter() {
			Matcher matcher = null;
			Pattern pattern = Pattern.compile(JOURNAL_FILE_NAME_REGEX_PATTERN);

			public boolean accept(File dir, String name) {
				matcher = pattern.matcher(name);
				if (matcher.find()) {
					return true;
				}
				return false;
			}
		});

		Matcher matcher = null;
		Pattern pattern = Pattern.compile(JOURNAL_FILE_NAME_REGEX_PATTERN);

		for (int i = 0; i < listOfFiles.length; i++) {
			matcher = pattern.matcher(listOfFiles[i].getName());
			if (matcher.find()) {
				ts = Long.parseLong(matcher.group(2));

				if (ts > maxTs) {
					maxTs = ts;
					latest = listOfFiles[i];
				}
			}

		}

		return latest;
	}

	/**
	 * allow for the lazy loading of the thread pool
	 * 
	 * @return
	 */
	private ExecutorService getExecutor() {
		logger.entering(this.getClass().getName(), "getExecutor");

		if (null == executor)
			executor = Executors.newFixedThreadPool(this.threadPoolSize);

		logger.exiting(this.getClass().getName(), "getExecutor", executor);
		return executor;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((folder == null) ? 0 : folder.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + threadPoolSize;
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
		@SuppressWarnings("unchecked")
		Bag<T, E> other = (Bag<T, E>) obj;
		if (folder == null) {
			if (other.folder != null)
				return false;
		} else if (!folder.equals(other.folder))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (threadPoolSize != other.threadPoolSize)
			return false;
		return true;
	}

}

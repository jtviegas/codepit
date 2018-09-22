package org.aprestos.labs.challenges.nexmo.three;

import java.io.OutputStream;
import java.io.PrintStream;

public class OS {

	private static final long NANO_PER_MILLI = 1000000;
	private Object lock = new Object();
	private Task task = null;
	private boolean on = true;

	public OS(final OutputStream out) {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.setOut(new PrintStream(out));
				runTasks();
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public void Set_timer(int ts, CB f) {
		synchronized (lock) {
			task = Task.create(ts, f);
		}
	}

	private void runTasks() {

		while (on) {
			synchronized (lock) {
				if (null != task && task.getExecutionTime() <= (System.nanoTime() / NANO_PER_MILLI)) {
					task.getCallback().f();
					task = null;
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException ignoreMe) {
			}
		}

	}

	public void shutdown() {
		this.on = false;
	}

}

package org.aprestos.labs.challenges.nexmo.three;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduler {

	private static final long NANOS_IN_MILLI = 1000000;
	private final OS os;
	private final Queue<Task> tasks = new PriorityQueue<Task>(new Comparator<Task>() {

		@Override
		public int compare(Task arg0, Task arg1) {
			return (int) (arg0.getExecutionTime() - arg1.getExecutionTime());
		}
	});
	private boolean on = true;

	Scheduler(OS os) {
		this.os = os;
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				manageSchedule();
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	public void Set_timer2(int ms, CB f) {
		if (null == f)
			throw new IllegalArgumentException("must provide callback");

		Task task = Task.create(ms, f);
		synchronized (tasks) {
			tasks.add(task);
		}
		/*
		 * System.out.println(String.format("scheduling task %s to run at %d",
		 * task.getCallback().getName(), task.getExecutionTime()));
		 */
	}

	public void shutdown() {
		this.on = false;
	}

	private long currentTimeInMillis() {
		return System.nanoTime() / NANOS_IN_MILLI;
	}

	private void manageSchedule() {
		int scheduled = 0;

		while (on) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException ignoreMe) {
			}
			Task task = null;
			synchronized (tasks) {
				task = tasks.peek();
				if (null != task && task.hashCode() == scheduled && currentTimeInMillis() > task.getExecutionTime()) {
					tasks.remove();
					scheduled = 0;
					continue;
				}
			}
			if (null != task && task.hashCode() != scheduled) {
				// new task
				os.Set_timer((int) Math.max(0, task.getExecutionTime() - currentTimeInMillis()), task.getCallback());
				scheduled = task.hashCode();
			}

		}

	}

}

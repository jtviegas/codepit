package org.aprestos.labs.challenges.nexmo.three;

public class Task {

	static Task create(int delay, CB callback) {
		return new Task(delay, callback);
	}

	private long executionTime;
	private CB callback;

	private Task(int delay, CB callback) {
		this.executionTime = System.nanoTime() / 1000000 + delay;
		this.callback = callback;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public CB getCallback() {
		return callback;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callback == null) ? 0 : callback.hashCode());
		result = prime * result + (int) (executionTime ^ (executionTime >>> 32));
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
		Task other = (Task) obj;
		if (callback == null) {
			if (other.callback != null)
				return false;
		} else if (!callback.equals(other.callback))
			return false;
		if (executionTime != other.executionTime)
			return false;
		return true;
	}

}

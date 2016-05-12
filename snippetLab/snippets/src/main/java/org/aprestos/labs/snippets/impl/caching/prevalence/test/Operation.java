package org.aprestos.labs.snippets.impl.caching.prevalence.test;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public abstract class Operation<T, E> implements Callable<E>, Serializable {
	
	private static Logger logger = Logger.getLogger(Operation.class.getName());
	private long id;
	private static final long serialVersionUID = 7957466666462592622L;

	protected T o;

	public Operation() {
		logger.entering(this.getClass().getName(), "Operation");
		synchronized (this) {
			this.id = System.currentTimeMillis();
		}
		logger.exiting(this.getClass().getName(), "Operation");
	}

	public long getId() {
		logger.entering(this.getClass().getName(), "getId");
		logger.exiting(this.getClass().getName(), "getId", this.id);
		return this.id;
	}

	public void setObject(T object) {
		logger.entering(this.getClass().getName(), "setObject", object);
		this.o = object;
		logger.exiting(this.getClass().getName(), "setObject");
	}

	public abstract E execute(T object) throws Exception;

	public E call() throws Exception {
		logger.entering(this.getClass().getName(), "call");
		E result = null;
		result = execute(o);
		logger.exiting(this.getClass().getName(), "call", result);
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((o == null) ? 0 : o.hashCode());
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
		Operation<T,E> other = (Operation<T,E>) obj;
		if (id != other.id)
			return false;
		if (o == null) {
			if (other.o != null)
				return false;
		} else if (!o.equals(other.o))
			return false;
		return true;
	}

	
	

}

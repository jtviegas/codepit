package com.tgedr.labs.challenge.assorted.areawith10players;

public interface ChangeListener<T> {
    
	static enum ChangeType { MOVEMENT, ELIGIBILITY_QUERY }

	void changed(ChangeType type, T t);
	
}
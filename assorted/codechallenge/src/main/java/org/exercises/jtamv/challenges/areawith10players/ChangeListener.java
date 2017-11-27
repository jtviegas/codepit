package org.exercises.jtamv.challenges.areawith10players;

public interface ChangeListener<T> {
    
	static enum ChangeType { MOVEMENT, ELIGIBILITY_QUERY }; 
	void changed(ChangeType type, T t);
	
}
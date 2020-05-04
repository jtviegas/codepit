package com.tgedr.labs.challenge.assorted.areawith10players;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.String.format;

/**
 * Player
 * 	Use Cases
 *		Has its own thread as a player has its own life cycle;
 *		Notifies the listeners, in this case the referee, of every behavioral change,  
 *			movement or query;
 * 		Moves every 1s (movement) , if ejected, waits for 10s to ask (query) to be back in the game;
 * 		Its state depends on the referee calls from the referee thread, 
 * 			so state thread safety is addressed;
 * 
 * @author joaovieg
 *
 */
public class Player implements Runnable, ChangeProvider {

    public static enum State {
		ON, YELLOWED, EJECTED, ELIGIBLE2PLAY, OFF
    }

    // we'll probably incur in not optimal performance but this random instance
    // might be sharable between Player instances
    private static Random random = new Random();
    private final int intervalBetweenMoves,  ejectedTimeOut;
    public static final Point2D.Double OUT = new Point2D.Double(-1.0,-1.0);
    private final double axisLength, stepLength;
    private final int id;
    private Point2D.Double position;
    private State state = State.ON;
    private Set<ChangeListener<Player>> listeners = new HashSet<ChangeListener<Player>>();

    Player(int id, double axisLength, double stepLength, int intervalBetweenMoves, int ejectedTimeOut) {
	this.id = id;
	this.axisLength = axisLength;
	this.stepLength = stepLength;
	this.intervalBetweenMoves = intervalBetweenMoves;
	this.ejectedTimeOut = ejectedTimeOut;
	this.position = OUT;
	debug("starting");
    }

    public int getId() {
	return this.id;
    }

    public Point2D getPosition() {
        return new Point2D.Double(position.x, position.y);
    }

    public void setState(State newState) {
	synchronized (this.state) {
	    if (State.YELLOWED.equals(this.state)
		    && State.YELLOWED.equals(newState))
		this.state = State.EJECTED;
	    else
		this.state = newState;
	}
	debug("new state");
    }

    public State getState() {
	return this.state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.exercises.jtamv.carma.ChangeProvider#addListener(org.exercises.jtamv.
     * carma.ChangeListener)
     */
    @Override
    public void addListener(ChangeListener<Player> listener) {
		this.listeners.add(listener);
    }

    @Override
    public void run() {
	int waitPeriod = 0;
	boolean loop = true;

	while (loop) {

	    try {
		Thread.sleep(waitPeriod);
	    } catch (InterruptedException e) {
	    }
	    synchronized (this.state) {
		switch (this.state) {
		case ON:
		case YELLOWED:
		    moveAndNotify(getNewPosition());
		    waitPeriod = intervalBetweenMoves;
		    break;
		case EJECTED:
		    moveAndNotify(OUT);
		    this.state = State.ELIGIBLE2PLAY;
		    waitPeriod = ejectedTimeOut;
		    break;
		case ELIGIBLE2PLAY:
		    queryEligibility();
		    waitPeriod = intervalBetweenMoves;
		    break;
		case OFF:
		    moveAndNotify(OUT);
		    loop = false;
		}
	    }

	}
    }

    public void debug(String msg) {
	System.out.println(format("[player %d <%s> @(%f,%f)]: ", this.id,
		this.state.toString(), this.position.x, this.position.y) + msg);
    }

    private void notifyListeners(ChangeListener.ChangeType type) {
	for (ChangeListener<Player> l : listeners)
	    l.changed(type, this);
    }

    private void queryEligibility() {
	debug("querying for eligibility");
	notifyListeners(ChangeListener.ChangeType.ELIGIBILITY_QUERY);
    }

    private Point2D.Double getRandomPosition() {
	return new Point2D.Double(this.axisLength * random.nextFloat(),
		this.axisLength * random.nextFloat());
    }
    
    private void moveAndNotify(Point2D.Double newPosition) {
	this.position = newPosition;
	//debug("moved");
	notifyListeners(ChangeListener.ChangeType.MOVEMENT);
    }
    
    /**
     * move randomly to the next position
     * checking area boundaries
     * 
     */
    private Point2D.Double getNewPosition() {
	Point2D.Double result = null;
	
	if(this.position.equals(OUT))
	    result = getRandomPosition();
	else {
	    result = new Point2D.Double(this.position.getX(),
		    this.position.getY());

		if (random.nextBoolean()) {
		    // moving on x axis
		    if (random.nextBoolean()
			    && this.position.x + this.stepLength <= this.axisLength) {// going
			// positive
			result.x = this.position.x + this.stepLength;
		    } else {// going negative
			if (this.position.x - this.stepLength >= 0)
			    result.x = this.position.x - this.stepLength;
			else
			    result.x = this.position.x + this.stepLength;
		    }
		} else {
		    // moving on y axis
		    if (random.nextBoolean()
			    && this.position.y + this.stepLength <= this.axisLength) {// going
			// positive
			result.y = this.position.y + this.stepLength;
		    } else {// going negative
			if (this.position.y - this.stepLength >= 0)
			    result.y = this.position.y - this.stepLength;
			else
			    result.y = this.position.y + this.stepLength;
		    }
		}
	}
	
	return result;
    }

}

package com.tgedr.labs.challenge.assorted.areawith10players;

import static java.lang.String.format;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * Exercise
 * 
 * 	The exercise defines the players, register the referee with them, 
 * 	and kicks off their own threads.
 * 	Then it monitors the players list waiting for it to be almost empty
 * 	finding the last player in it to be the winner.
 * 	The players are randomly defining the next movement direction;
 * 	The game takes a lot of time, for the players are moving at every second, 
 * 	and as the number of players gets smaller, it takes more time for them to bump onto each other.
 * 	We can always increase the speed 
 * 	of the game by decreasing INTERVAL_BETWEEN_MOVES_IN_MILLIS.
 * 	Normally if we set it to 1 millisecond the game will last 1 second.
 * 
 * @author joaovieg
 *
 */
public class Exercise {
    //---- exercise parameters
    private static final double AXIS_LENGTH = 100.0;
    private static final double MOVE_STEP = 1.0;
    private static final double MIN_DISTANCE_ALLOWED = 2.0;
    private static final int INTERVAL_BETWEEN_MOVES_IN_MILLIS = 1000;
    private static final int EJJECTED_TIME_OUT_IN_MILLIS = 10000;
    
    //map with players and ELECTED-2-PLAY-AGAIN status
    private final Map<Player, Boolean> players = new HashMap<Player, Boolean>();
    private static final long MILLIS2MIN = 1000*60;
    private static final int PLAYERS_CHECK_INTERVAL_IN_MILLIS = 5000;
    private static final int NUM_OF_PLAYERS = 10;
    private static final int THREAD_POOL_SIZE = 11;
    //will use the pool to submit tasks
    private ExecutorService pool;
    private Referee referee;
    
    public void play() {
	Date start = new Date();
	System.out.println(format("----------------------------- START %s -----------------------------", start.toString()));
	pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	referee = new Referee(pool, MIN_DISTANCE_ALLOWED, players);
	
	//utility id generator
	AtomicInteger idGenerator = new AtomicInteger(); 
	for(int i = 0; i < NUM_OF_PLAYERS; i++){
	    Player player = new Player(idGenerator.incrementAndGet(), AXIS_LENGTH, MOVE_STEP, INTERVAL_BETWEEN_MOVES_IN_MILLIS, EJJECTED_TIME_OUT_IN_MILLIS );
	    players.put(player, false);
	    player.addListener(referee);
	    pool.submit(player);
	}
	
	while(true){
	    try { Thread.sleep(PLAYERS_CHECK_INTERVAL_IN_MILLIS); } catch (InterruptedException e) { e.printStackTrace(); }
	    synchronized(players){
		Iterator<Map.Entry<Player, Boolean>> iterator = players.entrySet().iterator();
		while(iterator.hasNext()){
		    Map.Entry<Player, Boolean> entry = iterator.next();
		    Player player = entry.getKey();
		    
		    if(player.getState().equals(Player.State.OFF)){
			iterator.remove();
		    }
		}
		
		if(1 == players.size()){
		    Player player = players.keySet().toArray(new Player[]{})[0];
		   System.out.println( format("!!! player %d is the winner !!!", player.getId()));
		   player.setState(Player.State.OFF);
		   break;
		}
	    }
	}
	Date end = new Date();
	long dateDiffInMin = (end.getTime() - start.getTime())/MILLIS2MIN;
	pool.shutdown();
	System.out.println(format("----------------------------- End %s (game took %d minutes) -----------------------------", end.toString(), dateDiffInMin));
    }
    
    public static void main(String[] args){
	new Exercise().play();
    }
    
  
}

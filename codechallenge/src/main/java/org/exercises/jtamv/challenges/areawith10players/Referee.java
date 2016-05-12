package org.exercises.jtamv.challenges.areawith10players;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Referee
 * 	Use Cases:
 * 		Registers itself as a listener to all players.
 * 		Is then notified when: 
 * 			- player movement -> checks if player is in allowable distance from the others 
 * 					and if not changes its state showing a yellow card;
 * 			- player query to go back into the game -> if the player has never been 
 * 					ejected allows him to go back into the game
 * 		
 * @author joaovieg
 *
 */
public class Referee implements ChangeListener<Player> {
    
    private final double minDistance;
    private final ExecutorService pool;
    private final Map<Player, Boolean> players;
    
    public Referee(ExecutorService pool, double minDistanceAllowed, Map<Player, Boolean> players) {
	this.pool = pool; 
	minDistance = minDistanceAllowed;
	this.players = players;
    }
    
    
    @Override
    public void changed(ChangeType type, Player player) {
	switch (type) {
        	case MOVEMENT:// create a new movement task
        	    pool.submit(new MovementTask(player));
        	    break;
        	case ELIGIBILITY_QUERY:
        	    pool.submit(new EligibilityQueryTask(player));
        	    break;
	}

    }

    
    class EligibilityQueryTask implements Runnable {
	private Player player;
	EligibilityQueryTask(Player player){
	    this.player = player;
	}
	@Override
	public void run() {
	    synchronized(players){
		//we check for the map value where we find if the 
		// player has already been ejected in the past
		if(players.get(this.player)){
		    player.setState(Player.State.OFF);
		}
		else {
		    player.setState(Player.State.ON);
		    players.put(this.player, true);
		}
	    }
	}
	
    }
    
    class MovementTask implements Runnable {
	private Player player;
	MovementTask(Player player){
	    this.player = player;
	}

	@Override
	public void run() {
	    synchronized(players){
		
		if(player.getPosition().equals(Player.OUT))
		    return;
		
		// set/update player in map, assume the player has never been ejjected
		if(!players.get(player))
		    players.put(player, false);
		//check for distances
		for(Map.Entry<Player, Boolean> entry: players.entrySet()){
		    Player p = entry.getKey();
		    // we just want to compare with other players that are actually playing, also 
		    // there might be sometimes a lag between the moment when the referee changes the state of the player and he actually moves out
		    // so as we are the only changing the state of the player and we do hold a lock on the map we can prevent this case here 
		    if(p.getId() != player.getId() && ( p.getState().equals(Player.State.ON) || p.getState().equals(Player.State.YELLOWED) ) ){
			// if the distance between these two players is smaller than the allowed value
			// then we don't like it and show a yellow card to the player
			if(Double.compare( minDistance, player.getPosition().distance(entry.getKey().getPosition()) ) > 0){
			    player.setState(Player.State.YELLOWED);
			    break;
			}
		    }
		}
	    }
	}
	
    }

}

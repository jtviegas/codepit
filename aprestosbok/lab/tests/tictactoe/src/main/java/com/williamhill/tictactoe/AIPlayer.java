/**
 * 
 */
package com.williamhill.tictactoe;

import java.util.ArrayList;
import java.util.List;

import com.williamhill.tictactoe.gamestrategies.FirstStrategy;
import com.williamhill.tictactoe.gamestrategies.GameStrategy;

/**
 * @author joao.viegas
 *
 */
public class AIPlayer extends Player 
{

	private List<Move> history = new ArrayList<Move>();
	private GameStrategy strategy;
	/**
	 * @param name
	 * @param sign
	 */
	public AIPlayer(String name, Signs sign) {
		super(name, sign);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.williamhill.tictactoe.Player#getNextMove(com.williamhill.tictactoe.IBoard)
	 */
	@Override
	public Move getNextMove(IBoard board) 
	{
		Move _result = null;
		
		if(null == strategy)
			strategy=resolveStrategy(board);
		
		_result = strategy.nextMove(history, board);

		return _result; 
	}
	
	private GameStrategy resolveStrategy(IBoard board)
	{
		return new FirstStrategy();

	}

}

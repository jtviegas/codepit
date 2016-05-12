package com.williamhill.tictactoe.analysis;

import java.util.List;

import com.williamhill.geometry.discrete.Line;
import com.williamhill.tictactoe.BoardImpl;
import com.williamhill.tictactoe.IBoard;
import com.williamhill.tictactoe.Signs;

public interface BoardAnalysis 
{
	/**
	 * Finds if this is a winning move.
	 * 
	 * @param _s
	 * @param _row
	 * @param _column
	 * @param _board
	 * @return
	 */
	public boolean isSuccess(Signs _s, int _row, int _column, BoardImpl _board);

	/**
	 * get all the possible lines in the board
	 * 
	 * @return
	 */
	public abstract List<Line> getAllLines();

	/**
	 * Collects the Sign array sought in the specific gameboard line
	 * 
	 * @param _l
	 * @param _board
	 * @return
	 */
	public abstract Signs[] getBoardLineValues(Line _l, IBoard _board);

	/**
	 * counts number of specific Sign in a Signs array
	 * 
	 * @param _signs
	 * @param _o
	 * @return
	 */
	public abstract int countInstances(Signs[] _signs, Signs _o);
}


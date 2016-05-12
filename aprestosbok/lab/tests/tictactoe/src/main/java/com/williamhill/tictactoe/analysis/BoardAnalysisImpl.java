package com.williamhill.tictactoe.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.williamhill.geometry.discrete.Line;
import com.williamhill.geometry.discrete.Point;
import com.williamhill.tictactoe.BoardImpl;
import com.williamhill.tictactoe.IBoard;
import com.williamhill.tictactoe.Signs;

/**
 * Helper class to do analysis on the game board
 * @author joao.viegas
 *
 */
public class BoardAnalysisImpl implements BoardAnalysis
{
	private static final Logger logger = Logger.getLogger(BoardAnalysis.class.getName());
	
	private int boardDimension;
	
	public BoardAnalysisImpl() 
	{}
	
	public BoardAnalysisImpl(int _dimension) 
	{
		this.boardDimension = _dimension;
	}

	/**
	 * @return the boardDimension
	 */
	public int getBoardDimension() {
		return boardDimension;
	}

	/**
	 * @param boardDimension the boardDimension to set
	 */
	public void setBoardDimension(int boardDimension) {
		this.boardDimension = boardDimension;
	}

	
	/**
	 *	Finds if this is a winning move.
	 *	Rationale:
	 *		1st finds all lines that overlap the point;
	 *		2nd for every such line gets the corresponding signs array on the game board;
	 *		3rd if that array has number of signs equal to the axis length than that's a winning move
	 */
	public boolean isSuccess(Signs _s, int _row, int _column, BoardImpl _board)
	{
		boolean _result = false;
		
		Line[] _lines = Line.getOverlappingLines(new Point(_column-1,_row-1),new Point(boardDimension-1,boardDimension-1));

		for(Line _l: _lines)
		{
			if(boardDimension == countInstances(getBoardLineValues(_l, _board),_s))
			{
				_result=true;
				break;
			}
		}
	
		return _result;
	}

	
	/**
	 * counts number of specific Sign in a Signs array
	 * @param _signs
	 * @param _o
	 * @return
	 */

	public int countInstances(Signs[] _signs, Signs _o)
	{
		int _count = 0;
		for(Signs _instance : _signs)
		{
			if(_instance.equals(_o))
				_count++;
		}
		
		return _count;
	}
	
	/**
	 * Collects the Sign array sought in the specific gameboard line
	 * @param _l
	 * @param _board
	 * @return
	 */

	public Signs[] getBoardLineValues(Line _l, IBoard _board)
	{
		List<Signs> _signs = new ArrayList<Signs>();
		
		for(Point _p : _l.getPoints())
		{
			Signs _s = _board.getCell(_p.getY()+1, _p.getX()+1);
			if(null != _s)
				_signs.add(_s);
		}
		
		return _signs.toArray(new Signs[_signs.size()]);
	}


	/**
	 * get all the possible lines in the board
	 */
	public List<Line> getAllLines()
	{
		List<Line> _lines = new ArrayList<Line>();
		
		//vertical lines
		for(int _i=1; _i < boardDimension; _i++ )
			_lines.add(new Line(new Point(_i,0),new Point(_i,boardDimension-1)));
		
		//horizontal lines
		for(int _i=1; _i < boardDimension; _i++ )
			_lines.add(new Line(new Point(0,_i),new Point(boardDimension-1,_i)));
		
		_lines.add(new Line(new Point(0,0),new Point(boardDimension-1,boardDimension-1)));
		_lines.add(new Line(new Point(boardDimension-1,0),new Point(0,boardDimension-1)));
		
	
		return _lines;
	}
	
}


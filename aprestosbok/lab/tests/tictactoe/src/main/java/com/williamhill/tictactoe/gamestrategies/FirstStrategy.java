package com.williamhill.tictactoe.gamestrategies;

import java.util.ArrayList;
import java.util.List;

import com.williamhill.geometry.discrete.Line;
import com.williamhill.geometry.discrete.Point;
import com.williamhill.tictactoe.IBoard;
import com.williamhill.tictactoe.Move;
import com.williamhill.tictactoe.Signs;
import com.williamhill.tictactoe.analysis.BoardAnalysis;
import com.williamhill.tictactoe.analysis.BoardAnalysisImpl;

public class FirstStrategy implements GameStrategy 
{
	private BoardAnalysis analysis = null;
	
	public FirstStrategy() 
	{
		// TODO Auto-generated constructor stub
	}

	public Move nextMove(List<Move> _history, IBoard _board) 
	{
		Move _result = null;
		boolean _allowedMove = false;
		List<Move> _moves;
		
		while(!_allowedMove)
		{
			_allowedMove=false;
			
			if(0 == _history.size())
				_result = new Move(2,2); //go to the middle
			else 
			{
				_result = getUrgentMove(_board);
				
				if(null == _result)
				{
					if(_history.size() < 5) //go to corners
					{
						_moves = getCorners();
						for(Move _m: _moves)
						{
							if( !_history.contains(_m) )
							{
								_result=_m;
								break;
							}
						}
						
					}
					else //go to all others
					{
						_moves = getAllRemainingMoves();
						for(Move _m: _moves)
						{
							if( !_history.contains(_m) )
							{
								_result=_m;
								break;
							}
						}
					}
				}
				
				
			}
				
				
			if(null == _board.getCell(_result.getRow(), _result.getColumn()))
					_allowedMove=true;
			
			_history.add(_result);
		}
		
		return _result;
	}
	
	private List<Move> getCorners()
	{
		List<Move> _result = new ArrayList<Move>();
		_result.add(new Move(1,1));
		_result.add(new Move(1,AXIS_DIMENSION));
		_result.add(new Move(AXIS_DIMENSION,1));
		_result.add(new Move(AXIS_DIMENSION,AXIS_DIMENSION));
		
		return _result;
	}
 
	
	private List<Move> getAllRemainingMoves()
	{
		List<Move> _result = new ArrayList<Move>();
		List<Move> _corners = getCorners();
		Move _move = null;
		
		for(int _i=1; _i <= AXIS_DIMENSION; _i++ )
			for(int _j=1; _j <= AXIS_DIMENSION; _j++ )
			{
				if(_i != 1 && _j != 1)
				{
					_move = new Move(_i,_j);
					if(!_corners.contains(_move))	
						_result.add(_move);
				}
				
			}
		
		return _result;
	}
	
	private Move getUrgentMove(IBoard _board)
	{
		Move _result = null;
		
		if(null == this.analysis)
			this.analysis = new BoardAnalysisImpl(AXIS_DIMENSION);
		
		for(Line _line:analysis.getAllLines())
		{
			if(analysis.countInstances(analysis.getBoardLineValues(_line, _board), Signs.Circle)  == (AXIS_DIMENSION-1))
			{
				for(Point _point:_line.getPoints())
				{
					if(null == _board.getCell(_point.getY()+1, _point.getX()+1))
						return new Move(_point.getY()+1, _point.getX()+1);
				}
			}
		}
		
		return _result;
	}


}

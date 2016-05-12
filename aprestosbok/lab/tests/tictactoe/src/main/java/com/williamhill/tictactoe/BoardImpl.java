package com.williamhill.tictactoe;

import com.williamhill.tictactoe.analysis.BoardAnalysis;
import com.williamhill.tictactoe.analysis.BoardAnalysisImpl;


public class BoardImpl implements IBoard
{
	public static int count=0;
	public int index;
	private Signs[][] boardCells = new Signs[AXIS_DIMENSION][AXIS_DIMENSION];
	private Status status;
	private static final int AXIS_DIMENSION=3;
	private BoardAnalysis analysis = new BoardAnalysisImpl(AXIS_DIMENSION);
	private static final int MAX_MOVES = (int)Math.pow(AXIS_DIMENSION,2);
	private int remainingMoves = MAX_MOVES;
	
	public BoardImpl() 
	{
		index=count++;
	}

	/**
	 * This method return the sign of the cell at the row and column specified
	 * @param row Represents the cell row. Value should be between 1 and 3
	 * @param column Represents the cell column. Value should be between 1 and 3
	 * @return the sign associated to the specified Cell. It returns null if the cell has not been assigned yet
	 * @throws IllegalArgumentException This exception is thrown if the parameters are invalid
	 * @see Signs Signs Enum definiton for more information
	 */
	public Signs getCell(int row, int column) throws IllegalArgumentException 
	{
		if((row < 1 || row > AXIS_DIMENSION) || (column < 1 || column > AXIS_DIMENSION))
			throw new IllegalArgumentException("rows and columns must be between 1 and " + AXIS_DIMENSION);
		
		return boardCells[column-1][row-1];
	}

	/**
	 * 
	 * This method assign a specific sign for the cell specified by its row and column value.
	 * @param row Represents the cell row. Value should be between 1 and 3
	 * @param column Represents the cell row. Value should be between 1 and 3
	 * @param sign Is the sign to associate with the current cell
	 * @throws InvalidAssignmentException This exception is thrown if the selected cell is not free and/or the current status is not Open (See {@link #getStatus()} for more details).
	 * @throws IllegalArgumentException This exception is thrown if the row and/or column value is invalid 
	 * (outside the bounds [1 - 3]) or the sign value is null
	 * @see Signs Signs Enum definiton for more information
	 * 
	 */
	public void setCell(int row, int column, Signs sign)throws InvalidAssignmentException, IllegalArgumentException 
	{
		if(MAX_MOVES == this.remainingMoves)
		{
			//game is opening
			this.status = Status.GameOpen;
		}
		
		if(Status.GameOpen.equals(this.status))
		{
			if((row < 1 || row > AXIS_DIMENSION) || (column < 1 || column > AXIS_DIMENSION))
				throw new IllegalArgumentException("rows and columns must be between 1 and " + AXIS_DIMENSION);
			
			if(null != boardCells[column-1][row-1])
				throw new InvalidAssignmentException(row,column,"!!! cell already used !!!");
			
			boardCells[column-1][row-1] = sign;
			--remainingMoves;
			updateStatus(row,column,sign);
		}
		else
			throw new InvalidAssignmentException(row,column,"!!! game is not open !!!");
		
	}


	public Status getStatus() 
	{
		return this.status;
	}

	public IBoard clone()
	{
		
		BoardImpl _clone = new BoardImpl();
		
		Signs[][] _board = new Signs[AXIS_DIMENSION][AXIS_DIMENSION];
		
		for(int _i=0;_i<AXIS_DIMENSION;_i++)
			for(int _j=0;_j<AXIS_DIMENSION;_j++)
				_board[_i][_j] = this.boardCells[_i][_j];
		
		_clone.boardCells = _board;
		_clone.status = this.status;
		
		return _clone;
	}
	
	/**
	 * Finds if the current move changes the game status
	 * @param row
	 * @param column
	 * @param sign
	 */
	private void updateStatus(int row,int column,Signs sign)
	{
		if(analysis.isSuccess(sign, row, column, this))
		{
			if(sign.equals(Signs.Cross))
				this.status = Status.CrossWin;
			else
				this.status = Status.CircleWin;
		}
		else
		{
			if(0 == remainingMoves)
				this.status = Status.Draw;
		}
			
	}

}




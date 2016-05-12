package com.williamhill.tictactoe.gamestrategies;

import java.util.List;

import com.williamhill.tictactoe.IBoard;
import com.williamhill.tictactoe.Move;

public interface GameStrategy 
{
	static final int AXIS_DIMENSION=3;
	Move nextMove(List<Move> _history, IBoard _board);
}

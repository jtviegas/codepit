/**
 * 
 */
package com.williamhill.tictactoe;

import java.util.Scanner;

/**
 * @author joao.viegas
 *
 */
public class UserPlayer extends Player {

	/**
	 * @param name
	 * @param sign
	 */
	public UserPlayer(String name, Signs sign) {
		super(name, sign);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.williamhill.tictactoe.Player#getNextMove(com.williamhill.tictactoe.IBoard)
	 */
	@Override
	public Move getNextMove(IBoard board) 
	{
		String[] _params = null;
		Move _move = null;
		String _input = null;
		boolean _validInput = false;
		Scanner _scanner = new Scanner(System.in);
		while(!_validInput)
		{
			System.out.print("please provide a valid 'row,column' :");
			_input = _scanner.nextLine();
			if(_input.matches("^[1-3],[1-3]$"))
			{
				_params = _input.trim().split(",");
				_move = new Move(Integer.parseInt(_params[0]), Integer.parseInt(_params[1]));
				if( null == board.getCell(_move.getRow(), _move.getColumn()))
					return _move;
			}
		}

		return null;
	}

}

package org.challenges.odobo.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.challenges.odobo.exceptions.ChallengeException;
import org.challenges.odobo.exceptions.WrongParameterException;
import org.challenges.odobo.interfaces.Challenge;
import org.challenges.odobo.model.Point;
import org.challenges.odobo.model.Rectangle;
import org.challenges.odobo.model.Space;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Challenge Implementation
 * 
 * this class tries to summarize the problem and the solution proposed.
 * The challenge has the following steps:
 * 1) get input about how many rectangles to generate;
 * 2) generate vertical rectangles, skyline-similar;
 * 3) regenerate the rectangles space into the minimum horizontal rectangles equivalent;
 * 4) print the challenge in JSON format;
 * 
 * @author joao.viegas
 *
 */
public class ChallengeImpl implements Challenge 
{
	private static final int MIN_RECTANGLES=5, MAX_RECTANGLES=15;
	public static final int MAX_Y = 150, MAX_X = 250;
	private static final String INPUT_QUESTION ="Please, how many rectangles should I generate ? [%d-%d]: ";
	private static final String WRONG_INPUT ="Should provide a valid number of rectangles between %d and %d !";
	
	private int numRects;
	private List<Rectangle> sourceRectangles;
	private List<Rectangle> rectangles;
	
	public ChallengeImpl() {}

	/* (non-Javadoc)
	 * @see org.challenges.odobo.model.ChallengeI#getNumRects()
	 */
	public int getNumRects() {
		return numRects;
	}

	/* (non-Javadoc)
	 * @see org.challenges.odobo.model.ChallengeI#setNumRects(int)
	 */
	public void setNumRects(int numRects) {
		this.numRects = numRects;
	}

	/* (non-Javadoc)
	 * @see org.challenges.odobo.model.ChallengeI#getSourceRectangles()
	 */
	public List<Rectangle> getSourceRectangles() {
		return sourceRectangles;
	}

	/* (non-Javadoc)
	 * @see org.challenges.odobo.model.ChallengeI#setSourceRectangles(org.challenges.odobo.model.Rectangle[])
	 */
	public void setSourceRectangles(List<Rectangle> sourceRectangles) {
		this.sourceRectangles = sourceRectangles;
	}

	/* (non-Javadoc)
	 * @see org.challenges.odobo.model.ChallengeI#getRectangles()
	 */
	public List<Rectangle> getRectangles() {
		return rectangles;
	}

	/* (non-Javadoc)
	 * @see org.challenges.odobo.model.ChallengeI#setRectangles(org.challenges.odobo.model.Rectangle[])
	 */
	public void setRectangles(List<Rectangle> rectangles) {
		this.rectangles = rectangles;
	}
	
	/**
	 * JSON format printing
	 */
	public String toJSON() throws ChallengeException
	{
		ObjectMapper _mapper =  new ObjectMapper();
    	String _result = null;
    	
    	try 
    	{
			_result = _mapper.writeValueAsString(this);
		} 
    	catch (Exception e) 
    	{
    		throw new ChallengeException(e);
		}
    	
    	return _result;
	}

	/**
	 * 
	 */
	public void execute() throws ChallengeException 
	{
		// TODO Auto-generated method stub
		this.numRects = getInput();
		List<Space> _rects = generate(this.numRects);
		List<Space> _rerects = regenerate(_rects); 
		
		this.sourceRectangles = Space.asRectangles(_rects);
		this.rectangles = Space.asRectangles(_rerects);
		 
		System.out.println(toJSON());
		
	}
	
	/**
	 * get number of rectangles to generate
	 * @return
	 */
	private int getInput()
	{
		int _result = 0;
		 Scanner _scanner = new Scanner(System.in);
		 
		 System.out.print(String.format(INPUT_QUESTION, MIN_RECTANGLES, MAX_RECTANGLES));
		 try 
		 {
			_result = _scanner.nextInt();
			if(_result > MAX_RECTANGLES || _result < MIN_RECTANGLES)
				throw new WrongParameterException();
			
		} 
		 catch (Exception e) 
		{
			 System.out.println(String.format(WRONG_INPUT, MIN_RECTANGLES, MAX_RECTANGLES));
			 System.exit(-1);
		} 
		 
		 return _result;
	}
	
	/**
	 * generate adjacent vertical rectangles
	 */
	public List<Space> generate(int _num) 
	{
		Random _random = new Random();
		int _x1 = 0, _x2 = 0, _y = 0, _count = 0;
		List<Space> _result = new ArrayList<Space>();
		
		
		while(_count < _num)
		{
			_y = 1 + _random.nextInt(9);
			_x2 = _x2 + (1 + _random.nextInt(2));
			_result.add(new Space(new Point(_x1,_y), new Point(_x2,MAX_Y)));
			_x1 = _x2;
			_count++;
		}
		
		return _result;
	}

	/**
	 * regenerate the source space in a different way with different shapes,
	 * in this case in horizontal rectangles.
	 * Rationale:
	 * - given the source space explode it in singular spaces;
	 * - go over singular spaces line by line and merge the adjacent spaces on each y level horizontally;
	 * - go over the resulting lines and merge adjacent spaces/edges on the y coordinate;
	 */
	public List<Space> regenerate(List<Space> _spaces) throws ChallengeException 
	{
		List<Space> _explodedSpaces = new ArrayList<Space>();
		List<Space> _horizontallyMerged = new ArrayList<Space>();
		List<Space> _result = new ArrayList<Space>();
		List<Space> _spacesAtY = null;
		List<Space> _spacesAtX = null;
		
		int _y=0, _x=0;
		
		
		//explode spaces
		for(Space _s : _spaces)
			Space.explode(_explodedSpaces, _s);
		
		//merge horizontally
		while(_y < MAX_Y)
		{
			_spacesAtY = Space.getSpacesByY(_explodedSpaces, _y++);
			if(0 < _spacesAtY.size())
				_horizontallyMerged.addAll(Space.merge(_spacesAtY));
			
		}
		
		//merge vertically
		while(_x < MAX_X)
		{
			_spacesAtX = Space.getSpacesByX(_horizontallyMerged, _x++);
			if(0 < _spacesAtX.size())
				_result.addAll(Space.merge(_spacesAtX));
			
		}

		return _result;
	}
	
}

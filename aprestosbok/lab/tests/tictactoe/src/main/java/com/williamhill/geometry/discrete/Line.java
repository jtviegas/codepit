package com.williamhill.geometry.discrete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line 
{

	private Point a,b;
	
	public Line(){}
	
	public Line(Point _a, Point _b)
	{
		this.a=_a;
		this.b=_b;
	}

	/**
	 * @return the a
	 */
	public Point getA() {
		return a;
	}
	/**
	 * @param a the a to set
	 */
	public void setA(Point a) {
		this.a = a;
	}
	/**
	 * @return the b
	 */
	public Point getB() {
		return b;
	}
	/**
	 * @param b the b to set
	 */
	public void setB(Point b) {
		this.b = b;
	}
	private int getDimension()
	{
		return Math.max(Math.abs(a.getX()-b.getX())+1, Math.abs(a.getY()-b.getY())+1);
	}
	
	/**
	 * sorts the line edges, based on Y axis, first, and X axis after 
	 */
	private void sort()
	{
		if(a.getY() > b.getY())
		{
			swap();
		}
		else if (a.getY() == b.getY())
		{
			if(a.getX()>b.getX())
				swap();
		}
	}
	
	private void swap()
	{
		Point _dummy = (Point) a.clone();
		a = (Point) b.clone();
		b = _dummy;
	}
	
	/**
	 * gets the points that this line comprises.
	 * Rationale:
	 * 		tries to find the edges of the line and traverse it.
	 * @return
	 */
	public Point[] getPoints()
	{
		sort();
		
		int _x=a.getX();
		int _y=a.getY();
		int _index=0;
		
		Point[] _result = new Point[getDimension()];
		
		//NE-SW direction
		if(_x > b.getX() && _y < b.getY())
		{
			_result[_index++]=new Point(_x,_y);
			while( (--_x)>=0 && (++_y)<=b.getY() )
				_result[_index++]=new Point(_x,_y);
		}
		else
			//N-S direction
			if(_x == b.getX() && _y < b.getY())
			{
				_result[_index++]=new Point(_x,_y);
				while((++_y)<=b.getY() )
					_result[_index++]=new Point(_x,_y);
			}
			else
				//NW-SE
				if(_x < b.getX() && _y < b.getY())
				{
					_result[_index++]=new Point(_x,_y);
					while( (++_x)<=b.getX() && (++_y)<=b.getY() )
						_result[_index++]=new Point(_x,_y);
				}
				else
					//W-E
					if(_x < b.getX() && _y == b.getY())
					{
						_result[_index++]=new Point(_x,_y);
						while((++_x)<=b.getX() )
							_result[_index++]=new Point(_x,_y);
					}
					// X == Y
					else
						if(a.equals(b))
							_result[_index++]=new Point(_x,_y);
		
		return Arrays.copyOf(_result, _index);
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Line)) {
			return false;
		}
		Line other = (Line) obj;
		if (a == null) {
			if (other.a != null) {
				return false;
			}
		} else if (!a.equals(other.a)) {
			return false;
		}
		if (b == null) {
			if (other.b != null) {
				return false;
			}
		} else if (!b.equals(other.b)) {
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * Finds all the lines that overlap a specific point.
	 * Rationale:
	 * 		1st finds the overlapping diagonal lines, always two except in the case of a corner point, which is just one;
	 * 		2nd finds the overlapping vertical line;
	 * 		3rd finds the overlapping horizontal line;
	 * 		returns it all in an array.
	 * 
	 * @param _p
	 * @param _spaceLimit
	 * @return
	 */
	public static Line[] getOverlappingLines(Point _p, Point _spaceLimit)
	{	
		List<Line> _result = getOverlappingDiagonalLines(_p,_spaceLimit);
		_result.add(getOverlappingVerticalLine( _p,_spaceLimit));
		_result.add(getOverlappingHorizontalLine(_p,_spaceLimit));
		
		return _result.toArray(new Line[_result.size()]);
	}
	
	private static Line getOverlappingVerticalLine(Point _p, Point _spaceLimit)
	{
		return new Line(new Point(_p.getX(),0),new Point(_p.getX(),_spaceLimit.getY()));
	}
	
	private static Line getOverlappingHorizontalLine(Point _p, Point _spaceLimit)
	{
		return new Line(new Point(0,_p.getY()),new Point(_spaceLimit.getX(),_p.getY()));
	}
	
	private static List<Line> getOverlappingDiagonalLines(Point _p, Point _spaceLimit)
	{
		List<Line> _result = new ArrayList<Line>();
		Line _nwse = new Line();
		Line _swne = new Line();
		
		//NW-SE diagonal
		int _x = _p.getX();
		int _y = _p.getY();
		
		//NE and SW can't have NW-SE diagonal 
		if( !((_x == _spaceLimit.getX() && _y == 0) || (_x == 0 && _y == _spaceLimit.getY())) )
		{
		
			while( (_x-1)>-1 &&  (_y-1)>-1 )
			{
				_x--;
				_y--;
			}
			
			_nwse.setA(new Point(_x,_y));
			
			while( (_x+1)<=_spaceLimit.getX() &&  (_y+1)<=_spaceLimit.getY() )
			{
				_x++;
				_y++;
			}
			
			_nwse.setB(new Point(_x,_y));
			_result.add(_nwse);
		}
		
		//NE-SW diagonal
		_x = _p.getX();
		_y = _p.getY();
		
		//NW and SE can't have NE-SW diagonal 
		if( !((_x == 0 && _y == 0) || (_x == _spaceLimit.getX() && _y == _spaceLimit.getY())) )
		{
			while( (_x+1)<=_spaceLimit.getX() &&  (_y-1)>-1 )
			{
				_x++;
				_y--;
			}
			
			_swne.setA(new Point(_x,_y));
			
			while( (_x-1)>-1 &&  (_y+1)<=_spaceLimit.getY() )
			{
				_x--;
				_y++;
			}
			
			_swne.setB(new Point(_x,_y));
			_result.add(_swne);
		}

		return _result;
	}


}

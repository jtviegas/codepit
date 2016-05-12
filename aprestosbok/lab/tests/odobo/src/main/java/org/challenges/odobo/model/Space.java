package org.challenges.odobo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.challenges.odobo.exceptions.UnMergeableException;



public class Space 
{
	//upper left point
	private Point A;
	//lower right point
	private Point B; 
	
	public Space()
	{}

	public Space(Point _A,Point _B)
	{
		this.A = _A;
		this.B = _B;
	}

	
		/**
	 * @return the a
	 */
	public Point getA() {
		return A;
	}

	/**
	 * @param a the a to set
	 */
	public void setA(Point a) {
		A = a;
	}

	/**
	 * @return the b
	 */
	public Point getB() {
		return B;
	}

	/**
	 * @param b the b to set
	 */
	public void setB(Point b) {
		B = b;
	}

	public String toString()
	{
		return String.format("(%s,%s)", this.A.toString(), this.B.toString()); 
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((A == null) ? 0 : A.hashCode());
		result = prime * result + ((B == null) ? 0 : B.hashCode());
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
		if (!(obj instanceof Space)) {
			return false;
		}
		Space other = (Space) obj;
		if (A == null) {
			if (other.A != null) {
				return false;
			}
		} else if (!A.equals(other.A)) {
			return false;
		}
		if (B == null) {
			if (other.B != null) {
				return false;
			}
		} else if (!B.equals(other.B)) {
			return false;
		}
		return true;
	}

	public Object clone()
	{
		return new Space((Point)this.A.clone(), (Point)this.B.clone());
	}
	
	
	//---------------------------------------------------------------------------------------------------------
	//----------------------------PUBLIC METHODS SECTION-------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	
	
	
	public Rectangle asRectangle()
	{
		return new Rectangle(this.A.getX(),this.A.getY(),this.B.getX()-this.A.getX(), this.B.getY()-this.A.getY());
	}
	
	public Space merge(Space _other) throws UnMergeableException
	{
		Space _result = null;
		
		if(isAdjacentByX(_other))
		{
			_result = mergeByX(_other);
		}
		else
			if(isAdjacentByY(_other))
			{
				_result = mergeByY(_other);
			}
			else
				throw new UnMergeableException("unmergeable :" + this.toString() + " | " + _other.toString());
				
		return _result;
	}
	
	public Space mergeByX(Space _other) throws UnMergeableException
	{
		Space _result = null;
		
		if(this.A.getX() == _other.A.getX() 
				&&
				this.A.getY() == _other.B.getY()
				&&
				this.B.getX() == _other.B.getX())
		{
			_result = new Space((Point)_other.getA().clone(),(Point)this.B.clone());
		}
		else
			if(
					this.A.getX() == _other.A.getX() 
					&&
					this.B.getY() == _other.A.getY()
					&&
					this.B.getX() == _other.B.getX()
					)
			{
				_result = new Space((Point)this.A.clone(),(Point)_other.getB().clone());
			}
			else
			{
				throw new UnMergeableException("unmergeable by x :" + this.toString() + " | " + _other.toString());
			}
		
		return _result;
	}
	
	public Space mergeByY(Space _other) throws UnMergeableException
	{
		Space _result = null;
		
		if(this.A.getY() == _other.A.getY() 
				&&
				this.A.getX() == _other.B.getX()
				&&
				this.B.getY() == _other.B.getY())
		{
			_result = new Space((Point)_other.getA().clone(),(Point)this.B.clone());
		}
		else
			if(
					this.A.getY() == _other.A.getY() 
					&&
					this.B.getX() == _other.A.getX()
					&&
					this.B.getY() == _other.B.getY()
					)
			{
				_result = new Space((Point)this.A.clone(),(Point)_other.getB().clone());
			}
			else
			{
				throw new UnMergeableException("unmergeable by y :" + this.toString() + " | " + _other.toString());
			}
		
		return _result;
	}
	
	
	
	public boolean isAdjacent(Space _other)
	{
		return isAdjacentByY(_other) || isAdjacentByX(_other);
	}
	
	public boolean isAdjacentByY(Space _other)
	{
		return (
				this.A.getY() == _other.A.getY() 
				&&
				this.A.getX() == _other.B.getX()
				&&
				this.B.getY() == _other.B.getY()
				) 
				||
				(
				this.A.getY() == _other.A.getY() 
				&&
				this.B.getX() == _other.A.getX()
				&&
				this.B.getY() == _other.B.getY()
				);
	}
	
	public boolean isAdjacentByX(Space _other)
	{
		return (
				this.A.getX() == _other.A.getX() 
				&&
				this.A.getY() == _other.B.getY()
				&&
				this.B.getX() == _other.B.getX()
				) 
				||
				(
				this.A.getX() == _other.A.getX() 
				&&
				this.B.getY() == _other.A.getY()
				&&
				this.B.getX() == _other.B.getX()
				);
	}
	
	//---------------------------------------------------------------------------------------------------------
	//----------------------------STATIC METHODS SECTION-------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------
	
	public static List<Space> getSpacesByY(List<Space> _spaces, int _y)
	{
		List<Space> _result = new ArrayList<Space>();
		
		for(Space _s : _spaces)
		{
			if(_s.getA().getY() == _y)
				_result.add(_s);
		}
		
		return _result;
	}
	
	public static List<Space> getSpacesByX(List<Space> _spaces, int _x)
	{
		List<Space> _result = new ArrayList<Space>();
		
		for(Space _s : _spaces)
		{
			if(_s.getA().getX() == _x)
				_result.add(_s);
		}
		
		return _result;
	}
	
	public static List<Space> sortHorizontally(List<Space> _spaces)
	{
		Collections.sort(_spaces, new Comparator<Space>(){
			public int compare(Space o1, Space o2) 
			{
				if(o2.A.getX()>o1.A.getX())
					return 1;
				else
					return 0;
			}});
		return _spaces;
	}
	
	public static List<Space> sortVertically(List<Space> _spaces)
	{
		Collections.sort(_spaces, new Comparator<Space>(){
			public int compare(Space o1, Space o2) 
			{
				if(o2.A.getY()>o1.A.getY())
					return 1;
				else
					return 0;
			}});
		return _spaces;
	}
	
	public static List<Space> explode(List<Space> _spaces, Space _space)
	{
		int _xdiff = _space.getB().getX() - _space.getA().getX();
		int _ydiff = _space.getB().getY() - _space.getA().getY();
		
		if(_xdiff/2 > 0)
		{
			Space _s1 = new Space((Point)_space.getA().clone(),new Point(_space.getA().getX()+(_xdiff/2),_space.getB().getY()));
			Space _s2 = new Space(new Point(_space.getA().getX()+(_xdiff/2),_space.getA().getY()),(Point)_space.getB());
			_spaces.remove(_space);
			_spaces.add(_s1);
			_spaces.add(_s2);
			return explode(explode(_spaces,_s1),_s2);
		}
		else
			if(_ydiff/2 > 0)
			{
				Space _s1 = new Space((Point)_space.getA().clone(),new Point(_space.getB().getX(),_space.getB().getY()-(_ydiff/2)));
				Space _s2 = new Space(new Point(_space.getA().getX(),_space.getB().getY()-(_ydiff/2)),(Point)_space.getB());
				_spaces.remove(_space);
				_spaces.add(_s1);
				_spaces.add(_s2);
				return explode(explode(_spaces,_s1),_s2);
			}
			else
			{
				//nothing to do
				if(!_spaces.contains(_space))
					_spaces.add(_space);
				
				return _spaces;
			}
	}
	
	public static List<Space> merge(List<Space> _spaces) throws UnMergeableException
	{
		Space _space = null, _driver = null;
		List<Space> _result = new ArrayList<Space>();
		Space.sortHorizontally(_spaces);
		Iterator<Space> _it = _spaces.listIterator();
		while(_it.hasNext())
		{
			if(null == _driver)
				_driver = _it.next();
			else
			{
				_space = _it.next();
				if(_driver.isAdjacent(_space))
				{
					_driver = _driver.merge(_space);
				}
				else
				{
					_result.add(_driver);
					_driver = _space;
				}
			}
		}
		
		if(null != _driver)
			_result.add(_driver);

		return _result;
	}
	
	public static List<Rectangle> asRectangles(List<Space> _spaces)
	{
		List<Rectangle> _result = new ArrayList<Rectangle>();
		for(Space _s : _spaces)
			_result.add(_s.asRectangle());
			
		return _result;
		
	}

}

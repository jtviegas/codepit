package com.williamhill.tictactoe;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.williamhill.geometry.discrete.Line;
import com.williamhill.geometry.discrete.Point;

/**
 * Unit tests geometry package
 */
public class GeometryTest 
{
	
	
	@Test
    public void testPointsOne()
    {
		List<Point> _expected = Arrays.asList( new Point(0,1), new Point(1,1), new Point(2,1));
		Line _o = new Line(new Point(0,1),new Point(2,1));
		List<Point> _list = Arrays.asList(_o.getPoints());

    	Assert.assertTrue(_list.containsAll(_expected));
    }
	
	@Test
    public void testPointsTwo()
    {
		List<Point> _expected = Arrays.asList( new Point(0,2), new Point(1,1), new Point(2,0));
		Line _o = new Line(new Point(0,2),new Point(2,0));
		List<Point> _list = Arrays.asList(_o.getPoints());

    	Assert.assertTrue(_list.containsAll(_expected));
    }
	
	@Test
    public void testPointsThree()
    {
		List<Point> _expected = Arrays.asList( new Point(0,1), new Point(1,0));
		Line _o = new Line(new Point(1,0),new Point(0,1));
		List<Point> _list = Arrays.asList(_o.getPoints());

    	Assert.assertTrue(_list.containsAll(_expected));
    }
	
	@Test
    public void testPointsFour()
    {
		List<Point> _expected = Arrays.asList( new Point(0,1));
		Line _o = new Line(new Point(0,1),new Point(0,1));
		List<Point> _list = Arrays.asList(_o.getPoints());

    	Assert.assertTrue(_list.containsAll(_expected));
    }
	
	@Test
    public void testPointsFive()
    {
		List<Point> _expected = Arrays.asList( new Point(0,0),new Point(1,1),new Point(2,2));
		Line _o = new Line(new Point(2,2),new Point(0,0));
		List<Point> _list = Arrays.asList(_o.getPoints());

    	Assert.assertTrue(_list.containsAll(_expected));
    }
	
	@Test
	public void testOverlappingLinesOne()
	{
		List<Line> _expected = Arrays.asList( 
				new Line(new Point(0,0),new Point(2,2)),
				new Line(new Point(2,0),new Point(2,2)),
				new Line(new Point(0,2),new Point(2,2))
				);
		List<Line> _actual = Arrays.asList(Line.getOverlappingLines(new Point(2,2), new Point(2,2)));
		
		Assert.assertTrue(_actual.containsAll(_expected) && _actual.size()==_expected.size());
	}
	
	@Test
	public void testOverlappingLinesTwo()
	{
		List<Line> _expected = Arrays.asList( 
				new Line(new Point(0,0),new Point(2,2)),
				new Line(new Point(0,1),new Point(2,1)),
				new Line(new Point(0,1),new Point(2,1)),
				new Line(new Point(2,0),new Point(0,2))
				);
		List<Line> _actual = Arrays.asList(Line.getOverlappingLines(new Point(1,1), new Point(2,2)));
		
		Assert.assertTrue(_actual.containsAll(_expected) && _actual.size()==_expected.size());
	}
	
	@Test
	public void testOverlappingLinesThree()
	{
		List<Line> _expected = Arrays.asList( 
				new Line(new Point(0,0),new Point(2,2)),
				new Line(new Point(0,0),new Point(2,0)),
				new Line(new Point(0,0),new Point(0,2))
				);
		List<Line> _actual = Arrays.asList(Line.getOverlappingLines(new Point(0,0), new Point(2,2)));
		
		Assert.assertTrue(_actual.containsAll(_expected) && _actual.size()==_expected.size());
	}
	
}

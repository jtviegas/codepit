package org.aprestos.labs.snippets.impl.algo.sedgewick.convexhull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ConvexHullTest {

    @Test
    public void testOne() {
	
	Set<TwoDPoint> p = new HashSet<TwoDPoint>();
	p.add(new TwoDPoint(0.5, 1.5));
	p.add(new TwoDPoint(1.0, 3.0));
	p.add(new TwoDPoint(2.0, 2.0));
	p.add(new TwoDPoint(2.0, 1.0));
	p.add(new TwoDPoint(2.5, 3.5));
	p.add(new TwoDPoint(3.0, 0.5));
	
	ConvexHull o = new ConvexHull();
	TwoDPoint[] expected = new TwoDPoint[]{new TwoDPoint(3, 0.5), new TwoDPoint(2.5, 3.5), new TwoDPoint(1.0, 3.0), new TwoDPoint(0.5, 1.5)};
	TwoDPoint[] actual = o.getEnclosingPolygon(p);
	System.out.println(Arrays.deepToString(actual));
	Assert.assertArrayEquals(expected, actual);
	

    }
    
}

package org.aprestos.labs.snippets.impl.algo.sedgewick.convexhull;

import java.util.Arrays;
import java.util.Set;
import java.util.Stack;

public class ConvexHull {

    public TwoDPoint[] getEnclosingPolygon(Set<TwoDPoint> points){
	
	Stack<TwoDPoint> hull = new Stack<TwoDPoint>();
	TwoDPoint[] p = points.toArray(new TwoDPoint[]{});
	
	Arrays.sort(p, TwoDPoint.PointComparator.Y_ORDER);
	TwoDPoint root = p[0];
	TwoDPoint[] rest = Arrays.copyOfRange(p, 1, p.length);
	Arrays.sort(rest, TwoDPoint.PointComparator.POLAR_ORDER.setRoot(root));
	System.arraycopy(rest, 0, p, 1, rest.length);

	hull.push(p[0]);
	hull.push(p[1]);
	
	for(int i=2; i < p.length; i++){
	    
	    TwoDPoint top = hull.pop();
	    TwoDPoint next = p[i];
	    
	    while( hull.size() > 0 && 0 >= TwoDPoint.ccw(hull.peek(), top, next) )
		    top = hull.pop();
	    
	    hull.push(top);
	    hull.push(p[i]);
	}

	return hull.toArray(new TwoDPoint[]{});
    }
    
}

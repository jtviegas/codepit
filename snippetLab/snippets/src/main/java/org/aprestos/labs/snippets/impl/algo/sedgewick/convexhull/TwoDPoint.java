package org.aprestos.labs.snippets.impl.algo.sedgewick.convexhull;

import java.util.Comparator;

public class TwoDPoint {

    private final double x;
    private final double y;
    
    public TwoDPoint(double x, double y){
	this.x = x;
	this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	long temp;
	temp = Double.doubleToLongBits(x);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	temp = Double.doubleToLongBits(y);
	result = prime * result + (int) (temp ^ (temp >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TwoDPoint other = (TwoDPoint) obj;
	if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
	    return false;
	if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
	    return false;
	return true;
    }

    public String toString(){
	return String.format("(%f,%f)", x, y);
    }
    public static int ccw (TwoDPoint a, TwoDPoint b, TwoDPoint c){
	double determinant = (b.x - a.x)*(c.y-a.y) - (b.y-a.y) * (c.x - a.x);
	if(determinant < 0) return -1;
	else
	    if(determinant > 0) return 1;
	    else return 0;
    }
    
    public static double angle(TwoDPoint a){
	double result = 0;
	double hyp = java.lang.Math.hypot(a.x, a.y);
	result = java.lang.Math.acos(a.x/hyp);
	return result;
    }
    
    public static double relativeAngle(TwoDPoint a, TwoDPoint b){
	double result = 0;
	if(a.equals(b))
	    return 0;
	TwoDPoint vector = new TwoDPoint(a.x-b.x, a.y-b.y);
	double hyp = java.lang.Math.hypot(vector.x, vector.y);
	result = java.lang.Math.acos(vector.x/hyp);
	return result;
    }
    
    
   public interface PointComparatorInterface<T> extends Comparator<T>{
       Comparator<T> setRoot(T root);
   };

    public static enum PointComparator implements PointComparatorInterface<TwoDPoint> {

	Y_ORDER(){
	    public Comparator<TwoDPoint> setRoot(TwoDPoint root){
		return this;
	    }
	    public int compare(TwoDPoint o1, TwoDPoint o2) {
		return Double.compare(o1.y, o2.y);
	    }},
	   POLAR_ORDER(){
		
		TwoDPoint root ;
		public Comparator<TwoDPoint> setRoot(TwoDPoint root){
		    this.root = root;
		    return this;
		}
		public int compare(TwoDPoint o1, TwoDPoint o2) {
		    return  (-1) * Double.compare(relativeAngle(root, o1), relativeAngle(root, o2));
		}},
    }
    
}

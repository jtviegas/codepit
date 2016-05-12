package org.challenges.odobo.interfaces;

import java.util.List;

import org.challenges.odobo.exceptions.ChallengeException;
import org.challenges.odobo.model.Rectangle;
import org.challenges.odobo.model.Space;

public interface Challenge {

	/**
	 * @return the numRects
	 */
	public abstract int getNumRects();

	/**
	 * @param numRects the numRects to set
	 */
	public abstract void setNumRects(int numRects);

	/**
	 * @return the sourceRectangles
	 */
	public abstract List<Rectangle> getSourceRectangles();

	/**
	 * @param sourceRectangles the sourceRectangles to set
	 */
	public abstract void setSourceRectangles(List<Rectangle> sourceRectangles);

	/**
	 * @return the rectangles
	 */
	public abstract List<Rectangle> getRectangles();

	/**
	 * @param rectangles the rectangles to set
	 */
	public abstract void setRectangles(List<Rectangle> rectangles);

	/**
	 * Challenge input and output in JSON format
	 *  
	 * @return
	 * @throws ChallengeException
	 */
	public String toJSON() throws ChallengeException;
	
	/**
	 * Execute the challenge
	 * 
	 * @throws ChallengeException
	 */
	public void execute() throws ChallengeException;
	
	/*
	 * generate first space version - source space;
	 */
	public List<Space> generate(int _num);
	
	/**
	 * regenerate the source space in different rationale
	 * 
	 * @param _spaces
	 * @return
	 * @throws ChallengeException
	 */
	public List<Space> regenerate(List<Space> _spaces) throws ChallengeException;
	
}
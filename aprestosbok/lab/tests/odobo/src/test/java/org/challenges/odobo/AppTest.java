package org.challenges.odobo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.challenges.odobo.exceptions.ChallengeException;
import org.challenges.odobo.impl.ChallengeImpl;
import org.challenges.odobo.interfaces.Challenge;
import org.challenges.odobo.model.Point;
import org.challenges.odobo.model.Space;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;



public class AppTest 
{
    @Test
    public void testExplode()
    {
    	List<Space> _spaces = new ArrayList<Space>();
    	
    	Space _s1 = new Space(new Point(0,1),new Point(1,6));
    	Space.explode(_spaces, _s1);
    	
    	Assert.assertEquals(5, _spaces.size());
    	
    	Assert.assertTrue(_spaces.contains(new Space(new Point(0,2),new Point(1,3))));
    	Assert.assertTrue(_spaces.contains(new Space(new Point(0,1),new Point(1,2))));
    	Assert.assertTrue(_spaces.contains(new Space(new Point(0,3),new Point(1,4))));
    	Assert.assertTrue(_spaces.contains(new Space(new Point(0,4),new Point(1,5))));
    	Assert.assertTrue(_spaces.contains(new Space(new Point(0,5),new Point(1,6))));
    	
    }
    
    @Test
    public void testGenerate()
    {
    	Challenge _o = new ChallengeImpl();
    	List<Space> _result =_o.generate(3);
    	Assert.assertEquals(3, _result.size());
    	
    }
    
    @Test
    public void testRegenerate() throws ChallengeException
    {
    	List<Space> _result = new ArrayList<Space>();
    	List<Space> _spaces = new ArrayList<Space>();
    	
    	_spaces.add(new Space(new Point(0,0),new Point(1,1)));
    	_spaces.add(new Space(new Point(1,0),new Point(2,1)));
    	_spaces.add(new Space(new Point(1,1),new Point(2,2)));
    	_spaces.add(new Space(new Point(2,1),new Point(3,2)));
    	_spaces.add(new Space(new Point(2,2),new Point(3,3)));
    	_spaces.add(new Space(new Point(4,2),new Point(5,3)));
    	
    	_spaces.add(new Space(new Point(1,3),new Point(2,4)));
    	_spaces.add(new Space(new Point(1,4),new Point(2,5)));
    	_spaces.add(new Space(new Point(2,3),new Point(3,4)));
    	_spaces.add(new Space(new Point(2,4),new Point(3,5)));
    	
    	
    	Challenge _o = new ChallengeImpl();
    	_result = _o.regenerate(_spaces);
    	
    	Assert.assertEquals(5, _result.size());
    	
    	Assert.assertTrue(_result.contains(new Space(new Point(0,0),new Point(2,1))));
    	Assert.assertTrue(_result.contains(new Space(new Point(1,1),new Point(3,2))));
    	Assert.assertTrue(_result.contains(new Space(new Point(2,2),new Point(3,3))));
    	Assert.assertTrue(_result.contains(new Space(new Point(4,2),new Point(5,3))));
    	Assert.assertTrue(_result.contains(new Space(new Point(1,3),new Point(3,5))));
    	
    }
    
    @Test
    public void testMapper() throws JsonGenerationException, JsonMappingException, IOException
    {
    	ObjectMapper _mapper =  new ObjectMapper();
    	
    	Challenge _o = new ChallengeImpl();
    	List<Space> _result =_o.generate(3);
    	Assert.assertEquals(3, _result.size());
    	
    	_mapper.writeValueAsString(_result);
    	
    }
    
    @Test
    public void testRegenerateExample() throws ChallengeException
    {
    	List<Space> _result = new ArrayList<Space>();
    	List<Space> _spaces = new ArrayList<Space>();
    	
    	_spaces.add(new Space(new Point(0,50),new Point(50,150)));
    	_spaces.add(new Space(new Point(50,63),new Point(90,150)));
    	_spaces.add(new Space(new Point(90,84),new Point(160,150)));
    	_spaces.add(new Space(new Point(160,4),new Point(205,150)));
    	_spaces.add(new Space(new Point(205,96),new Point(235,150)));
    	
    	
    	Challenge _o = new ChallengeImpl();
    	_result = _o.regenerate(_spaces);
    	
    	Assert.assertEquals(5, _result.size());
    	
    	Assert.assertTrue(_result.contains(new Space(new Point(0,96),new Point(235,150))));
    	Assert.assertTrue(_result.contains(new Space(new Point(0,84),new Point(205,96))));
    	Assert.assertTrue(_result.contains(new Space(new Point(0,63),new Point(90,84))));
    	Assert.assertTrue(_result.contains(new Space(new Point(0,50),new Point(50,63))));
    	Assert.assertTrue(_result.contains(new Space(new Point(160,4),new Point(205,84))));
    	
    }
   
}

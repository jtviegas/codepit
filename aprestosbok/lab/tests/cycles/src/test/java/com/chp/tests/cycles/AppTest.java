package com.chp.tests.cycles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void testOne() throws Exception 
    {
    	BufferedReader _in = null;
    	
			try {
				_in = new BufferedReader(new InputStreamReader(AppTest.class.getResourceAsStream("/one.data")));
				PatternFinder _p = new PatternFinderImpl();

				if(_in.ready())
					Assert.assertEquals("631", _p.find(_in.readLine().trim()));
			} 
			catch (IOException e) 
			{
				e.printStackTrace(System.out);
				throw new Exception(e); 
			}
			finally
			{
				if(null != _in)
					try {_in.close();} catch (IOException e) {/*forget about this exception*/}
				
				_in = null;
			}
			
    }
    
    @Test
    public void testTwo() throws Exception 
    {
    	BufferedReader _in = null;
    	
			try 
			{
				_in = new BufferedReader(new InputStreamReader(AppTest.class.getResourceAsStream("/two.data")));
				PatternFinder _p = new PatternFinderImpl();

				if(_in.ready())
					Assert.assertEquals("678", _p.find(_in.readLine().trim()));
			} 
			catch (IOException e) 
			{
				e.printStackTrace(System.out);
				throw new Exception(e); 
			}
			finally
			{
				if(null != _in)
					try {_in.close();} catch (IOException e) {/*forget about this exception*/}
				
				_in = null;
			}
			
    }
    
    @Test
    public void testThree() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("6 3 1", _p.find("2 6 3 1 6 3 1"));
    }
    
    @Test
    public void testFour() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("",_p.find("29 28 27 26"));
    }
    
    @Test
    public void testFive() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("10 15",_p.find("15 10 15 10 15"));
    }
    
    @Test
    public void testSix() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("20 25 30 35",_p.find("1 5 10 15 20 25 30 35 20 25 30 35"));
    }
    
    @Test
    public void testSeven() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("5",_p.find("5 5 5 5 5 5 5"));
    }
    
    @Test
    public void testEight() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("7 1",_p.find("7 1 7 1 7 1 7 1"));
    }
    
    @Test
    public void testNine() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("",_p.find("0 2 6 3 1 6 3 1 9 8 7"));
    }
    
    
    @Test
    public void testTen() throws Exception 
    {
    	PatternFinder _p = new PatternFinderImpl();
		Assert.assertEquals("23 4 56", _p.find("21 49 23 4 56 23 4 56 23 4 56"));
    }
    
    
}

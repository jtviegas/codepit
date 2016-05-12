/*
 * HistoryManager.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.webdev.gwtapplications.client.snippets.historymanager;


/**
 * 
 */
public class HistoryManager
{
    
    private String[] tokens;
    private static final int DEFAULT_HISTORY_LENGTH = 10;
    private int index=-1;
    
    public HistoryManager()
    {
	init(DEFAULT_HISTORY_LENGTH);
    }
    
    public HistoryManager(int historyLength)
    {
	init(historyLength);
    }
    
    private void init(int length)
    {
	tokens = new String[length];
    }
    

    public void onHistoryChanged(String historyToken)
    {
	if(historyToken.equals(findPrevious()))
	{
	    //we are going backward
	    getPrevious();
	}
	else if(historyToken.equals(findNext()))
	{
	    //we are going forward
	    getNext();
	}
	else
	{
	    if(index < tokens.length - 1)
		    index++;
		else
		    index = 0;
		
		tokens[index] = historyToken;    
	}
	
	System.out.println("index is now " + index);
	
    }
    
    public String findPrevious()
    {
	String result = null;
	
	if(-1 < index)
	{
	
	    int newIndex=-1;
		
		if(index == 0)
		{
		    //for all other indexes, backwards, 
		    //check if they convey a value
		    for(int i = tokens.length-1 ; i > 0 ; i--)
		    {
			if(null != tokens[i])
			{    
			    newIndex = i;
			    break;
			}
		    }
		    
		    if(-1 < newIndex)
		    {
			//found a non-null value
			//handle back the token
			result = tokens[newIndex];
		    }
		}
		else
		{
		    //we have already been there
		    //so move backwards
		    newIndex = index;
		    result = tokens[--newIndex];
		}
	    
	}
	
	
	return result;
	
    }
    
    public String findNext()
    {
	String result = null;
	int newIndex = index;
	if(newIndex == tokens.length - 1)
	    newIndex = -1;
	
	result = tokens[++newIndex];
	
	return result;
    }
    
    public String getPrevious()
    {
	String result = null;
	
	int newIndex=-1;
	
	if(index == 0)
	{
	    //for all other indexes, backwards, 
	    //check if they convey a value
	    for(int i = tokens.length-1 ; i > 0 ; i--)
	    {
		if(null != tokens[i])
		{    
		    newIndex = i;
		    break;
		}
	    }
	    
	    if(-1 < newIndex)
	    {
		//found a non-null value
		//handle back the token
		index = newIndex;
		result = tokens[index];
	    }
	}
	else
	{
	    //we have already been there
	    //so move backwards
	    result = tokens[--index];
	}
	
	return result;
	
    }
    
    public String getNext()
    {
	String result = null;
	
	if(index == tokens.length - 1)
	    index = -1;
	
	result = tokens[++index];
	
	return result;
    }

    

}

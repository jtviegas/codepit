/*
 * TextFileFacade.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class TextFileFacade
{

    private List<String> lines = new ArrayList<String>();
    private File file;
    /**
     * 
     */

    public TextFileFacade(String path) throws FileNotFoundException
    {
	file = new File(path);
	
	if(!file.exists())
	    throw new FileNotFoundException();
    }
    
    public void read() throws FileNotFoundException,IOException
    {
	lines = readFile(file);
    }

    public boolean isDirty() throws FileNotFoundException,IOException
    {
	List<String> original = readFile(file);
	return !original.equals(lines);
    }
    
    public void replaceRow(String pattern,String replacement,Boolean multipleReplacements)
    {
	List<String> linesFound = findLines(pattern);
	if(linesFound.size() < 1)
	    return;
	
	int index=0;
	
	if(!multipleReplacements)
	{
	    linesFound.set(index, replacement);
	}
	else
	{
	    for(String line:linesFound)
	    {
		linesFound.set(index++, replacement);
	    }
	}
    }
    
    private List<String> findLines(String pattern)
    {
	Pattern p = 
            Pattern.compile(pattern);
	Matcher matcher = null;
	List<String> result = new ArrayList<String>();
	
	for(String line:lines)
	{
	    matcher = p.matcher(line);
	    if(matcher.find())
		result.add(line);
	}
	
	return result;
    }
    
    private List<String> readFile(File f) throws FileNotFoundException,IOException
    {
	List<String> result = new ArrayList<String>();
	BufferedReader br = new BufferedReader(new FileReader(f));

	String line = br.readLine();
	
	while(null != line)
	{
	    result.add(line);
	    line = br.readLine();
	}
	
	return result;
    }
}

package org.aprestos.labs.snippets.impl.lang.string.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aprestos.labs.snippets.interfaces.Snippet;
import org.springframework.stereotype.Component;

//@Component("snippet")
public class SnippetImpl implements Snippet {

	String msg = "pattern: %s   text: %s   matches? %s";
	String patternStr = "[a-zA-Z0-9_]{3,10}";
	Pattern pattern = Pattern.compile(patternStr);
	String[] texts = new String[]{
			"12",
			"ab","ab1","_ab", "as_d",
			"_)(*",
			"adfasf___"
	};
	 
	public void go() throws Exception {
		Matcher matcher = null;
		
		for(String s:texts)
			 System.out.println(String.format(msg, patternStr, s, Boolean.toString(pattern.matcher(s).find())));
		
	}

}

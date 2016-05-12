/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class EMailValidator implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        
    }

    private boolean validateRFC822address(String arg0)
    {
        String patternStr = 
                "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	Pattern pattern = Pattern.compile(patternStr);

	Matcher matcher = pattern.matcher(arg0);
	boolean matchFound = matcher.matches();
                
        return matchFound;
    }
    
}

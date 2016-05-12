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
public class RegExpDigressions implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        String[] strings = new String[]{"and-s234dde-21", "a23nd-2d", "-der-1", "asde323erg3", "asdeerg-"};
        
        for(String s : strings)
        {

            Pattern p = Pattern.compile("(.*[-])([0-9]+)$");
            Matcher match = p.matcher(s);
            while(match.find())
            {
                int x = Integer.parseInt(match.group(2)) + 1;
                System.out.println("found and added one-> " + match.group(1) + x);    
            }

            
            
            
            //System.out.println(match.replaceAll("XXX"));
        }
        
    }

}

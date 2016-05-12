/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;

/**
 *
 * @author jtviegas
 */
public class EnumerationGenerics implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        EnumReader<Enum> reader = new EnumReader<Enum>();
        
        for(EnumOne one:EnumOne.values())
            reader.go(one);
        
        for(EnumTwo two:EnumTwo.values())
            reader.go(two);
        
        for(EnumThree three:EnumThree.values())
            reader.go(three);
        
        EnumReader<EnumOne> reader2 = new EnumReader<EnumOne>();
        for(EnumOne one:EnumOne.values())
            reader2.go(one);
        
    }

    private class EnumReader<E extends Enum>
    {
        
        public void go(E e)
        {
            System.out.println(e.getClass().getName() + e.toString());
        }
    }

    private enum EnumOne {oneA, oneB, oneC }
    private enum EnumTwo{twoA, twoB, twoC}
    private enum EnumThree{threeA, threeB, threeC}

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.heritagedigressions;

/**
 *
 * @author jtviegas
 */
public class Model 
{
    private String name;
    
    public Model()
    {
        name = this.getClass().getName();
    }
    
    public String getName()
    {
        return name;
    }
}

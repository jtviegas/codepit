/**
 * Save.java
 * copyright aprestos.org, 2009.
 */
package snippetlab.snippets.gui.mvc.actions;

import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * 
 */
public class Action extends ActionEvent
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected static final Random random = new Random();
    /**
     * @param source
     * @param id
     */
    public Action(Object source, String command)
    {
	super(source, random.nextInt(), command);
    }


}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.commonschain;

import org.apache.commons.chain.Command;

/**
 *
 * @author jtviegas
 */
public interface Catalog extends org.apache.commons.chain.Catalog
{
	public void addCommand(Enum<?> name, Command command);
	public Command getCommand(Enum<?> name);

}

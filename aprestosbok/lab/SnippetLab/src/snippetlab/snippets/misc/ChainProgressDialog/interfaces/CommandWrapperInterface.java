/*
 * CommandStatusWrapperInterface.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.misc.ChainProgressDialog.interfaces;

import org.apache.commons.chain.Command;

public interface CommandWrapperInterface
{

	public Command getCommand();

	public void setCommand(Command command);

	public int getStatus();

	public void setStatus(int status);

}

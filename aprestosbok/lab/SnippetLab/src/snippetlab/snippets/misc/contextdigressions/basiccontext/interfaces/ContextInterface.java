/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.contextdigressions.basiccontext.interfaces;

/**
 *
 * @author jtviegas
 */
public interface ContextInterface 
{
	/**
	 * Adds data to the context, registering it with the given key. If the key
	 * already exists in the context nothing is done.
	 * 
	 * @param key
	 *            The data key.
	 * @param data
	 *            The data.
	 */
	public abstract void put(String key, Object data);

	/**
	 * Returns the data registered in the context with the given key. Does not
	 * remove it from the context. If the key is not found, returns null.
	 * 
	 * @param key
	 *            The data key.
	 * @return The data for the key or null if the key is not known.
	 */
	public abstract Object get(String key);

	/**
	 * Removes the data registered with the given key from the context, and
	 * returns it. If the key is not found, returns null.
	 * 
	 * @param key
	 *            The data key.
	 * @return The data for the key or null if the key is not known.
	 */
	public abstract Object getAndRemove(String key);
}

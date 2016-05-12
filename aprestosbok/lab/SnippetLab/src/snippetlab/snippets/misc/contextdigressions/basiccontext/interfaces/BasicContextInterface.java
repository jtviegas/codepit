/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.contextdigressions.basiccontext.interfaces;

import snippetlab.snippets.misc.contextdigressions.basiccontext.*;

import org.apache.commons.chain.Context;

/**
 *
 * @author jtviegas
 */
public interface BasicContextInterface extends Context, ContextInterface
{
    public boolean containsKey(Enum<?> key);
    public boolean containsKey(String key);
    
    public Object get(Enum<?> key);
    public Object getAndRemove(Enum<?> key);
    public Object getAndRemove(Object key);
    public Object put(Enum<?> key, Object value);
    public Object remove(String key);
    public Object remove(Enum<?> key);
                  
}

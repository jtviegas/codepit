/*
 * BasicContextInterface.java
 */

package org.aprestos.code.bok.common.interfaces;

import org.apache.commons.chain.Context;

/**
 * BasicContextInterface is a customised flavour of the Context
 * implementation from the apache commons Chain library.
 * One of the extensions provided is the option for specifying
 * keys as enums.
 * 
 */
public interface BasicContextInterface extends Context
{

    /**
     * Checks presence of a value associated with an enum key.
     * @param key	the key associated with a value.
     * @return		<code>true</code> if the key-value pair is found
     * 			in the context.
     */
    boolean containsKey(Enum<?> key);

    /**
     * Checks presence of a value associated with a String key.
     * @param key	the key associated with a value.
     * @return		<code>true</code> if the key-value pair is found
     * 			in the context.
     */
    boolean containsKey(String key);

    
    Object get(String key);

    Object get(Enum<?> key);

    Object getAndRemove(String key);

    Object getAndRemove(Enum<?> key);

    Object getAndRemove(Object key);

    Object put(String key, Object value);

    Object put(Enum<?> key, Object value);

    Object remove(String key);

    Object remove(Enum<?> key);

}

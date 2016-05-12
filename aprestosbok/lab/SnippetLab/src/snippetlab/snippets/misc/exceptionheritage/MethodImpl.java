/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.exceptionheritage;

/**
 *
 * @author jtviegas
 */
public class MethodImpl implements MethodInterface
{

    public void method() throws SonException {
        throw new SonException();
    }

}

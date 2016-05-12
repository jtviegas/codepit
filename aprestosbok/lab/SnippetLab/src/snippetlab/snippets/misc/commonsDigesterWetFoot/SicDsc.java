/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.commonsDigesterWetFoot;

/**
 *
 * @author jtviegas
 */
public class SicDsc extends SicRange
{
    public void setBegin(String begin)
    {
        super.setBegin(new SicCode(begin));
    }
    
    public void setEnd(String end)
    {
        super.setEnd(new SicCode(end));
    }
    
    
}

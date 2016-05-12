/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import snippetlab.snippets.AbstractSnippet;
import snippetlab.snippets.misc.PanelSizeDigressions.CustomDialog;
import snippetlab.snippets.misc.PanelSizeDigressions.ModuleMockImpl;

/**
 *
 * @author jtviegas
 */
public class PanelSizeDigressions extends AbstractSnippet
{

    @Override
    public void method() 
    {
       CustomDialog cd = new CustomDialog();
       cd.start(new ModuleMockImpl());
       cd.setVisibleToUser();
       cd = null;
    }


}

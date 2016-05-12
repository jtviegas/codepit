/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.heritagedigressions.Model;
import snippetlab.snippets.misc.heritagedigressions.ModelDaughter;
import snippetlab.snippets.misc.heritagedigressions.ModelSon;

/**
 *
 * @author jtviegas
 */
public class HeritageDigressions implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame) 
    {
        
        List<Model> models = Arrays.asList(new Model(), new ModelSon(), new ModelDaughter());
        
        for(Model model:models)
            System.out.println("~~~~-> " + model.getName());
        
    }
    
}

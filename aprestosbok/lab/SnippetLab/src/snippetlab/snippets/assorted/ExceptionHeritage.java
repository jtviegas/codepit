/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.assorted;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.misc.exceptionheritage.DaughterException;
import snippetlab.snippets.misc.exceptionheritage.FatherException;
import snippetlab.snippets.misc.exceptionheritage.GrandFatherException;
import snippetlab.snippets.misc.exceptionheritage.MethodImpl;
import snippetlab.snippets.misc.exceptionheritage.MethodInterface;
import snippetlab.snippets.misc.exceptionheritage.SonException;

/**
 *
 * @author jtviegas
 */
public class ExceptionHeritage implements SnippetInterface
{

    public void go(JPanel panel, JFrame frame)
    {
            try 
            {
                handlerMethod(0);
            } catch (Exception ex) 
            {
                Logger.getLogger(ExceptionHeritage.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("found an exception from exception");
            }

            try {
                handlerMethod(1);
            } catch (Exception ex) {
                if (ex instanceof GrandFatherException) {
                    System.out.println("found a GrandFatherException from exception");
                }
                Logger.getLogger(ExceptionHeritage.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                handlerMethod(2);
            } catch (Exception ex) {
                if (ex instanceof FatherException) {
                    System.out.println("found a FatherException from exception");
                }
                Logger.getLogger(ExceptionHeritage.class.getName()).log(Level.SEVERE, null, ex);
            }

        try 
        {
            MethodInterface method = new MethodImpl();
            method.method();
        } catch (SonException ex) {
            Logger.getLogger(ExceptionHeritage.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("found a SonException from exception");
        }
        catch(Exception xx)
        {
            System.out.println("found an Exception from exception");    
        }
        
        
    }

    private void grandFatherMethod() throws GrandFatherException
    {
        throw new GrandFatherException();
    }
    
    private void fatherMethod() throws FatherException
    {
        throw new FatherException();
    }
 
    private void sonMethod() throws SonException
    {
        throw new SonException();
    }
    
    private void daughterMethod() throws DaughterException
    {
        throw new DaughterException();
    }
    
    private void handlerMethod(int i) throws Exception
    {
        if(i == 0)
        {
            throw new Exception("0");
        }
        if(i == 1)
        {
            grandFatherMethod();
        }
        if(i == 2)
        {
            fatherMethod();
        }
        if(i == 3)
        {
            sonMethod();
        }
        if(i == 4)
        {
            daughterMethod();
        }
    }
    
}

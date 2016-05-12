package org.aprestos.webdev.gwtapplications.client;

import org.aprestos.webdev.gwtapplications.client.interfaces.SnippetInterface;
import org.aprestos.webdev.gwtapplications.client.snippets.Hyperlinks;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GWTApplications implements EntryPoint 
{

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() 
  {
    
      Panel panel = new FlowPanel();
      
      
      SnippetInterface snippet = new Hyperlinks();
      snippet.go(panel);
      
      RootPanel.get().add(panel);

    
  }

  
  
}

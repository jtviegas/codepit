/*
 * Hyperlinks.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package org.aprestos.webdev.gwtapplications.client.snippets;

import org.aprestos.webdev.gwtapplications.client.interfaces.SnippetInterface;
import org.aprestos.webdev.gwtapplications.client.snippets.historymanager.HistoryManager;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.HistoryListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;


/**
 * 
 */
public class Hyperlinks implements HistoryListener, SnippetInterface
{
    
    
    private Label previous = new Label();
    private Label next = new Label();
    
    private HistoryManager historyManager = new HistoryManager();
    
    public Hyperlinks(){}
    /**
     * @see org.aprestos.webdev.gwtapplications.client.interfaces.SnippetInterface#go()
     */
    public void go(Panel panel)
    {

	panel.add(new Hyperlink("go A","goA"));
	panel.add(new Hyperlink("go B","goB"));
	panel.add(new Hyperlink("go C","goC"));
	
	
	panel.add(previous);
	panel.add(next);

	    
	History.addHistoryListener(this);
	    
	HTML link = new HTML("<a href='http://www.amazon.com'>amazon</a>");
	panel.add(link);

    }
    /**
     * @see com.google.gwt.user.client.HistoryListener#onHistoryChanged(java.lang.String)
     */
    public void onHistoryChanged(String historyToken)
    {
	historyManager.onHistoryChanged(historyToken);
	
	
	previous.setText("previous - " + historyManager.findPrevious());
	next.setText("next - " + historyManager.findNext());
	
    }
    


}

/*
 * CardDigression.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.layouts.card;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import snippetlab.interfaces.SnippetInterface;

public class CardDigression implements SnippetInterface
{
	//snippetlab.snippets.swing.layouts.card.CardDigression
	@Override
	public void go(JPanel panel, JFrame frame)
	{
		panel.add(new CardHost(),BorderLayout.CENTER);
	}

}

/*
 * SwingXDigressions.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.border.DropShadowBorder;

import snippetlab.interfaces.SnippetInterface;

public class SwingXDigressions implements SnippetInterface
{

	public SwingXDigressions()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void go(JPanel panel, JFrame frame)
	{
		JXHeader header = new JXHeader("title","just a description",new ImageIcon("png/doc.png"));
		panel.add(header, BorderLayout.NORTH);
		
		
		JXPanel p = new JXPanel();
		p.setBorder(new DropShadowBorder());
		
	}


}

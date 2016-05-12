/*
 * SwingFaceDigression.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.face;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import snippetlab.interfaces.SnippetInterface;
import snippetlab.snippets.swing.face.model.Model;

/**
 * 
 */
public class SwingFaceDigression implements SnippetInterface
{

	@Override
	public void go(JPanel panel, JFrame frame)
	{
		JButton goRWFace=new JButton("Test RW face rationale");
		goRWFace.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					System.out.println("going in gui session");
					GuiDataSession gds = new GuiDataSession(getAModel(),true);
					gds.init();
					gds.go();
					System.out.println("going out gui session");
				}
				catch(Exception x)
				{
					x.printStackTrace();
				}
				
				
			}
			
		});
		
		JButton goROFace=new JButton("Test RO face rationale");
		goROFace.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					System.out.println("going in gui session");
					GuiDataSession gds = new GuiDataSession(getAModel(),false);
					gds.init();
					gds.go();
					System.out.println("going out gui session");
				}
				catch(Exception x)
				{
					x.printStackTrace();
				}
				
				
			}
			
		});
		
		JPanel p=new JPanel(new FlowLayout());
		
		p.add(goRWFace);
		p.add(goROFace);
		
		panel.add(p, BorderLayout.SOUTH);

	}
	
	
	private Model getAModel()
	{
		Model model = new Model();
		
		model.setCode(1234);
		model.setName("one hundred twenty three and four");
		model.setDescription("yet another model for testing purposes");
		
		
		model.setEnabled(true);
		model.setOn(false);
//		model.setAttribute("attribute F");
//		model.setListItems(Arrays.asList("item A", "item D"));
		
		return model;
	}
}

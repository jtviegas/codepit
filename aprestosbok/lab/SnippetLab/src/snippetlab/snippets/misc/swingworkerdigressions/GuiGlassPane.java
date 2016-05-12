/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.swingworkerdigressions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author jtviegas
 */
public class GuiGlassPane extends JPanel implements MouseListener
{

	private static final long serialVersionUID = 1L;
	
	public GuiGlassPane()
	{

		this.setOpaque(false);
		this.addMouseListener(this);

	}
	

	@Override
	public void mouseClicked(MouseEvent e)
	{

		System.out.println("glasspane is active and freezing.");

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}
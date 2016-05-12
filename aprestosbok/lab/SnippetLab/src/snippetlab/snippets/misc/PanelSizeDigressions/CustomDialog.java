/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.PanelSizeDigressions;

import com.jgoodies.forms.layout.CellConstraints;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 *
 * @author jtviegas
 */
public class CustomDialog extends JDialog
{

	private CellConstraints cc;

        private PanelGuiContainerImpl panel = new PanelGuiContainerImpl();
        
        
	
	public CustomDialog() 
	{
		super((JFrame)null, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		initDialog();
		
	}
        
        public void start(ModuleMockInterface mm)
        {
            mm.start(panel);
        }
	
	/*
	 * this is the method that must me called to put dialog instances visible to
	 * user
	 */
	/**
	 * Atribui valor ao atributo VisibleToUser.
	 */
	public void setVisibleToUser()
	{

		pack();
		
		setDialogMinimumSize();

		setResizable(true);
		setVisible(true);
	}





	public GuiContainerInterface getGuiContainerComponent()
	{
		return panel;
	}
	


	/**
	 * Atribui valor ao atributo DialogMinimumSize.
	 */
	private void setDialogMinimumSize()
	{
		final Dimension minimumSize = getSize();

		setMinimumSize(minimumSize);

		ComponentAdapter componentAdapter = new ComponentAdapter()
		{
            @Override
			public void componentResized(ComponentEvent ev)
			{
				Window tmp = (Window) ev.getSource();
				if (tmp.getWidth() < minimumSize.width
						|| tmp.getHeight() < minimumSize.height)
				{
					tmp.setSize(minimumSize.width, minimumSize.height);
				}
			}
		};

		addComponentListener(componentAdapter);
	}

	/*
	 * @throws Exception
	 */
	/**
	 * Aqui deverá ser colocada a descrição do método.
	 * 
	 * @exception Exception
	 *                Descrição da excepção.
	 */
	private final void initDialog() 
	{

            
		JScrollPane sp = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setViewportView(panel);
		
		this.setContentPane(sp);
		
		
	}

	
	

	
}


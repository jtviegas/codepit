/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package snippetlab.snippets.misc.PanelSizeDigressions;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author jtviegas
 */
public class PanelGuiContainerImpl extends JPanel implements GuiContainerInterface
{
	private CellConstraints cc = new CellConstraints();

	/**
	 * 
	 */
	public PanelGuiContainerImpl()
	{
          super();  
          FormLayout layout = new FormLayout(
                //columns
                "2dlu, " + "FILL:pref:grow, " + "2dlu"
                ,// rows
		"2dlu, " + "FILL:pref:grow, " + 
                "2dlu, " 
                + "pref, " + // 4 - Buttons Panel
                "2dlu");
            
                
            setLayout(layout);
            initComponents();
	}

	/**
	 * @see sicacpdistribution.common.interfaces.GUIContainerInterface#add(javax.swing.JComponent, sicacpdistribution.common.interfaces.MMInterface)
	 */
	public void add(JComponent component)
	{
                add(component, cc.xy(2, 2));
	}

	/**
	 * @see sicacpdistribution.common.interfaces.GUIContainerInterface#remove(javax.swing.JComponent)
	 */
	public void remove(JComponent component)
	{
		remove(component);
		revalidate();
	}

	public JComponent getContainer()
	{
		return this;
	}
        
        private void initComponents()
        {
            

		// --------------------------------------------------------------

		JPanel buttonPanel = new JPanel();

		JButton ok = new JButton("ok");
		JButton cancel = new JButton("cancel");
		JButton apply = new JButton("help");
                
		String buttonHeight = "40";
		String buttonWidth = "60";
		String buttonGapSpace = "30";
		
		FormLayout buttonLayout = new FormLayout("FILL:pref:grow, " + buttonWidth + "dlu, " +
				// 2 -
						// ok
				buttonGapSpace + "dlu," + buttonWidth + "dlu, " +
						// 4 - apply
				buttonGapSpace + "dlu," + buttonWidth + "dlu, "
				// 6 - cancel
						, buttonHeight);
		
		buttonPanel.setLayout(buttonLayout);

		
		
		
			buttonPanel.add(ok, cc.xy(2, 1));
			buttonPanel.add(apply, cc.xy(4, 1));
			buttonPanel.add(cancel, cc.xy(6, 1));
		// --------------------------------------------------------------

		add(buttonPanel, cc.xy(2, 4));

        }
        
	
}

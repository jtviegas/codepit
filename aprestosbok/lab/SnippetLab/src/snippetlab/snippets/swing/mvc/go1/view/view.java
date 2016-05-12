package snippetlab.snippets.swing.mvc.go1.view;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;



/**
 * @author José Pesqueira Mendes
 */
public class view extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public view() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		ln_name = new JLabel();
		tf_name = new JTextField();
		lb_gender = new JLabel();
		cb_gender = new JComboBox();
		lb_colors = new JLabel();
		JScrollPane scrollPane1 = new JScrollPane();
		jl_colors = new JList();
		ck_on = new JCheckBox();
		lb_text = new JLabel();
		JScrollPane scrollPane2 = new JScrollPane();
		tp_text = new JTextPane();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setLayout(new FormLayout(
			new ColumnSpec[] {
				new ColumnSpec(Sizes.dluX(110)),
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				new ColumnSpec(Sizes.dluX(149))
			},
			new RowSpec[] {
				new RowSpec(Sizes.dluY(15)),
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.dluY(15), FormSpec.NO_GROW),
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.dluY(50), 0.5),
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(Sizes.dluY(15)),
				FormFactory.LINE_GAP_ROWSPEC,
				new RowSpec(RowSpec.FILL, Sizes.dluY(50), 0.5)
			}));

		//---- ln_name ----
		ln_name.setText("name");
		add(ln_name, cc.xy(1, 1));
		add(tf_name, cc.xy(3, 1));

		//---- lb_gender ----
		lb_gender.setText("gender");
		add(lb_gender, cc.xy(1, 3));
		add(cb_gender, cc.xy(3, 3));

		//---- lb_colors ----
		lb_colors.setText("colors loved");
		lb_colors.setVerticalAlignment(SwingConstants.TOP);
		add(lb_colors, cc.xy(1, 5));

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(jl_colors);
		}
		add(scrollPane1, cc.xy(3, 5));

		//---- ck_on ----
		ck_on.setText("on");
		add(ck_on, cc.xy(3, 7));

		//---- lb_text ----
		lb_text.setText("text");
		lb_text.setVerticalAlignment(SwingConstants.TOP);
		add(lb_text, cc.xy(1, 9));

		//======== scrollPane2 ========
		{
			scrollPane2.setViewportView(tp_text);
		}
		add(scrollPane2, cc.xy(3, 9));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel ln_name;
	private JTextField tf_name;
	private JLabel lb_gender;
	private JComboBox cb_gender;
	private JLabel lb_colors;
	private JList jl_colors;
	private JCheckBox ck_on;
	private JLabel lb_text;
	private JTextPane tp_text;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
	
	
	
}

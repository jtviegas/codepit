/*
 * FramePopupMenu.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.scrollpopupmenu;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class FramePopupMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton btnPopup = null;
	private JTextField txtNumElem = null;
	private JLabel lblNumElem = null;
 
	private JButton getBtnPopup() {
		if (btnPopup == null) {
			btnPopup = new JButton();
			btnPopup.setText("Ver menú popup");
			btnPopup.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ScrollPopupMenu mnu = new ScrollPopupMenu(FramePopupMenu.this.getGraphicsConfiguration());
					int n = Integer.parseInt(getTxtNumElem().getText());
					for (int i=0;i<n;i++)
						mnu.add(new JButton("Menú " + (i+1)));
					mnu.show(btnPopup, btnPopup.getWidth(), 0);
				}
			});
		}
		return btnPopup;
	}
 
	private JTextField getTxtNumElem() {
		if (txtNumElem == null) {
			txtNumElem = new JTextField();
			txtNumElem.setColumns(3);
			txtNumElem.setText("60");
		}
		return txtNumElem;
	}
 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FramePopupMenu thisClass = new FramePopupMenu();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}
 
	public FramePopupMenu() {
		super();
		initialize();
	}
 
	private void initialize() {
		this.setSize(274, 109);
		this.setContentPane(getJContentPane());
		this.setTitle("Scroll en un menú popup");
	}
 
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			lblNumElem = new JLabel();
			lblNumElem.setText("Número de elementos del Menú");
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(8);
			flowLayout.setVgap(8);
			jContentPane = new JPanel();
			jContentPane.setLayout(flowLayout);
			jContentPane.add(getBtnPopup(), null);
			jContentPane.add(lblNumElem, null);
			jContentPane.add(getTxtNumElem(), null);
		}
		return jContentPane;
	}
}

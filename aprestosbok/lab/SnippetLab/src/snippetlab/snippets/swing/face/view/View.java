/*
 * AView.java Copyright (C) EID, SA.
 */
/**
 * 
 */
package snippetlab.snippets.swing.face.view;

import javax.swing.JPanel;

/**
 * 
 */
public class View extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private javax.swing.JComboBox cb_attribute;
    private javax.swing.JCheckBox ck_enabled;
    private javax.swing.JCheckBox ck_on;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList jl_items;
    private javax.swing.JTextField tf_code;
    private javax.swing.JTextArea tf_description;
    private javax.swing.JTextField tf_name;
	/**
	 * 
	 */
	public View()
	{
		initComponents();
	}


	
	private void initComponents() 
	{

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf_code = new javax.swing.JTextField();
        tf_name = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tf_description = new javax.swing.JTextArea();
        ck_enabled = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        ck_on = new javax.swing.JCheckBox();
        cb_attribute = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jl_items = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jLabel1.setText("code");

        jLabel2.setText("name");

        jLabel3.setText("description");



        tf_description.setColumns(20);
        tf_description.setRows(5);
        jScrollPane1.setViewportView(tf_description);

        ck_enabled.setText("enabled");


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ck_enabled)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(tf_code, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tf_name))
                        .addGap(550, 550, 550))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tf_code, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tf_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(ck_enabled)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel1);

        ck_on.setText("on");

        cb_attribute.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cb_attribute.setSelectedIndex(-1);

        jScrollPane2.setViewportView(jl_items);

        jLabel4.setText("items");

        jLabel5.setText("attribute");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cb_attribute, 0, 196, Short.MAX_VALUE)
                        .addComponent(ck_on)
                        .addComponent(jScrollPane2)))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(ck_on)
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb_attribute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
        );
    }



	/**
	 * @return the cb_attribute
	 */
	public javax.swing.JComboBox getCb_attribute()
	{
		return cb_attribute;
	}



	/**
	 * @param cb_attribute the cb_attribute to set
	 */
	public void setCb_attribute(javax.swing.JComboBox cb_attribute)
	{
		this.cb_attribute = cb_attribute;
	}



	/**
	 * @return the ck_enabled
	 */
	public javax.swing.JCheckBox getCk_enabled()
	{
		return ck_enabled;
	}



	/**
	 * @param ck_enabled the ck_enabled to set
	 */
	public void setCk_enabled(javax.swing.JCheckBox ck_enabled)
	{
		this.ck_enabled = ck_enabled;
	}



	/**
	 * @return the ck_on
	 */
	public javax.swing.JCheckBox getCk_on()
	{
		return ck_on;
	}



	/**
	 * @param ck_on the ck_on to set
	 */
	public void setCk_on(javax.swing.JCheckBox ck_on)
	{
		this.ck_on = ck_on;
	}



	/**
	 * @return the jLabel1
	 */
	public javax.swing.JLabel getJLabel1()
	{
		return jLabel1;
	}



	/**
	 * @param label1 the jLabel1 to set
	 */
	public void setJLabel1(javax.swing.JLabel label1)
	{
		jLabel1 = label1;
	}



	/**
	 * @return the jLabel2
	 */
	public javax.swing.JLabel getJLabel2()
	{
		return jLabel2;
	}



	/**
	 * @param label2 the jLabel2 to set
	 */
	public void setJLabel2(javax.swing.JLabel label2)
	{
		jLabel2 = label2;
	}



	/**
	 * @return the jLabel3
	 */
	public javax.swing.JLabel getJLabel3()
	{
		return jLabel3;
	}



	/**
	 * @param label3 the jLabel3 to set
	 */
	public void setJLabel3(javax.swing.JLabel label3)
	{
		jLabel3 = label3;
	}



	/**
	 * @return the jLabel4
	 */
	public javax.swing.JLabel getJLabel4()
	{
		return jLabel4;
	}



	/**
	 * @param label4 the jLabel4 to set
	 */
	public void setJLabel4(javax.swing.JLabel label4)
	{
		jLabel4 = label4;
	}



	/**
	 * @return the jLabel5
	 */
	public javax.swing.JLabel getJLabel5()
	{
		return jLabel5;
	}



	/**
	 * @param label5 the jLabel5 to set
	 */
	public void setJLabel5(javax.swing.JLabel label5)
	{
		jLabel5 = label5;
	}



	/**
	 * @return the jPanel1
	 */
	public javax.swing.JPanel getJPanel1()
	{
		return jPanel1;
	}



	/**
	 * @param panel1 the jPanel1 to set
	 */
	public void setJPanel1(javax.swing.JPanel panel1)
	{
		jPanel1 = panel1;
	}



	/**
	 * @return the jPanel2
	 */
	public javax.swing.JPanel getJPanel2()
	{
		return jPanel2;
	}



	/**
	 * @param panel2 the jPanel2 to set
	 */
	public void setJPanel2(javax.swing.JPanel panel2)
	{
		jPanel2 = panel2;
	}



	/**
	 * @return the jScrollPane1
	 */
	public javax.swing.JScrollPane getJScrollPane1()
	{
		return jScrollPane1;
	}



	/**
	 * @param scrollPane1 the jScrollPane1 to set
	 */
	public void setJScrollPane1(javax.swing.JScrollPane scrollPane1)
	{
		jScrollPane1 = scrollPane1;
	}



	/**
	 * @return the jScrollPane2
	 */
	public javax.swing.JScrollPane getJScrollPane2()
	{
		return jScrollPane2;
	}



	/**
	 * @param scrollPane2 the jScrollPane2 to set
	 */
	public void setJScrollPane2(javax.swing.JScrollPane scrollPane2)
	{
		jScrollPane2 = scrollPane2;
	}



	/**
	 * @return the jTabbedPane1
	 */
	public javax.swing.JTabbedPane getJTabbedPane1()
	{
		return jTabbedPane1;
	}



	/**
	 * @param tabbedPane1 the jTabbedPane1 to set
	 */
	public void setJTabbedPane1(javax.swing.JTabbedPane tabbedPane1)
	{
		jTabbedPane1 = tabbedPane1;
	}



	/**
	 * @return the jl_items
	 */
	public javax.swing.JList getJl_items()
	{
		return jl_items;
	}



	/**
	 * @param jl_items the jl_items to set
	 */
	public void setJl_items(javax.swing.JList jl_items)
	{
		this.jl_items = jl_items;
	}



	/**
	 * @return the tf_code
	 */
	public javax.swing.JTextField getTf_code()
	{
		return tf_code;
	}



	/**
	 * @param tf_code the tf_code to set
	 */
	public void setTf_code(javax.swing.JTextField tf_code)
	{
		this.tf_code = tf_code;
	}



	/**
	 * @return the tf_description
	 */
	public javax.swing.JTextArea getTf_description()
	{
		return tf_description;
	}



	/**
	 * @param tf_description the tf_description to set
	 */
	public void setTf_description(javax.swing.JTextArea tf_description)
	{
		this.tf_description = tf_description;
	}



	/**
	 * @return the tf_name
	 */
	public javax.swing.JTextField getTf_name()
	{
		return tf_name;
	}



	/**
	 * @param tf_name the tf_name to set
	 */
	public void setTf_name(javax.swing.JTextField tf_name)
	{
		this.tf_name = tf_name;
	}



	 
}

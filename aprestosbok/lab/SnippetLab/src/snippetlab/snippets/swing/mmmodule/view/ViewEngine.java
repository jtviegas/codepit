/*
 * AController.java Copyright (C) EID, SA.
 */
package snippetlab.snippets.swing.mmmodule.view;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import snippetlab.snippets.swing.mmmodule.model.Model;
import snippetlab.snippets.swing.mmmodule.view.states.OnState;


public class ViewEngine
{
	
	private boolean readwrite = false;
	private View view;
	private Model model;
	
	private DefaultListModel listModel;
	private DefaultComboBoxModel comboModel;
	private ChangeListener changeListener;
	
	private String[] attributes = new String[]{
	       		    "attribute A","attribute B","attribute C","attribute D",
	       		    "attribute E","attribute F"};
	private String[] items = new String[]{
		                  		    "item A","item B","item C","item D",
		                  		    "item E","item F"};

	public ViewEngine()
	{
		
	}

	/**
	 * @param readwrite
	 * @param model
	 */
	public ViewEngine(boolean readwrite, Model model)
	{
		super();
		this.readwrite = readwrite;
		this.model = model;
	}

	public void setModel(Model model)
	{
		this.model = model;
	}

	public void setReadwrite(boolean readwrite)
	{
		this.readwrite = readwrite;
	}

	
	/**
	 * initializes data, tipically ran in a background task.
	 */
	public void init()
	{
		
		listModel = new DefaultListModel();
		for(String item:items)
			listModel.addElement(item);	

		comboModel = new DefaultComboBoxModel();
		for(String attribute:attributes)
			comboModel.addElement(attribute);
		
		view = new View();

	}
	
	public void load()
	{
		setDefaultViewState();
		
		if(readwrite)
			setBehaviour();
		
		writeGUI(model);
		setReadWriteState(this.readwrite);
	}
	
	public void unload()
	{
		if(readwrite)
			unsetBehaviour();
		
		clearViewState();

	}
	
	public View getView()
	{
		return view;
	}
	
	public Model getModel()
	{
		if(queryChanges())
			this.model = readGui();
		
		return this.model;
	}
	
	public boolean queryChanges()
	{
		boolean result = false;
		
		result = !model.equals(readGui());
		
		return result;
	}

	private void setBehaviour()
	{
		if(null == changeListener)
			changeListener = new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
				{
					OnState state = new OnState(view);
					state.change();
				}
			};
		
		view.getCk_on().addChangeListener(changeListener);
	}
	
	private void unsetBehaviour()
	{
		view.getCk_on().removeChangeListener(changeListener);
	}
	
	private Model readGui()
	{
		Model result = new Model();
		
		if(view.getTf_code().getText().trim().length() > 0)
			result.setCode(Integer.parseInt(view.getTf_code().getText().trim()));
		
		if(view.getTf_name().getText().trim().length() > 0)
			result.setName(view.getTf_name().getText().trim());
		
		if(view.getTf_description().getText().trim().length() > 0)
			result.setDescription(view.getTf_description().getText().trim());
		
		result.setEnabled(view.getCk_enabled().isSelected());
		result.setOn(view.getCk_on().isSelected());
		
		if(view.getCb_attribute().getSelectedIndex() > -1)
			result.setAttribute((String)view.getCb_attribute().getSelectedItem());
		
		for(int index:view.getJl_items().getSelectedIndices())
		{
			result.getListItems().add((String)view.getJl_items().getModel().getElementAt(index));
		}
		
		return result;
	}
	
	private void writeGUI(Model amodel)
	{
		view.getTf_code().setText(Integer.toString(amodel.getCode()));
		view.getTf_name().setText(new String(model.getName()));
		view.getTf_description().setText(new String(model.getDescription()));
		view.getCk_enabled().setSelected(model.isEnabled());
		view.getCk_on().setSelected(model.isOn());
		view.getCb_attribute().setSelectedItem(model.getAttribute());
		
		int[] indices = new int[model.getListItems().size()];
		
		for(int i=0;i < model.getListItems().size();i++)
		{
			String item = model.getListItems().get(i);
			int index = getListItemIndex(view.getJl_items(),item);
			indices[i]=index;
		}
		
		view.getJl_items().setSelectedIndices(indices);
		
	}

	
	private void setReadWriteState(boolean rw)
	{
		view.getTf_code().setEnabled(rw);
		view.getTf_name().setEnabled(rw);
		view.getTf_description().setEnabled(rw);
		view.getCk_enabled().setEnabled(rw);
		view.getCk_on().setEnabled(rw);
		view.getCb_attribute().setEnabled(rw);
		view.getJl_items().setEnabled(rw);
	}
	
	private int getListItemIndex(JList list, Object item)
	{
		int result = -1;
		
		for(int i=0 ; i < list.getModel().getSize() ; i++)
		{
			Object element = list.getModel().getElementAt(i);
			if(item.equals(element))
			{
				result = i;
				break;
			}
		}

		return result;
	}

	
	private void setDefaultViewState()
	{
		view.getTf_code().setText(null);
		view.getTf_name().setText(null);
		view.getTf_description().setText(null);
		view.getCk_enabled().setSelected(false);
		view.getCk_on().setSelected(false);
		view.getCb_attribute().setModel(comboModel);
		view.getCb_attribute().setSelectedItem(-1);
		view.getJl_items().setModel(listModel);
		view.getJl_items().clearSelection();
	}
	
	private void clearViewState()
	{
		view.getTf_code().setText(null);
		view.getTf_name().setText(null);
		view.getTf_description().setText(null);
		view.getCk_enabled().setSelected(false);
		view.getCk_on().setSelected(false);
		view.getCb_attribute().setModel(new DefaultComboBoxModel());
		view.getCb_attribute().setSelectedItem(-1);
		view.getJl_items().setModel(new DefaultListModel());
		view.getJl_items().clearSelection();
	}
	
	
}

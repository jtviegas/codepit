package org.jtvatdsi.studies.struts.quotesui.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jtvatdsi.studies.struts.quotesui.forms.SubmitForm;
import org.jtvatdsi.studies.struts.quotesui.misc.Constants;

public class SubmitAction extends Action {

	public SubmitAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		SubmitForm submitForm = (SubmitForm) form;
		
		
		
		return mapping.findForward(Constants.FORWARD_RESULT);
		
	}
	
	

}

package org.jtvatdsi.studies.struts.quotesui.actions;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jtvatdsi.studies.struts.quotesui.misc.Constants;

public class LangAction extends Action {

	public LangAction() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String[] localeParams = request.getParameter("locale").split("_");
		request.getSession().setAttribute(Globals.LOCALE_KEY, new Locale(localeParams[0],localeParams[1]));
		return mapping.findForward(Constants.FORWARD_HOME);
		
	}
	
	

}

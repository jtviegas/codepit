<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean-el.tld" prefix="bean"%>


<html:errors />

<html:form action="/submit.do" focus="author">
	<TABLE>
		<TR>
			<TH align="right"><bean:message key="author" />:</TH>
			<TD align="left"><html:text property="author" /></TD>
		</TR>
		<TR>
			<TH align="right"><bean:message key="text" />:</TH>
			<TD align="left" width="100%"><html:textarea styleClass="input-xxlarge" property="text" /></TD>
		</TR>
		<TR>
			<TD colspan="2" align="center">
			<html:reset styleClass="btn"><bean:message key="reset" /></html:reset>
			<html:submit styleClass="btn"><bean:message key="submit"/></html:submit>
			</TD>
		</TR>
	</TABLE>
</html:form>
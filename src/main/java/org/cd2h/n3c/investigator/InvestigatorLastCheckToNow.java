package org.cd2h.n3c.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Timestamp;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorLastCheckToNow extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(InvestigatorLastCheckToNow.class);


	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setLastCheckToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing Investigator for lastCheck tag ", e);
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Investigator for lastCheck tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
			}

		}
		return SKIP_BODY;
	}

	public Timestamp getLastCheck() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getLastCheck();
		} catch (Exception e) {

			log.error("Can't find enclosing Investigator for lastCheck tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Investigator for lastCheck tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
			}

		}
	}

	public void setLastCheck() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setLastCheckToNow( );
		} catch (Exception e) {

			log.error("Can't find enclosing Investigator for lastCheck tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing Investigator for lastCheck tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
			}

		}
	}
}
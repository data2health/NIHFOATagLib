package org.cd2h.n3c.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import java.sql.Timestamp;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorLastCheck extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(InvestigatorLastCheck.class);

	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getLastCheck());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for lastCheck tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for lastCheck tag ");
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
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for lastCheck tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
			}
		}
	}

	public void setLastCheck(Timestamp lastCheck) throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setLastCheck(lastCheck);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for lastCheck tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for lastCheck tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
			}
		}
	}

}

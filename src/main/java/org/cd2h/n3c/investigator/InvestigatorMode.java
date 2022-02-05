package org.cd2h.n3c.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorMode extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(InvestigatorMode.class);

	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getMode());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for mode tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for mode tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for mode tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getMode() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getMode();
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for mode tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for mode tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for mode tag ");
			}
		}
	}

	public void setMode(String mode) throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setMode(mode);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for mode tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for mode tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for mode tag ");
			}
		}
	}

}

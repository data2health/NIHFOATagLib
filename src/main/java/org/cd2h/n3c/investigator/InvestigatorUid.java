package org.cd2h.n3c.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorUid extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(InvestigatorUid.class);

	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getUid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for uid tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for uid tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for uid tag ");
			}

		}
		return SKIP_BODY;
	}

	public int getUid() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getUid();
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for uid tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for uid tag ");
				parent.doEndTag();
				return 0;
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for uid tag ");
			}
		}
	}

	public void setUid(int uid) throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setUid(uid);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for uid tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Investigator for uid tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Investigator for uid tag ");
			}
		}
	}

}

package edu.uiowa.nihfoa.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorUid extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getUid());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing Investigator for uid tag ");
		}
		return SKIP_BODY;
	}

	public int getUid() throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getUid();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing Investigator for uid tag ");
		}
	}

	public void setUid(int uid) throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing Investigator for uid tag ");
		}
	}

}

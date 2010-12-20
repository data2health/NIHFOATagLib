package edu.uiowa.nihfoa.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.util.Date;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorLastCheckToNow extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setLastCheckToNow( );
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
		}
		return SKIP_BODY;
	}

	public Date getLastCheck() throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getLastCheck();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
		}
	}

	public void setLastCheck( ) throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setLastCheckToNow( );
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing Investigator for lastCheck tag ");
		}
	}

}

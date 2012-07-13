package edu.uiowa.nihfoa.investigator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InvestigatorMode extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(InvestigatorMode.class);


	public int doStartTag() throws JspException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			if (!theInvestigator.commitNeeded) {
				pageContext.getOut().print(theInvestigator.getMode());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for mode tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for mode tag ");
		}
		return SKIP_BODY;
	}

	public String getMode() throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			return theInvestigator.getMode();
		} catch (Exception e) {
			log.error(" Can't find enclosing Investigator for mode tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for mode tag ");
		}
	}

	public void setMode(String mode) throws JspTagException {
		try {
			Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
			theInvestigator.setMode(mode);
		} catch (Exception e) {
			log.error("Can't find enclosing Investigator for mode tag ", e);
			throw new JspTagException("Error: Can't find enclosing Investigator for mode tag ");
		}
	}

}

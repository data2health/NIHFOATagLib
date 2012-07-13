package edu.uiowa.nihfoa.nihIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIcDescription extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(NihIcDescription.class);


	public int doStartTag() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (!theNihIc.commitNeeded) {
				pageContext.getOut().print(theNihIc.getDescription());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for description tag ");
		}
		return SKIP_BODY;
	}

	public String getDescription() throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			return theNihIc.getDescription();
		} catch (Exception e) {
			log.error(" Can't find enclosing NihIc for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for description tag ");
		}
	}

	public void setDescription(String description) throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			theNihIc.setDescription(description);
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for description tag ");
		}
	}

}

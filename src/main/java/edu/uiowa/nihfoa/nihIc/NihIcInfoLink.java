package edu.uiowa.nihfoa.nihIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIcInfoLink extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (!theNihIc.commitNeeded) {
				pageContext.getOut().print(theNihIc.getInfoLink());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for infoLink tag ");
		}
		return SKIP_BODY;
	}

	public String getInfoLink() throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			return theNihIc.getInfoLink();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for infoLink tag ");
		}
	}

	public void setInfoLink(String infoLink) throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			theNihIc.setInfoLink(infoLink);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for infoLink tag ");
		}
	}

}

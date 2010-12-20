package edu.uiowa.nihfoa.nihIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIcLogoLink extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (!theNihIc.commitNeeded) {
				pageContext.getOut().print(theNihIc.getLogoLink());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for logoLink tag ");
		}
		return SKIP_BODY;
	}

	public String getLogoLink() throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			return theNihIc.getLogoLink();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for logoLink tag ");
		}
	}

	public void setLogoLink(String logoLink) throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			theNihIc.setLogoLink(logoLink);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for logoLink tag ");
		}
	}

}

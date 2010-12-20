package edu.uiowa.nihfoa.nihIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIcIc extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (!theNihIc.commitNeeded) {
				pageContext.getOut().print(theNihIc.getIc());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for ic tag ");
		}
		return SKIP_BODY;
	}

	public String getIc() throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			return theNihIc.getIc();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for ic tag ");
		}
	}

	public void setIc(String ic) throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			theNihIc.setIc(ic);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing NihIc for ic tag ");
		}
	}

}

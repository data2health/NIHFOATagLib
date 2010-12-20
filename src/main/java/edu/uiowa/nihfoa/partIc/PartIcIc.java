package edu.uiowa.nihfoa.partIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class PartIcIc extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			if (!thePartIc.commitNeeded) {
				pageContext.getOut().print(thePartIc.getIc());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing PartIc for ic tag ");
		}
		return SKIP_BODY;
	}

	public String getIc() throws JspTagException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			return thePartIc.getIc();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing PartIc for ic tag ");
		}
	}

	public void setIc(String ic) throws JspTagException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			thePartIc.setIc(ic);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing PartIc for ic tag ");
		}
	}

}

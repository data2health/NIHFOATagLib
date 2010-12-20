package edu.uiowa.nihfoa.activityCode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class ActivityCodeCode extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			if (!theActivityCode.commitNeeded) {
				pageContext.getOut().print(theActivityCode.getCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing ActivityCode for code tag ");
		}
		return SKIP_BODY;
	}

	public String getCode() throws JspTagException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			return theActivityCode.getCode();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing ActivityCode for code tag ");
		}
	}

	public void setCode(String code) throws JspTagException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			theActivityCode.setCode(code);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing ActivityCode for code tag ");
		}
	}

}

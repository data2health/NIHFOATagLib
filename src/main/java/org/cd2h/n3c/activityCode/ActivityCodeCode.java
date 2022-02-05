package org.cd2h.n3c.activityCode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class ActivityCodeCode extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(ActivityCodeCode.class);

	public int doStartTag() throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			if (!theActivityCode.commitNeeded) {
				pageContext.getOut().print(theActivityCode.getCode());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for code tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing ActivityCode for code tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing ActivityCode for code tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getCode() throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			return theActivityCode.getCode();
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for code tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing ActivityCode for code tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing ActivityCode for code tag ");
			}
		}
	}

	public void setCode(String code) throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			theActivityCode.setCode(code);
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for code tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing ActivityCode for code tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing ActivityCode for code tag ");
			}
		}
	}

}

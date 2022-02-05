package org.cd2h.n3c.activityCode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class ActivityCodeID extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(ActivityCodeID.class);

	public int doStartTag() throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			if (!theActivityCode.commitNeeded) {
				pageContext.getOut().print(theActivityCode.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for ID tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing ActivityCode for ID tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing ActivityCode for ID tag ");
			}

		}
		return SKIP_BODY;
	}

	public int getID() throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			return theActivityCode.getID();
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for ID tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing ActivityCode for ID tag ");
				parent.doEndTag();
				return 0;
			}else{
				throw new JspTagException("Error: Can't find enclosing ActivityCode for ID tag ");
			}
		}
	}

	public void setID(int ID) throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			theActivityCode.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for ID tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing ActivityCode for ID tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing ActivityCode for ID tag ");
			}
		}
	}

}

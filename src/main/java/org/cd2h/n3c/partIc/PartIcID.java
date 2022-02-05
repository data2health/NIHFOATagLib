package org.cd2h.n3c.partIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class PartIcID extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(PartIcID.class);

	public int doStartTag() throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			if (!thePartIc.commitNeeded) {
				pageContext.getOut().print(thePartIc.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ID tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing PartIc for ID tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing PartIc for ID tag ");
			}

		}
		return SKIP_BODY;
	}

	public int getID() throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			return thePartIc.getID();
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ID tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing PartIc for ID tag ");
				parent.doEndTag();
				return 0;
			}else{
				throw new JspTagException("Error: Can't find enclosing PartIc for ID tag ");
			}
		}
	}

	public void setID(int ID) throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			thePartIc.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ID tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing PartIc for ID tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing PartIc for ID tag ");
			}
		}
	}

}

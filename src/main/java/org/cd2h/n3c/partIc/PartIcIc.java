package org.cd2h.n3c.partIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class PartIcIc extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(PartIcIc.class);

	public int doStartTag() throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			if (!thePartIc.commitNeeded) {
				pageContext.getOut().print(thePartIc.getIc());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ic tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing PartIc for ic tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing PartIc for ic tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getIc() throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			return thePartIc.getIc();
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ic tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing PartIc for ic tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing PartIc for ic tag ");
			}
		}
	}

	public void setIc(String ic) throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			thePartIc.setIc(ic);
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ic tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing PartIc for ic tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing PartIc for ic tag ");
			}
		}
	}

}

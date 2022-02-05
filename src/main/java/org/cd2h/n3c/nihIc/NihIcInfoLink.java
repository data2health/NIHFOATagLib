package org.cd2h.n3c.nihIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIcInfoLink extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(NihIcInfoLink.class);

	public int doStartTag() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (!theNihIc.commitNeeded) {
				pageContext.getOut().print(theNihIc.getInfoLink());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for infoLink tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing NihIc for infoLink tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing NihIc for infoLink tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getInfoLink() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			return theNihIc.getInfoLink();
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for infoLink tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing NihIc for infoLink tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing NihIc for infoLink tag ");
			}
		}
	}

	public void setInfoLink(String infoLink) throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			theNihIc.setInfoLink(infoLink);
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for infoLink tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing NihIc for infoLink tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing NihIc for infoLink tag ");
			}
		}
	}

}

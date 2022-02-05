package org.cd2h.n3c.foaType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class FoaTypeInfoLink extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(FoaTypeInfoLink.class);

	public int doStartTag() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (!theFoaType.commitNeeded) {
				pageContext.getOut().print(theFoaType.getInfoLink());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for infoLink tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing FoaType for infoLink tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing FoaType for infoLink tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getInfoLink() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			return theFoaType.getInfoLink();
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for infoLink tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing FoaType for infoLink tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing FoaType for infoLink tag ");
			}
		}
	}

	public void setInfoLink(String infoLink) throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			theFoaType.setInfoLink(infoLink);
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for infoLink tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing FoaType for infoLink tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing FoaType for infoLink tag ");
			}
		}
	}

}

package org.cd2h.n3c.foaType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class FoaTypeCategory extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(FoaTypeCategory.class);

	public int doStartTag() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (!theFoaType.commitNeeded) {
				pageContext.getOut().print(theFoaType.getCategory());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for category tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing FoaType for category tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing FoaType for category tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getCategory() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			return theFoaType.getCategory();
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for category tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing FoaType for category tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing FoaType for category tag ");
			}
		}
	}

	public void setCategory(String category) throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			theFoaType.setCategory(category);
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for category tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing FoaType for category tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing FoaType for category tag ");
			}
		}
	}

}

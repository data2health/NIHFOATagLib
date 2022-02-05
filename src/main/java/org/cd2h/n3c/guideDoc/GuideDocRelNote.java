package org.cd2h.n3c.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocRelNote extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(GuideDocRelNote.class);

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getRelNote());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for relNote tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for relNote tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for relNote tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getRelNote() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getRelNote();
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for relNote tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for relNote tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for relNote tag ");
			}
		}
	}

	public void setRelNote(String relNote) throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setRelNote(relNote);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for relNote tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for relNote tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for relNote tag ");
			}
		}
	}

}

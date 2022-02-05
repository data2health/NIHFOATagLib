package org.cd2h.n3c.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocFileName extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(GuideDocFileName.class);

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getFileName());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for fileName tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for fileName tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for fileName tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getFileName() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getFileName();
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for fileName tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for fileName tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for fileName tag ");
			}
		}
	}

	public void setFileName(String fileName) throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setFileName(fileName);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for fileName tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for fileName tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for fileName tag ");
			}
		}
	}

}

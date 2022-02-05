package org.cd2h.n3c.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocPrimaryIc extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(GuideDocPrimaryIc.class);

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getPrimaryIc());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for primaryIc tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for primaryIc tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for primaryIc tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getPrimaryIc() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getPrimaryIc();
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for primaryIc tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for primaryIc tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for primaryIc tag ");
			}
		}
	}

	public void setPrimaryIc(String primaryIc) throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setPrimaryIc(primaryIc);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for primaryIc tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing GuideDoc for primaryIc tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for primaryIc tag ");
			}
		}
	}

}

package org.cd2h.n3c.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Timestamp;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocRelDateToNow extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(GuideDocRelDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setRelDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for relDate tag ", e);
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing GuideDoc for relDate tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for relDate tag ");
			}

		}
		return SKIP_BODY;
	}

	public Timestamp getRelDate() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getRelDate();
		} catch (Exception e) {

			log.error("Can't find enclosing GuideDoc for relDate tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing GuideDoc for relDate tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for relDate tag ");
			}

		}
	}

	public void setRelDate() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setRelDateToNow( );
		} catch (Exception e) {

			log.error("Can't find enclosing GuideDoc for relDate tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing GuideDoc for relDate tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for relDate tag ");
			}

		}
	}
}
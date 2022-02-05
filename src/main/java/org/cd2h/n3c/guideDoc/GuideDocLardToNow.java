package org.cd2h.n3c.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.Timestamp;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocLardToNow extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(GuideDocLardToNow.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setLardToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for lard tag ", e);
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing GuideDoc for lard tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for lard tag ");
			}

		}
		return SKIP_BODY;
	}

	public Timestamp getLard() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getLard();
		} catch (Exception e) {

			log.error("Can't find enclosing GuideDoc for lard tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing GuideDoc for lard tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for lard tag ");
			}

		}
	}

	public void setLard() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setLardToNow( );
		} catch (Exception e) {

			log.error("Can't find enclosing GuideDoc for lard tag ", e);

			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Can't find enclosing GuideDoc for lard tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing GuideDoc for lard tag ");
			}

		}
	}
}
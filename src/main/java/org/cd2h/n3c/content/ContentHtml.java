package org.cd2h.n3c.content;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class ContentHtml extends NIHFOATagLibTagSupport {

	private static final Logger log = LogManager.getLogger(ContentHtml.class);

	public int doStartTag() throws JspException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			if (!theContent.commitNeeded) {
				pageContext.getOut().print(theContent.getHtml());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Content for html tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Content for html tag ");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Content for html tag ");
			}

		}
		return SKIP_BODY;
	}

	public String getHtml() throws JspException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			return theContent.getHtml();
		} catch (Exception e) {
			log.error("Can't find enclosing Content for html tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Content for html tag ");
				parent.doEndTag();
				return null;
			}else{
				throw new JspTagException("Error: Can't find enclosing Content for html tag ");
			}
		}
	}

	public void setHtml(String html) throws JspException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			theContent.setHtml(html);
		} catch (Exception e) {
			log.error("Can't find enclosing Content for html tag ", e);
			freeConnection();
			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: Can't find enclosing Content for html tag ");
				parent.doEndTag();
			}else{
				throw new JspTagException("Error: Can't find enclosing Content for html tag ");
			}
		}
	}

}

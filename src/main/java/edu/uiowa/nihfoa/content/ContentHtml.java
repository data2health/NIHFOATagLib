package edu.uiowa.nihfoa.content;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class ContentHtml extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(ContentHtml.class);


	public int doStartTag() throws JspException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			if (!theContent.commitNeeded) {
				pageContext.getOut().print(theContent.getHtml());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Content for html tag ", e);
			throw new JspTagException("Error: Can't find enclosing Content for html tag ");
		}
		return SKIP_BODY;
	}

	public String getHtml() throws JspTagException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			return theContent.getHtml();
		} catch (Exception e) {
			log.error(" Can't find enclosing Content for html tag ", e);
			throw new JspTagException("Error: Can't find enclosing Content for html tag ");
		}
	}

	public void setHtml(String html) throws JspTagException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			theContent.setHtml(html);
		} catch (Exception e) {
			log.error("Can't find enclosing Content for html tag ", e);
			throw new JspTagException("Error: Can't find enclosing Content for html tag ");
		}
	}

}

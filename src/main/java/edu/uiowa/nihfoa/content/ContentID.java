package edu.uiowa.nihfoa.content;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class ContentID extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(ContentID.class);


	public int doStartTag() throws JspException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			if (!theContent.commitNeeded) {
				pageContext.getOut().print(theContent.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Content for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Content for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			return theContent.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Content for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Content for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Content theContent = (Content)findAncestorWithClass(this, Content.class);
			theContent.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Content for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Content for ID tag ");
		}
	}

}

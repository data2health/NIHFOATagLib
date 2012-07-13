package edu.uiowa.nihfoa.nihIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIcTitle extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(NihIcTitle.class);


	public int doStartTag() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (!theNihIc.commitNeeded) {
				pageContext.getOut().print(theNihIc.getTitle());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for title tag ");
		}
		return SKIP_BODY;
	}

	public String getTitle() throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			return theNihIc.getTitle();
		} catch (Exception e) {
			log.error(" Can't find enclosing NihIc for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for title tag ");
		}
	}

	public void setTitle(String title) throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			theNihIc.setTitle(title);
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for title tag ");
		}
	}

}

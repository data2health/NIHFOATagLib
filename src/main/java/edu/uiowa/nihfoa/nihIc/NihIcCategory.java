package edu.uiowa.nihfoa.nihIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIcCategory extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(NihIcCategory.class);


	public int doStartTag() throws JspException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (!theNihIc.commitNeeded) {
				pageContext.getOut().print(theNihIc.getCategory());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for category tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for category tag ");
		}
		return SKIP_BODY;
	}

	public String getCategory() throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			return theNihIc.getCategory();
		} catch (Exception e) {
			log.error(" Can't find enclosing NihIc for category tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for category tag ");
		}
	}

	public void setCategory(String category) throws JspTagException {
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			theNihIc.setCategory(category);
		} catch (Exception e) {
			log.error("Can't find enclosing NihIc for category tag ", e);
			throw new JspTagException("Error: Can't find enclosing NihIc for category tag ");
		}
	}

}

package edu.uiowa.nihfoa.foaType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class FoaTypeCategory extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(FoaTypeCategory.class);


	public int doStartTag() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (!theFoaType.commitNeeded) {
				pageContext.getOut().print(theFoaType.getCategory());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for category tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for category tag ");
		}
		return SKIP_BODY;
	}

	public String getCategory() throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			return theFoaType.getCategory();
		} catch (Exception e) {
			log.error(" Can't find enclosing FoaType for category tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for category tag ");
		}
	}

	public void setCategory(String category) throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			theFoaType.setCategory(category);
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for category tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for category tag ");
		}
	}

}

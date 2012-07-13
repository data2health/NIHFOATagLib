package edu.uiowa.nihfoa.foaType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class FoaTypeDescription extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(FoaTypeDescription.class);


	public int doStartTag() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (!theFoaType.commitNeeded) {
				pageContext.getOut().print(theFoaType.getDescription());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for description tag ");
		}
		return SKIP_BODY;
	}

	public String getDescription() throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			return theFoaType.getDescription();
		} catch (Exception e) {
			log.error(" Can't find enclosing FoaType for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for description tag ");
		}
	}

	public void setDescription(String description) throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			theFoaType.setDescription(description);
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for description tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for description tag ");
		}
	}

}

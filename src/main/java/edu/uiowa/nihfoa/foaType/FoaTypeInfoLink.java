package edu.uiowa.nihfoa.foaType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class FoaTypeInfoLink extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(FoaTypeInfoLink.class);


	public int doStartTag() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (!theFoaType.commitNeeded) {
				pageContext.getOut().print(theFoaType.getInfoLink());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for infoLink tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for infoLink tag ");
		}
		return SKIP_BODY;
	}

	public String getInfoLink() throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			return theFoaType.getInfoLink();
		} catch (Exception e) {
			log.error(" Can't find enclosing FoaType for infoLink tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for infoLink tag ");
		}
	}

	public void setInfoLink(String infoLink) throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			theFoaType.setInfoLink(infoLink);
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for infoLink tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for infoLink tag ");
		}
	}

}

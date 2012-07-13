package edu.uiowa.nihfoa.foaType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class FoaTypeCode extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(FoaTypeCode.class);


	public int doStartTag() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (!theFoaType.commitNeeded) {
				pageContext.getOut().print(theFoaType.getCode());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for code tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for code tag ");
		}
		return SKIP_BODY;
	}

	public String getCode() throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			return theFoaType.getCode();
		} catch (Exception e) {
			log.error(" Can't find enclosing FoaType for code tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for code tag ");
		}
	}

	public void setCode(String code) throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			theFoaType.setCode(code);
		} catch (Exception e) {
			log.error("Can't find enclosing FoaType for code tag ", e);
			throw new JspTagException("Error: Can't find enclosing FoaType for code tag ");
		}
	}

}

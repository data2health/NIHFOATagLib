package edu.uiowa.nihfoa.foaType;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class FoaTypeTitle extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (!theFoaType.commitNeeded) {
				pageContext.getOut().print(theFoaType.getTitle());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing FoaType for title tag ");
		}
		return SKIP_BODY;
	}

	public String getTitle() throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			return theFoaType.getTitle();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing FoaType for title tag ");
		}
	}

	public void setTitle(String title) throws JspTagException {
		try {
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			theFoaType.setTitle(title);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing FoaType for title tag ");
		}
	}

}

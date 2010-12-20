package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocExpirationDate extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getExpirationDate());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for expirationDate tag ");
		}
		return SKIP_BODY;
	}

	public String getExpirationDate() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getExpirationDate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for expirationDate tag ");
		}
	}

	public void setExpirationDate(String expirationDate) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setExpirationDate(expirationDate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for expirationDate tag ");
		}
	}

}

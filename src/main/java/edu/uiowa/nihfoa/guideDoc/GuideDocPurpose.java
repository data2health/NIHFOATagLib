package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocPurpose extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getPurpose());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for purpose tag ");
		}
		return SKIP_BODY;
	}

	public String getPurpose() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getPurpose();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for purpose tag ");
		}
	}

	public void setPurpose(String purpose) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setPurpose(purpose);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for purpose tag ");
		}
	}

}

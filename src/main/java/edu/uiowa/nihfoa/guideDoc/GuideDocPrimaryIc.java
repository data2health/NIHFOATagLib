package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocPrimaryIc extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getPrimaryIc());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for primaryIc tag ");
		}
		return SKIP_BODY;
	}

	public String getPrimaryIc() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getPrimaryIc();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for primaryIc tag ");
		}
	}

	public void setPrimaryIc(String primaryIc) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setPrimaryIc(primaryIc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for primaryIc tag ");
		}
	}

}

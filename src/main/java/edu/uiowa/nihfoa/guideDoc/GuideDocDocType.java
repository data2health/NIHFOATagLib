package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocDocType extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getDocType());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docType tag ");
		}
		return SKIP_BODY;
	}

	public String getDocType() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getDocType();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docType tag ");
		}
	}

	public void setDocType(String docType) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setDocType(docType);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docType tag ");
		}
	}

}

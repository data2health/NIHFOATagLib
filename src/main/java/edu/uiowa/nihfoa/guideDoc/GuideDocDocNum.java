package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocDocNum extends NIHFOATagLibTagSupport {

	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getDocNum());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docNum tag ");
		}
		return SKIP_BODY;
	}

	public String getDocNum() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getDocNum();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docNum tag ");
		}
	}

	public void setDocNum(String docNum) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setDocNum(docNum);
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docNum tag ");
		}
	}

}

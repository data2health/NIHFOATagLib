package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocDocNum extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocDocNum.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getDocNum());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for docNum tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docNum tag ");
		}
		return SKIP_BODY;
	}

	public String getDocNum() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getDocNum();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for docNum tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docNum tag ");
		}
	}

	public void setDocNum(String docNum) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setDocNum(docNum);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for docNum tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docNum tag ");
		}
	}

}

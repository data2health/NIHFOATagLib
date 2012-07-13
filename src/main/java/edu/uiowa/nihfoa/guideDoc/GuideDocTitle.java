package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocTitle extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocTitle.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getTitle());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for title tag ");
		}
		return SKIP_BODY;
	}

	public String getTitle() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getTitle();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for title tag ");
		}
	}

	public void setTitle(String title) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setTitle(title);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for title tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for title tag ");
		}
	}

}

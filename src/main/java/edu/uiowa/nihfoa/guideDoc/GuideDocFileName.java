package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocFileName extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocFileName.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getFileName());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for fileName tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for fileName tag ");
		}
		return SKIP_BODY;
	}

	public String getFileName() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getFileName();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for fileName tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for fileName tag ");
		}
	}

	public void setFileName(String fileName) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setFileName(fileName);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for fileName tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for fileName tag ");
		}
	}

}

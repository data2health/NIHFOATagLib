package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocGuideLink extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocGuideLink.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getGuideLink());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for guideLink tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for guideLink tag ");
		}
		return SKIP_BODY;
	}

	public String getGuideLink() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getGuideLink();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for guideLink tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for guideLink tag ");
		}
	}

	public void setGuideLink(String guideLink) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setGuideLink(guideLink);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for guideLink tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for guideLink tag ");
		}
	}

}

package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocIntentDate extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocIntentDate.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getIntentDate());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for intentDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for intentDate tag ");
		}
		return SKIP_BODY;
	}

	public String getIntentDate() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getIntentDate();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for intentDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for intentDate tag ");
		}
	}

	public void setIntentDate(String intentDate) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setIntentDate(intentDate);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for intentDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for intentDate tag ");
		}
	}

}

package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocRelNote extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocRelNote.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getRelNote());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for relNote tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for relNote tag ");
		}
		return SKIP_BODY;
	}

	public String getRelNote() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getRelNote();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for relNote tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for relNote tag ");
		}
	}

	public void setRelNote(String relNote) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setRelNote(relNote);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for relNote tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for relNote tag ");
		}
	}

}

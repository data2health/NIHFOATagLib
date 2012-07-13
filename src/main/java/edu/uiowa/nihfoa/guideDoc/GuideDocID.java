package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocID extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocID.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for ID tag ");
		}
	}

}

package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocRelDateToNow extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocRelDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setRelDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for relDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for relDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getRelDate() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getRelDate();
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for relDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for relDate tag ");
		}
	}

	public void setRelDate( ) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setRelDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for relDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for relDate tag ");
		}
	}

}

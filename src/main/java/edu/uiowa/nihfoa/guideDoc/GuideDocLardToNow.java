package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocLardToNow extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocLardToNow.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setLardToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for lard tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for lard tag ");
		}
		return SKIP_BODY;
	}

	public Date getLard() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getLard();
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for lard tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for lard tag ");
		}
	}

	public void setLard( ) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setLardToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for lard tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for lard tag ");
		}
	}

}

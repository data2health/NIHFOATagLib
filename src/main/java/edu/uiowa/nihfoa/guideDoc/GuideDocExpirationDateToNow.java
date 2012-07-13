package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocExpirationDateToNow extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocExpirationDateToNow.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setExpirationDateToNow( );
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for expirationDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for expirationDate tag ");
		}
		return SKIP_BODY;
	}

	public Date getExpirationDate() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getExpirationDate();
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for expirationDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for expirationDate tag ");
		}
	}

	public void setExpirationDate( ) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setExpirationDateToNow( );
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for expirationDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for expirationDate tag ");
		}
	}

}

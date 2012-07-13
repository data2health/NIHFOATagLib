package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocAppReceiptDate extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocAppReceiptDate.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getAppReceiptDate());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for appReceiptDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for appReceiptDate tag ");
		}
		return SKIP_BODY;
	}

	public String getAppReceiptDate() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getAppReceiptDate();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for appReceiptDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for appReceiptDate tag ");
		}
	}

	public void setAppReceiptDate(String appReceiptDate) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setAppReceiptDate(appReceiptDate);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for appReceiptDate tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for appReceiptDate tag ");
		}
	}

}

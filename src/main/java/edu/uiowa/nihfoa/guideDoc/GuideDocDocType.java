package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocDocType extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocDocType.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getDocType());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for docType tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docType tag ");
		}
		return SKIP_BODY;
	}

	public String getDocType() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getDocType();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for docType tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docType tag ");
		}
	}

	public void setDocType(String docType) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setDocType(docType);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for docType tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for docType tag ");
		}
	}

}

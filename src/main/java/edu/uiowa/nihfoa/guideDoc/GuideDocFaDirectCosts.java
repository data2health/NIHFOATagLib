package edu.uiowa.nihfoa.guideDoc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class GuideDocFaDirectCosts extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(GuideDocFaDirectCosts.class);


	public int doStartTag() throws JspException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (!theGuideDoc.commitNeeded) {
				pageContext.getOut().print(theGuideDoc.getFaDirectCosts());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for faDirectCosts tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for faDirectCosts tag ");
		}
		return SKIP_BODY;
	}

	public String getFaDirectCosts() throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			return theGuideDoc.getFaDirectCosts();
		} catch (Exception e) {
			log.error(" Can't find enclosing GuideDoc for faDirectCosts tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for faDirectCosts tag ");
		}
	}

	public void setFaDirectCosts(String faDirectCosts) throws JspTagException {
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			theGuideDoc.setFaDirectCosts(faDirectCosts);
		} catch (Exception e) {
			log.error("Can't find enclosing GuideDoc for faDirectCosts tag ", e);
			throw new JspTagException("Error: Can't find enclosing GuideDoc for faDirectCosts tag ");
		}
	}

}

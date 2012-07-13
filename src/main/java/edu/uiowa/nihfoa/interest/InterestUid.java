package edu.uiowa.nihfoa.interest;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InterestUid extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(InterestUid.class);


	public int doStartTag() throws JspException {
		try {
			Interest theInterest = (Interest)findAncestorWithClass(this, Interest.class);
			if (!theInterest.commitNeeded) {
				pageContext.getOut().print(theInterest.getUid());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Interest for uid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Interest for uid tag ");
		}
		return SKIP_BODY;
	}

	public int getUid() throws JspTagException {
		try {
			Interest theInterest = (Interest)findAncestorWithClass(this, Interest.class);
			return theInterest.getUid();
		} catch (Exception e) {
			log.error(" Can't find enclosing Interest for uid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Interest for uid tag ");
		}
	}

	public void setUid(int uid) throws JspTagException {
		try {
			Interest theInterest = (Interest)findAncestorWithClass(this, Interest.class);
			theInterest.setUid(uid);
		} catch (Exception e) {
			log.error("Can't find enclosing Interest for uid tag ", e);
			throw new JspTagException("Error: Can't find enclosing Interest for uid tag ");
		}
	}

}

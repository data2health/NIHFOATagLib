package edu.uiowa.nihfoa.interest;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class InterestID extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(InterestID.class);


	public int doStartTag() throws JspException {
		try {
			Interest theInterest = (Interest)findAncestorWithClass(this, Interest.class);
			if (!theInterest.commitNeeded) {
				pageContext.getOut().print(theInterest.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing Interest for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Interest for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			Interest theInterest = (Interest)findAncestorWithClass(this, Interest.class);
			return theInterest.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing Interest for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Interest for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			Interest theInterest = (Interest)findAncestorWithClass(this, Interest.class);
			theInterest.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing Interest for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing Interest for ID tag ");
		}
	}

}

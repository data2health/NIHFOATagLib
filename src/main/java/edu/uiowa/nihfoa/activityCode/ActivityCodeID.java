package edu.uiowa.nihfoa.activityCode;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class ActivityCodeID extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(ActivityCodeID.class);


	public int doStartTag() throws JspException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			if (!theActivityCode.commitNeeded) {
				pageContext.getOut().print(theActivityCode.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing ActivityCode for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			return theActivityCode.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing ActivityCode for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing ActivityCode for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			ActivityCode theActivityCode = (ActivityCode)findAncestorWithClass(this, ActivityCode.class);
			theActivityCode.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing ActivityCode for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing ActivityCode for ID tag ");
		}
	}

}

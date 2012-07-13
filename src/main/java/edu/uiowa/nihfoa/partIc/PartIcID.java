package edu.uiowa.nihfoa.partIc;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class PartIcID extends NIHFOATagLibTagSupport {
	private static final Log log = LogFactory.getLog(PartIcID.class);


	public int doStartTag() throws JspException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			if (!thePartIc.commitNeeded) {
				pageContext.getOut().print(thePartIc.getID());
			}
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing PartIc for ID tag ");
		}
		return SKIP_BODY;
	}

	public int getID() throws JspTagException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			return thePartIc.getID();
		} catch (Exception e) {
			log.error(" Can't find enclosing PartIc for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing PartIc for ID tag ");
		}
	}

	public void setID(int ID) throws JspTagException {
		try {
			PartIc thePartIc = (PartIc)findAncestorWithClass(this, PartIc.class);
			thePartIc.setID(ID);
		} catch (Exception e) {
			log.error("Can't find enclosing PartIc for ID tag ", e);
			throw new JspTagException("Error: Can't find enclosing PartIc for ID tag ");
		}
	}

}

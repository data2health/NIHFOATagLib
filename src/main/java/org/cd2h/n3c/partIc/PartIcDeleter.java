package org.cd2h.n3c.partIc;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.cd2h.n3c.NIHFOATagLibTagSupport;
import org.cd2h.n3c.NIHFOATagLibBodyTagSupport;
import org.cd2h.n3c.nihIc.NihIc;
import org.cd2h.n3c.guideDoc.GuideDoc;

@SuppressWarnings("serial")
public class PartIcDeleter extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String ic = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Logger log = LogManager.getLogger(PartIcDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
		if (theNihIc!= null)
			parentEntities.addElement(theNihIc);
		GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
		if (theGuideDoc!= null)
			parentEntities.addElement(theGuideDoc);

		if (theNihIc == null) {
		} else {
			ic = theNihIc.getIc();
		}
		if (theGuideDoc == null) {
		} else {
			ID = theGuideDoc.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from NIH_FOA.part_ic where 1=1"
                                                        + (ID == 0 ? "" : " and id = ? ")
                                                        + (ic == null ? "" : " and ic = ? ")
                                                        + (ic == null ? "" : " and ic = ? ")
                                                        + (ID == 0 ? "" : " and id = ? "));
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (ic != null) stat.setString(webapp_keySeq++, ic);
			if (ic != null) stat.setString(webapp_keySeq++, ic);
			if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating PartIc deleter", e);

			clearServiceState();
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: JDBC error generating PartIc deleter");
				return parent.doEndTag();
			}else{
				throw new JspException("Error: JDBC error generating PartIc deleter",e);
			}

        } finally {
            freeConnection();
        }

        return SKIP_BODY;
    }

	public int doEndTag() throws JspException {

		clearServiceState();
		Boolean error = (Boolean) pageContext.getAttribute("tagError");
		if(error != null && error){

			freeConnection();

			Exception e = (Exception) pageContext.getAttribute("tagErrorException");
			String message = (String) pageContext.getAttribute("tagErrorMessage");

			Tag parent = getParent();
			if(parent != null){
				return parent.doEndTag();
			}else if(e != null && message != null){
				throw new JspException(message,e);
			}else if(parent == null){
				pageContext.removeAttribute("tagError");
				pageContext.removeAttribute("tagErrorException");
				pageContext.removeAttribute("tagErrorMessage");
			}
		}
		return super.doEndTag();
	}

    private void clearServiceState() {
        ID = 0;
        ic = null;
        parentEntities = new Vector<NIHFOATagLibTagSupport>();

        this.rs = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }



	public int getID () {
		return ID;
	}

	public void setID (int ID) {
		this.ID = ID;
	}

	public int getActualID () {
		return ID;
	}

	public String getIc () {
		return ic;
	}

	public void setIc (String ic) {
		this.ic = ic;
	}

	public String getActualIc () {
		return ic;
	}
}

package org.cd2h.n3c.activityCode;


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
import org.cd2h.n3c.foaType.FoaType;
import org.cd2h.n3c.guideDoc.GuideDoc;

@SuppressWarnings("serial")
public class ActivityCodeDeleter extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String code = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Logger log = LogManager.getLogger(ActivityCodeDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
		if (theFoaType!= null)
			parentEntities.addElement(theFoaType);
		GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
		if (theGuideDoc!= null)
			parentEntities.addElement(theGuideDoc);

		if (theFoaType == null) {
		} else {
			code = theFoaType.getCode();
		}
		if (theGuideDoc == null) {
		} else {
			ID = theGuideDoc.getID();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from NIH_FOA.activity_code where 1=1"
                                                        + (ID == 0 ? "" : " and id = ? ")
                                                        + (code == null ? "" : " and code = ? ")
                                                        + (code == null ? "" : " and code = ? ")
                                                        + (ID == 0 ? "" : " and id = ? "));
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (code != null) stat.setString(webapp_keySeq++, code);
			if (code != null) stat.setString(webapp_keySeq++, code);
			if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating ActivityCode deleter", e);

			clearServiceState();
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: JDBC error generating ActivityCode deleter");
				return parent.doEndTag();
			}else{
				throw new JspException("Error: JDBC error generating ActivityCode deleter",e);
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
        code = null;
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

	public String getCode () {
		return code;
	}

	public void setCode (String code) {
		this.code = code;
	}

	public String getActualCode () {
		return code;
	}
}

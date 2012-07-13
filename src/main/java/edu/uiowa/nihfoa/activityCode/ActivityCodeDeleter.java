package edu.uiowa.nihfoa.activityCode;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.NIHFOATagLibBodyTagSupport;
import edu.uiowa.nihfoa.guideDoc.GuideDoc;
import edu.uiowa.nihfoa.foaType.FoaType;

@SuppressWarnings("serial")
public class ActivityCodeDeleter extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String code = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Log log = LogFactory.getLog(ActivityCodeDeleter.class);


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
		if (theGuideDoc!= null)
			parentEntities.addElement(theGuideDoc);
		FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
		if (theFoaType!= null)
			parentEntities.addElement(theFoaType);

		if (theGuideDoc == null) {
		} else {
			ID = theGuideDoc.getID();
		}
		if (theFoaType == null) {
		} else {
			code = theFoaType.getCode();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from NIH_FOA.activity_code where 1=1"
                                                        + (ID == 0 ? "" : " and id = ? ")
                                                        + (code == null ? "" : " and code = ? "));
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (code != null) stat.setString(webapp_keySeq++, code);
            stat.execute();

			webapp_keySeq = 1;
        } catch (SQLException e) {
            log.error("JDBC error generating ActivityCode deleter", e);
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating ActivityCode deleter");
        } finally {
            freeConnection();
        }

        return SKIP_BODY;
    }

	public int doEndTag() throws JspException {
		clearServiceState();
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

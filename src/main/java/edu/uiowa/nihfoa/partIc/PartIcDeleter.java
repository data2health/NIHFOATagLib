package edu.uiowa.nihfoa.partIc;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.NIHFOATagLibBodyTagSupport;
import edu.uiowa.nihfoa.guideDoc.GuideDoc;
import edu.uiowa.nihfoa.nihIc.NihIc;

@SuppressWarnings("serial")

public class PartIcDeleter extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String ic = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {
		GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
		if (theGuideDoc!= null)
			parentEntities.addElement(theGuideDoc);
		NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
		if (theNihIc!= null)
			parentEntities.addElement(theNihIc);

		if (theGuideDoc == null) {
		} else {
			ID = theGuideDoc.getID();
		}
		if (theNihIc == null) {
		} else {
			ic = theNihIc.getIc();
		}


        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from NIH_FOA.part_ic where 1=1"
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        + (ic == null ? "" : " and ic = ?")
                                                        );
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (ic != null) stat.setString(webapp_keySeq++, ic);
            stat.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating PartIc deleter");
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

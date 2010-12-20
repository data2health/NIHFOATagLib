package edu.uiowa.nihfoa.content;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.NIHFOATagLibBodyTagSupport;

@SuppressWarnings("serial")

public class ContentDeleter extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String html = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {



        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from NIH_FOA.content where 1=1"
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        );
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            stat.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating Content deleter");
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
}

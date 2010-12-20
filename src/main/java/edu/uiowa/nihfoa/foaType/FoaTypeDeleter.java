package edu.uiowa.nihfoa.foaType;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.NIHFOATagLibBodyTagSupport;

@SuppressWarnings("serial")

public class FoaTypeDeleter extends NIHFOATagLibBodyTagSupport {
    String code = null;
    String category = null;
    String title = null;
    String description = null;
    String infoLink = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();


    ResultSet rs = null;
    String var = null;
    int rsCount = 0;

    public int doStartTag() throws JspException {



        PreparedStatement stat;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("DELETE from NIH_FOA.foa_type where 1=1"
                                                        + (code == null ? "" : " and code = ?")
                                                        );
            if (code != null) stat.setString(webapp_keySeq++, code);
            stat.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            clearServiceState();
            throw new JspTagException("Error: JDBC error generating FoaType deleter");
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

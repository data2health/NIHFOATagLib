package edu.uiowa.nihfoa.guideDoc;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.NIHFOATagLibBodyTagSupport;

@SuppressWarnings("serial")

public class GuideDocIteratorByPrimary extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String primaryIc = null;
    String title = null;
    String purpose = null;
    String relNote = null;
    String faDirectCosts = null;
    String guideLink = null;
    String relDate = null;
    String intentDate = null;
    String appReceiptDate = null;
    String lard = null;
    String fileName = null;
    String docType = null;
    String docNum = null;
    String expirationDate = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();


    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

	public static String guideDocCount(String primary_ic) throws JspTagException {
		int count = 0;
		GuideDocIterator theIterator = new GuideDocIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.guide_doc where primary_ic = ?"
						);

            stat.setString(1,primary_ic);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
			theIterator.freeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			theIterator.freeConnection();
			throw new JspTagException("Error: JDBC error generating GuideDoc iterator");
		}
		return "" + count;
	}

    public int doStartTag() throws JspException {



        PreparedStatement stat = null;
        try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT NIH_FOA.guide_doc.id from " + generateFromClause() + " where 1=1"
                                                        + " and primary_ic = ? "
                                                        + generateJoinCriteria()
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            stat.setString(1,primaryIc);
            rs = stat.executeQuery();

            if (rs.next()) {
                ID = rs.getInt(1);
                pageContext.setAttribute(var, ID);
                ++rsCount;
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating GuideDoc iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    public String getPrimaryIc() {
        return primaryIc;
    }

    public void setPrimaryIc(String primaryIc) {
        this.primaryIc = primaryIc;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("NIH_FOA.guide_doc");
      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "id";
        }
    }

    private String generateLimitCriteria() {
        if (limitCriteria > 0) {
            return " limit " + limitCriteria;
        } else {
            return "";
        }
    }

    public int doAfterBody() throws JspTagException {
        try {
            if (rs.next()) {
                ID = rs.getInt(1);
                pageContext.setAttribute(var, ID);
                ++rsCount;
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across GuideDoc");
        }
        clearServiceState();
        freeConnection();
        return SKIP_BODY;
    }

    private void clearServiceState() {
        ID = 0;
        parentEntities = new Vector<NIHFOATagLibTagSupport>();

        this.rs = null;
        this.sortCriteria = null;
        this.var = null;
        this.rsCount = 0;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public int getLimitCriteria() {
        return limitCriteria;
    }

    public void setLimitCriteria(int limitCriteria) {
        this.limitCriteria = limitCriteria;
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
}

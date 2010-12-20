package edu.uiowa.nihfoa.investigator;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.NIHFOATagLibBodyTagSupport;

@SuppressWarnings("serial")

public class InvestigatorIterator extends NIHFOATagLibBodyTagSupport {
    int uid = 0;
    String mode = null;
    Date lastCheck = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Log log =LogFactory.getLog(Investigator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

	public static String investigatorCount() throws JspTagException {
		int count = 0;
		InvestigatorIterator theIterator = new InvestigatorIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.investigator"
						);

			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error generating Investigator iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean investigatorExists (String uid) throws JspTagException {
		int count = 0;
		InvestigatorIterator theIterator = new InvestigatorIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.investigator where 1=1"
						+ " and uid = ?"
						);

			stat.setInt(1,Integer.parseInt(uid));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error generating Investigator iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {



      try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT NIH_FOA.investigator.uid from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            rs = stat.executeQuery();

            if (rs.next()) {
                uid = rs.getInt(1);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Investigator iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("NIH_FOA.investigator");
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
            return "uid";
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
                uid = rs.getInt(1);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Investigator");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JspTagException("Error: JDBC error ending Investigator iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        uid = 0;
        parentEntities = new Vector<NIHFOATagLibTagSupport>();

        this.rs = null;
        this.stat = null;
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



	public int getUid () {
		return uid;
	}

	public void setUid (int uid) {
		this.uid = uid;
	}

	public int getActualUid () {
		return uid;
	}
}

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
import edu.uiowa.nihfoa.foaType.FoaType;
import edu.uiowa.nihfoa.guideDoc.GuideDoc;

@SuppressWarnings("serial")
public class ActivityCodeIterator extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String code = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Log log = LogFactory.getLog(ActivityCodeIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useFoaType = false;
   boolean useGuideDoc = false;

	public static String activityCodeCountByFoaType(String code) throws JspTagException {
		int count = 0;
		ActivityCodeIterator theIterator = new ActivityCodeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.activity_code where 1=1"
						+ " and code = ?"
						);

			stat.setString(1,code);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating ActivityCode iterator", e);
			throw new JspTagException("Error: JDBC error generating ActivityCode iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean foaTypeHasActivityCode(String code) throws JspTagException {
		return ! activityCodeCountByFoaType(code).equals("0");
	}

	public static String activityCodeCountByGuideDoc(String ID) throws JspTagException {
		int count = 0;
		ActivityCodeIterator theIterator = new ActivityCodeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.activity_code where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating ActivityCode iterator", e);
			throw new JspTagException("Error: JDBC error generating ActivityCode iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean guideDocHasActivityCode(String ID) throws JspTagException {
		return ! activityCodeCountByGuideDoc(ID).equals("0");
	}

	public static Boolean activityCodeExists (String ID, String code) throws JspTagException {
		int count = 0;
		ActivityCodeIterator theIterator = new ActivityCodeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.activity_code where 1=1"
						+ " and id = ?"
						+ " and code = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			stat.setString(2,code);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating ActivityCode iterator", e);
			throw new JspTagException("Error: JDBC error generating ActivityCode iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean foaTypeGuideDocExists (String code, String ID) throws JspTagException {
		int count = 0;
		ActivityCodeIterator theIterator = new ActivityCodeIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.activity_code where 1=1"
						+ " and code = ?"
						+ " and id = ?"
						);

			stat.setString(1,code);
			stat.setInt(2,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating ActivityCode iterator", e);
			throw new JspTagException("Error: JDBC error generating ActivityCode iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

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


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (code == null ? "" : " and code = ?")
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        +  generateLimitCriteria());
            if (code != null) stat.setString(webapp_keySeq++, code);
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT NIH_FOA.activity_code.id, NIH_FOA.activity_code.code from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (code == null ? "" : " and code = ?")
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (code != null) stat.setString(webapp_keySeq++, code);
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            rs = stat.executeQuery();

            if (rs.next()) {
                ID = rs.getInt(1);
                code = rs.getString(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating ActivityCode iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating ActivityCode iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("NIH_FOA.activity_code");
       if (useFoaType)
          theBuffer.append(", NIH_FOA.foa_type");
       if (useGuideDoc)
          theBuffer.append(", NIH_FOA.guide_doc");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useFoaType)
          theBuffer.append(" and foa_type.code = activity_code.null");
       if (useGuideDoc)
          theBuffer.append(" and guide_doc.ID = activity_code.null");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "id,code";
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
                code = rs.getString(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across ActivityCode", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across ActivityCode");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending ActivityCode iterator",e);
            throw new JspTagException("Error: JDBC error ending ActivityCode iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        ID = 0;
        code = null;
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


   public boolean getUseFoaType() {
        return useFoaType;
    }

    public void setUseFoaType(boolean useFoaType) {
        this.useFoaType = useFoaType;
    }

   public boolean getUseGuideDoc() {
        return useGuideDoc;
    }

    public void setUseGuideDoc(boolean useGuideDoc) {
        this.useGuideDoc = useGuideDoc;
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

package edu.uiowa.nihfoa.partIc;


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
import edu.uiowa.nihfoa.nihIc.NihIc;

@SuppressWarnings("serial")
public class PartIcIterator extends NIHFOATagLibBodyTagSupport {
    int ID = 0;
    String ic = null;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Log log = LogFactory.getLog(PartIcIterator.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useGuideDoc = false;
   boolean useNihIc = false;

	public static String partIcCountByGuideDoc(String ID) throws JspTagException {
		int count = 0;
		PartIcIterator theIterator = new PartIcIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.part_ic where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating PartIc iterator", e);
			throw new JspTagException("Error: JDBC error generating PartIc iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean guideDocHasPartIc(String ID) throws JspTagException {
		return ! partIcCountByGuideDoc(ID).equals("0");
	}

	public static String partIcCountByNihIc(String ic) throws JspTagException {
		int count = 0;
		PartIcIterator theIterator = new PartIcIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.part_ic where 1=1"
						+ " and ic = ?"
						);

			stat.setString(1,ic);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating PartIc iterator", e);
			throw new JspTagException("Error: JDBC error generating PartIc iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean nihIcHasPartIc(String ic) throws JspTagException {
		return ! partIcCountByNihIc(ic).equals("0");
	}

	public static Boolean partIcExists (String ID, String ic) throws JspTagException {
		int count = 0;
		PartIcIterator theIterator = new PartIcIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.part_ic where 1=1"
						+ " and id = ?"
						+ " and ic = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			stat.setString(2,ic);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating PartIc iterator", e);
			throw new JspTagException("Error: JDBC error generating PartIc iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean guideDocNihIcExists (String ID, String ic) throws JspTagException {
		int count = 0;
		PartIcIterator theIterator = new PartIcIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.part_ic where 1=1"
						+ " and id = ?"
						+ " and ic = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			stat.setString(2,ic);
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			log.error("JDBC error generating PartIc iterator", e);
			throw new JspTagException("Error: JDBC error generating PartIc iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

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


      try {
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        + (ic == null ? "" : " and ic = ?")
                                                        +  generateLimitCriteria());
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (ic != null) stat.setString(webapp_keySeq++, ic);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT NIH_FOA.part_ic.id, NIH_FOA.part_ic.ic from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        + (ic == null ? "" : " and ic = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (ic != null) stat.setString(webapp_keySeq++, ic);
            rs = stat.executeQuery();

            if (rs.next()) {
                ID = rs.getInt(1);
                ic = rs.getString(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating PartIc iterator: " + stat.toString(), e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating PartIc iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("NIH_FOA.part_ic");
       if (useGuideDoc)
          theBuffer.append(", NIH_FOA.guide_doc");
       if (useNihIc)
          theBuffer.append(", NIH_FOA.nih_ic");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useGuideDoc)
          theBuffer.append(" and guide_doc.ID = part_ic.null");
       if (useNihIc)
          theBuffer.append(" and nih_ic.ic = part_ic.null");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "id,ic";
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
                ic = rs.getString(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across PartIc", e);
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across PartIc");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            log.error("JDBC error ending PartIc iterator",e);
            throw new JspTagException("Error: JDBC error ending PartIc iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        ID = 0;
        ic = null;
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


   public boolean getUseGuideDoc() {
        return useGuideDoc;
    }

    public void setUseGuideDoc(boolean useGuideDoc) {
        this.useGuideDoc = useGuideDoc;
    }

   public boolean getUseNihIc() {
        return useNihIc;
    }

    public void setUseNihIc(boolean useNihIc) {
        this.useNihIc = useNihIc;
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

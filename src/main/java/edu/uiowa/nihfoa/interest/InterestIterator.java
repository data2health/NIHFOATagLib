package edu.uiowa.nihfoa.interest;


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
import edu.uiowa.nihfoa.investigator.Investigator;

@SuppressWarnings("serial")

public class InterestIterator extends NIHFOATagLibBodyTagSupport {
    int uid = 0;
    int ID = 0;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Log log =LogFactory.getLog(Interest.class);


    PreparedStatement stat = null;
    ResultSet rs = null;
    String sortCriteria = null;
    int limitCriteria = 0;
    String var = null;
    int rsCount = 0;

   boolean useGuideDoc = false;
   boolean useInvestigator = false;

	public static String interestCountByGuideDoc(String ID) throws JspTagException {
		int count = 0;
		InterestIterator theIterator = new InterestIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.interest where 1=1"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error generating Interest iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean guideDocHasInterest(String ID) throws JspTagException {
		return ! interestCountByGuideDoc(ID).equals("0");
	}

	public static String interestCountByInvestigator(String uid) throws JspTagException {
		int count = 0;
		InterestIterator theIterator = new InterestIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.interest where 1=1"
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
			throw new JspTagException("Error: JDBC error generating Interest iterator");
		} finally {
			theIterator.freeConnection();
		}
		return "" + count;
	}

	public static Boolean investigatorHasInterest(String uid) throws JspTagException {
		return ! interestCountByInvestigator(uid).equals("0");
	}

	public static Boolean interestExists (String uid, String ID) throws JspTagException {
		int count = 0;
		InterestIterator theIterator = new InterestIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.interest where 1=1"
						+ " and uid = ?"
						+ " and id = ?"
						);

			stat.setInt(1,Integer.parseInt(uid));
			stat.setInt(2,Integer.parseInt(ID));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error generating Interest iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

	public static Boolean guideDocInvestigatorExists (String ID, String uid) throws JspTagException {
		int count = 0;
		InterestIterator theIterator = new InterestIterator();
		try {
			PreparedStatement stat = theIterator.getConnection().prepareStatement("SELECT count(*) from NIH_FOA.interest where 1=1"
						+ " and id = ?"
						+ " and uid = ?"
						);

			stat.setInt(1,Integer.parseInt(ID));
			stat.setInt(2,Integer.parseInt(uid));
			ResultSet crs = stat.executeQuery();

			if (crs.next()) {
				count = crs.getInt(1);
			}
			stat.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error generating Interest iterator");
		} finally {
			theIterator.freeConnection();
		}
		return count > 0;
	}

    public int doStartTag() throws JspException {
		GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
		if (theGuideDoc!= null)
			parentEntities.addElement(theGuideDoc);
		Investigator theInvestigator = (Investigator)findAncestorWithClass(this, Investigator.class);
		if (theInvestigator!= null)
			parentEntities.addElement(theInvestigator);

		if (theGuideDoc == null) {
		} else {
			ID = theGuideDoc.getID();
		}
		if (theInvestigator == null) {
		} else {
			uid = theInvestigator.getUid();
		}


      try {
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT NIH_FOA.interest.uid, NIH_FOA.interest.id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        + (uid == 0 ? "" : " and uid = ?")
                                                        + " order by " + generateSortCriteria() + generateLimitCriteria());
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (uid != 0) stat.setInt(webapp_keySeq++, uid);
            rs = stat.executeQuery();

            if (rs.next()) {
                uid = rs.getInt(1);
                ID = rs.getInt(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error generating Interest iterator: " + stat.toString());
        }

        return SKIP_BODY;
    }

    private String generateFromClause() {
       StringBuffer theBuffer = new StringBuffer("NIH_FOA.interest");
       if (useGuideDoc)
          theBuffer.append(", NIH_FOA.guide_doc");
       if (useInvestigator)
          theBuffer.append(", NIH_FOA.investigator");

      return theBuffer.toString();
    }

    private String generateJoinCriteria() {
       StringBuffer theBuffer = new StringBuffer();
       if (useGuideDoc)
          theBuffer.append(" and guide_doc.ID = interest.null");
       if (useInvestigator)
          theBuffer.append(" and investigator.uid = interest.null");

      return theBuffer.toString();
    }

    private String generateSortCriteria() {
        if (sortCriteria != null) {
            return sortCriteria;
        } else {
            return "uid,id";
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
                ID = rs.getInt(2);
                pageContext.setAttribute(var, ++rsCount);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            clearServiceState();
            freeConnection();
            throw new JspTagException("Error: JDBC error iterating across Interest");
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
            rs.close();
            stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new JspTagException("Error: JDBC error ending Interest iterator");
        } finally {
            clearServiceState();
            freeConnection();
        }
        return super.doEndTag();
    }

    private void clearServiceState() {
        uid = 0;
        ID = 0;
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

   public boolean getUseInvestigator() {
        return useInvestigator;
    }

    public void setUseInvestigator(boolean useInvestigator) {
        this.useInvestigator = useInvestigator;
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

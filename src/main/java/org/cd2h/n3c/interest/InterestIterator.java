package org.cd2h.n3c.interest;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.cd2h.n3c.NIHFOATagLibTagSupport;
import org.cd2h.n3c.NIHFOATagLibBodyTagSupport;
import org.cd2h.n3c.guideDoc.GuideDoc;
import org.cd2h.n3c.investigator.Investigator;

@SuppressWarnings("serial")
public class InterestIterator extends NIHFOATagLibBodyTagSupport {
    int uid = 0;
    int ID = 0;
	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	private static final Logger log = LogManager.getLogger(InterestIterator.class);


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
			log.error("JDBC error generating Interest iterator", e);
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
			log.error("JDBC error generating Interest iterator", e);
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
			log.error("JDBC error generating Interest iterator", e);
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
			log.error("JDBC error generating Interest iterator", e);
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
            //run count query  
            int webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT count(*) from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        + (uid == 0 ? "" : " and uid = ?")
                                                        + generateLimitCriteria());
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (uid != 0) stat.setInt(webapp_keySeq++, uid);
            rs = stat.executeQuery();

            if (rs.next()) {
                pageContext.setAttribute(var+"Total", rs.getInt(1));
            }


            //run select id query  
            webapp_keySeq = 1;
            stat = getConnection().prepareStatement("SELECT NIH_FOA.interest.uid, NIH_FOA.interest.id from " + generateFromClause() + " where 1=1"
                                                        + generateJoinCriteria()
                                                        + (ID == 0 ? "" : " and id = ?")
                                                        + (uid == 0 ? "" : " and uid = ?")
                                                        + " order by " + generateSortCriteria()  +  generateLimitCriteria());
            if (ID != 0) stat.setInt(webapp_keySeq++, ID);
            if (uid != 0) stat.setInt(webapp_keySeq++, uid);
            rs = stat.executeQuery();

            if ( rs != null && rs.next() ) {
                uid = rs.getInt(1);
                ID = rs.getInt(2);
                if (var != null)
                    pageContext.setAttribute(var, this);
                return EVAL_BODY_INCLUDE;
            }
        } catch (SQLException e) {
            log.error("JDBC error generating Interest iterator: " + stat, e);

			freeConnection();
			clearServiceState();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: JDBC error generating Interest iterator: " + stat);
				return parent.doEndTag();
			}else{
				throw new JspException("Error: JDBC error generating Interest iterator: " + stat,e);
			}

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
          theBuffer.append(" and NIH_FOA.guide_doc.id = NIH_FOA.interest.id");
       if (useInvestigator)
          theBuffer.append(" and NIH_FOA.investigator.uid = NIH_FOA.interest.uid");

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

    public int doAfterBody() throws JspException {
        try {
            if ( rs != null && rs.next() ) {
                uid = rs.getInt(1);
                ID = rs.getInt(2);
                return EVAL_BODY_AGAIN;
            }
        } catch (SQLException e) {
            log.error("JDBC error iterating across Interest", e);

			freeConnection();
			clearServiceState();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "JDBC error iterating across Interest" + stat.toString());
				return parent.doEndTag();
			}else{
				throw new JspException("JDBC error iterating across Interest",e);
			}

        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspTagException, JspException {
        try {
			if( var != null )
				pageContext.removeAttribute(var);
			if( pageContext != null ){
				Boolean error = (Boolean) pageContext.getAttribute("tagError");
				if( error != null && error ){

					freeConnection();
					clearServiceState();

					Exception e = null; // (Exception) pageContext.getAttribute("tagErrorException");
					String message = null; // (String) pageContext.getAttribute("tagErrorMessage");

					if(pageContext != null){
						e = (Exception) pageContext.getAttribute("tagErrorException");
						message = (String) pageContext.getAttribute("tagErrorMessage");

					}
					Tag parent = getParent();
					if(parent != null){
						return parent.doEndTag();
					}else if(e != null && message != null){
						throw new JspException(message,e);
					}else if(parent == null && pageContext != null){
						pageContext.removeAttribute("tagError");
						pageContext.removeAttribute("tagErrorException");
						pageContext.removeAttribute("tagErrorMessage");
					}
				}
			}

            if( rs != null ){
                rs.close();
            }

            if( stat != null ){
                stat.close();
            }

        } catch ( SQLException e ) {
            log.error("JDBC error ending Interest iterator",e);
			freeConnection();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "JDBC error retrieving uid " + uid);
				return parent.doEndTag();
			}else{
				throw new JspException("Error: JDBC error ending Interest iterator",e);
			}

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

    public Boolean isFirst() throws SQLException {
        return rs.isFirst();
    }

    public Boolean isLast() throws SQLException {
        return rs.isLast();
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

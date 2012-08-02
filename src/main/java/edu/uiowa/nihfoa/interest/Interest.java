package edu.uiowa.nihfoa.interest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.nihfoa.guideDoc.GuideDoc;
import edu.uiowa.nihfoa.investigator.Investigator;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.Sequence;

@SuppressWarnings("serial")
public class Interest extends NIHFOATagLibTagSupport {

	static Interest currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Interest.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int uid = 0;
	int ID = 0;

	private String var = null;

	private Interest cachedInterest = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
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

			InterestIterator theInterestIterator = (InterestIterator)findAncestorWithClass(this, InterestIterator.class);

			if (theInterestIterator != null) {
				uid = theInterestIterator.getUid();
				ID = theInterestIterator.getID();
			}

			if (theInterestIterator == null && theGuideDoc == null && theInvestigator == null && uid == 0) {
				// no uid was provided - the default is to assume that it is a new Interest and to generate a new uid
				uid = Sequence.generateID();
				insertEntity();
			} else if (theInterestIterator == null && theGuideDoc != null && theInvestigator == null) {
				// an uid was provided as an attribute - we need to load a Interest from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select uid from NIH_FOA.interest where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (uid == 0)
						uid = rs.getInt(1);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (theInterestIterator == null && theGuideDoc == null && theInvestigator != null) {
				// an uid was provided as an attribute - we need to load a Interest from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select id from NIH_FOA.interest where uid = ?");
				stmt.setInt(1,uid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (ID == 0)
						ID = rs.getInt(1);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or uid was provided as an attribute - we need to load a Interest from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from NIH_FOA.interest where uid = ? and id = ?");
				stmt.setInt(1,uid);
				stmt.setInt(2,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			log.error("JDBC error retrieving uid " + uid, e);
			throw new JspTagException("Error: JDBC error retrieving uid " + uid);
		} finally {
			freeConnection();
		}

		Interest currentInterest = (Interest) pageContext.getAttribute("tag_interest");
		if(currentInterest != null){
			cachedInterest = currentInterest;
		}
		currentInterest = this;
		pageContext.setAttribute((var == null ? "tag_interest" : var), currentInterest);

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(this.cachedInterest != null){
			pageContext.setAttribute((var == null ? "tag_interest" : var), this.cachedInterest);
		}else{
			pageContext.removeAttribute((var == null ? "tag_interest" : var));
			this.cachedInterest = null;
		}

		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.interest set where uid = ? and id = ?");
				stmt.setInt(1,uid);
				stmt.setInt(2,ID);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException {
		try {
			PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.interest(uid,id) values (?,?)");
			stmt.setInt(1,uid);
			stmt.setInt(2,ID);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
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

	public String getVar () {
		return var;
	}

	public void setVar (String var) {
		this.var = var;
	}

	public String getActualVar () {
		return var;
	}

	public static Integer uidValue() throws JspException {
		try {
			return currentInstance.getUid();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function uidValue()");
		}
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	private void clearServiceState () {
		uid = 0;
		ID = 0;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();
		this.var = null;

	}

}

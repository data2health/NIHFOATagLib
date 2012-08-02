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
import edu.uiowa.nihfoa.Sequence;

@SuppressWarnings("serial")
public class Investigator extends NIHFOATagLibTagSupport {

	static Investigator currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Investigator.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int uid = 0;
	String mode = null;
	Date lastCheck = null;

	private String var = null;

	private Investigator cachedInvestigator = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			InvestigatorIterator theInvestigatorIterator = (InvestigatorIterator)findAncestorWithClass(this, InvestigatorIterator.class);

			if (theInvestigatorIterator != null) {
				uid = theInvestigatorIterator.getUid();
			}

			if (theInvestigatorIterator == null && uid == 0) {
				// no uid was provided - the default is to assume that it is a new Investigator and to generate a new uid
				uid = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or uid was provided as an attribute - we need to load a Investigator from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select mode,last_check from NIH_FOA.investigator where uid = ?");
				stmt.setInt(1,uid);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (mode == null)
						mode = rs.getString(1);
					if (lastCheck == null)
						lastCheck = rs.getTimestamp(2);
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

		Investigator currentInvestigator = (Investigator) pageContext.getAttribute("tag_investigator");
		if(currentInvestigator != null){
			cachedInvestigator = currentInvestigator;
		}
		currentInvestigator = this;
		pageContext.setAttribute((var == null ? "tag_investigator" : var), currentInvestigator);

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(this.cachedInvestigator != null){
			pageContext.setAttribute((var == null ? "tag_investigator" : var), this.cachedInvestigator);
		}else{
			pageContext.removeAttribute((var == null ? "tag_investigator" : var));
			this.cachedInvestigator = null;
		}

		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.investigator set mode = ?, last_check = ? where uid = ?");
				stmt.setString(1,mode);
				stmt.setTimestamp(2,lastCheck == null ? null : new java.sql.Timestamp(lastCheck.getTime()));
				stmt.setInt(3,uid);
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
			if (uid == 0) {
				uid = Sequence.generateID();
				log.debug("generating new Investigator " + uid);
			}

			if (mode == null)
				mode = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.investigator(uid,mode,last_check) values (?,?,?)");
			stmt.setInt(1,uid);
			stmt.setString(2,mode);
			stmt.setTimestamp(3,lastCheck == null ? null : new java.sql.Timestamp(lastCheck.getTime()));
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

	public String getMode () {
		if (commitNeeded)
			return "";
		else
			return mode;
	}

	public void setMode (String mode) {
		this.mode = mode;
		commitNeeded = true;
	}

	public String getActualMode () {
		return mode;
	}

	public Date getLastCheck () {
		return lastCheck;
	}

	public void setLastCheck (Date lastCheck) {
		this.lastCheck = lastCheck;
		commitNeeded = true;
	}

	public Date getActualLastCheck () {
		return lastCheck;
	}

	public void setLastCheckToNow ( ) {
		this.lastCheck = new java.util.Date();
		commitNeeded = true;
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

	public static String modeValue() throws JspException {
		try {
			return currentInstance.getMode();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function modeValue()");
		}
	}

	public static Date lastCheckValue() throws JspException {
		try {
			return currentInstance.getLastCheck();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function lastCheckValue()");
		}
	}

	private void clearServiceState () {
		uid = 0;
		mode = null;
		lastCheck = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();
		this.var = null;

	}

}

package org.cd2h.n3c.investigator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Timestamp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;


import org.cd2h.n3c.NIHFOATagLibTagSupport;
import org.cd2h.n3c.Sequence;

@SuppressWarnings("serial")
public class Investigator extends NIHFOATagLibTagSupport {

	static Investigator currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Logger log = LogManager.getLogger(Investigator.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int uid = 0;
	String mode = null;
	Timestamp lastCheck = null;

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

			freeConnection();
			clearServiceState();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "JDBC error retrieving uid " + uid);
				return parent.doEndTag();
			}else{
				throw new JspException("JDBC error retrieving uid " + uid,e);
			}

		} finally {
			freeConnection();
		}

		if(pageContext != null){
			Investigator currentInvestigator = (Investigator) pageContext.getAttribute("tag_investigator");
			if(currentInvestigator != null){
				cachedInvestigator = currentInvestigator;
			}
			currentInvestigator = this;
			pageContext.setAttribute((var == null ? "tag_investigator" : var), currentInvestigator);
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(pageContext != null){
			if(this.cachedInvestigator != null){
				pageContext.setAttribute((var == null ? "tag_investigator" : var), this.cachedInvestigator);
			}else{
				pageContext.removeAttribute((var == null ? "tag_investigator" : var));
				this.cachedInvestigator = null;
			}
		}

		try {
			Boolean error = null; // (Boolean) pageContext.getAttribute("tagError");
			if(pageContext != null){
				error = (Boolean) pageContext.getAttribute("tagError");
			}

			if(error != null && error){

				freeConnection();
				clearServiceState();

				Exception e = (Exception) pageContext.getAttribute("tagErrorException");
				String message = (String) pageContext.getAttribute("tagErrorMessage");

				Tag parent = getParent();
				if(parent != null){
					return parent.doEndTag();
				}else if(e != null && message != null){
					throw new JspException(message,e);
				}else if(parent == null){
					pageContext.removeAttribute("tagError");
					pageContext.removeAttribute("tagErrorException");
					pageContext.removeAttribute("tagErrorMessage");
				}
			}
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.investigator set mode = ?, last_check = ? where uid = ? ");
				stmt.setString( 1, mode );
				stmt.setTimestamp( 2, lastCheck );
				stmt.setInt(3,uid);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);

			freeConnection();
			clearServiceState();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "Error: IOException while writing to the user");
				return parent.doEndTag();
			}else{
				throw new JspTagException("Error: IOException while writing to the user");
			}

		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException, SQLException {
		if (uid == 0) {
			uid = Sequence.generateID();
			log.debug("generating new Investigator " + uid);
		}

		if (mode == null){
			mode = "";
		}
		PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.investigator(uid,mode,last_check) values (?,?,?)");
		stmt.setInt(1,uid);
		stmt.setString(2,mode);
		stmt.setTimestamp(3,lastCheck);
		stmt.executeUpdate();
		stmt.close();
		freeConnection();
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

	public Timestamp getLastCheck () {
		return lastCheck;
	}

	public void setLastCheck (Timestamp lastCheck) {
		this.lastCheck = lastCheck;
		commitNeeded = true;
	}

	public Timestamp getActualLastCheck () {
		return lastCheck;
	}

	public void setLastCheckToNow ( ) {
		this.lastCheck = new java.sql.Timestamp(new java.util.Date().getTime());
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

	public static Timestamp lastCheckValue() throws JspException {
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

package org.cd2h.n3c.activityCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.cd2h.n3c.foaType.FoaType;
import org.cd2h.n3c.guideDoc.GuideDoc;

import org.cd2h.n3c.NIHFOATagLibTagSupport;
import org.cd2h.n3c.Sequence;

@SuppressWarnings("serial")
public class ActivityCode extends NIHFOATagLibTagSupport {

	static ActivityCode currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Logger log = LogManager.getLogger(ActivityCode.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int ID = 0;
	String code = null;

	private String var = null;

	private ActivityCode cachedActivityCode = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
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

			ActivityCodeIterator theActivityCodeIterator = (ActivityCodeIterator)findAncestorWithClass(this, ActivityCodeIterator.class);

			if (theActivityCodeIterator != null) {
				ID = theActivityCodeIterator.getID();
				code = theActivityCodeIterator.getCode();
			}

			if (theActivityCodeIterator == null && theFoaType == null && theGuideDoc == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new ActivityCode and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else if (theActivityCodeIterator == null && theFoaType != null && theGuideDoc == null) {
				// an ID was provided as an attribute - we need to load a ActivityCode from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select id from NIH_FOA.activity_code where code = ?");
				stmt.setString(1,code);
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
			} else if (theActivityCodeIterator == null && theFoaType == null && theGuideDoc != null) {
				// an ID was provided as an attribute - we need to load a ActivityCode from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select code from NIH_FOA.activity_code where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (code == null)
						code = rs.getString(1);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else {
				// an iterator or ID was provided as an attribute - we need to load a ActivityCode from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from NIH_FOA.activity_code where id = ? and code = ?");
				stmt.setInt(1,ID);
				stmt.setString(2,code);
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
			log.error("JDBC error retrieving ID " + ID, e);

			freeConnection();
			clearServiceState();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "JDBC error retrieving ID " + ID);
				return parent.doEndTag();
			}else{
				throw new JspException("JDBC error retrieving ID " + ID,e);
			}

		} finally {
			freeConnection();
		}

		if(pageContext != null){
			ActivityCode currentActivityCode = (ActivityCode) pageContext.getAttribute("tag_activityCode");
			if(currentActivityCode != null){
				cachedActivityCode = currentActivityCode;
			}
			currentActivityCode = this;
			pageContext.setAttribute((var == null ? "tag_activityCode" : var), currentActivityCode);
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(pageContext != null){
			if(this.cachedActivityCode != null){
				pageContext.setAttribute((var == null ? "tag_activityCode" : var), this.cachedActivityCode);
			}else{
				pageContext.removeAttribute((var == null ? "tag_activityCode" : var));
				this.cachedActivityCode = null;
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
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.activity_code set where id = ?  and code = ? ");
				stmt.setInt(1,ID);
				stmt.setString(2,code);
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
		PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.activity_code(id,code) values (?,?)");
		stmt.setInt(1,ID);
		stmt.setString(2,code);
		stmt.executeUpdate();
		stmt.close();
		freeConnection();
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
		if (commitNeeded)
			return "";
		else
			return code;
	}

	public void setCode (String code) {
		this.code = code;
	}

	public String getActualCode () {
		return code;
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

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String codeValue() throws JspException {
		try {
			return currentInstance.getCode();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function codeValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		code = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();
		this.var = null;

	}

}

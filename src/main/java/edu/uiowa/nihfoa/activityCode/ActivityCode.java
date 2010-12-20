package edu.uiowa.nihfoa.activityCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.nihfoa.guideDoc.GuideDoc;
import edu.uiowa.nihfoa.foaType.FoaType;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.Sequence;

@SuppressWarnings("serial")

public class ActivityCode extends NIHFOATagLibTagSupport {

	static ActivityCode currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log =LogFactory.getLog(ActivityCode.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int ID = 0;
	String code = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (theGuideDoc!= null)
				parentEntities.addElement(theGuideDoc);
			FoaType theFoaType = (FoaType)findAncestorWithClass(this, FoaType.class);
			if (theFoaType!= null)
				parentEntities.addElement(theFoaType);

			if (theGuideDoc == null) {
			} else {
				ID = theGuideDoc.getID();
			}
			if (theFoaType == null) {
			} else {
				code = theFoaType.getCode();
			}

			ActivityCodeIterator theActivityCodeIterator = (ActivityCodeIterator)findAncestorWithClass(this, ActivityCodeIterator.class);

			if (theActivityCodeIterator != null) {
				ID = theActivityCodeIterator.getID();
				code = theActivityCodeIterator.getCode();
			}

			if (theActivityCodeIterator == null && theGuideDoc == null && theFoaType == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new ActivityCode and to generate a new ID
				ID = Sequence.generateID();
				log.debug("generating new ActivityCode " + ID);
				insertEntity();
			} else if (theActivityCodeIterator == null && theGuideDoc != null && theFoaType == null) {
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
			} else if (theActivityCodeIterator == null && theGuideDoc == null && theFoaType != null) {
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
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error retrieving ID " + ID);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.activity_code set where id = ? and code = ?");
				stmt.setInt(1,ID);
				stmt.setString(2,code);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			clearServiceState();
			freeConnection();
		}
		return super.doEndTag();
	}

	public void insertEntity() throws JspException {
		try {
			PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.activity_code(id,code) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,code);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
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

	public static int IDValue() throws JspException {
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

	}

}

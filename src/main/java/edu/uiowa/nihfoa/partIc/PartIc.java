package edu.uiowa.nihfoa.partIc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import edu.uiowa.nihfoa.guideDoc.GuideDoc;
import edu.uiowa.nihfoa.nihIc.NihIc;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.Sequence;

@SuppressWarnings("serial")
public class PartIc extends NIHFOATagLibTagSupport {

	static PartIc currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(PartIc.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int ID = 0;
	String ic = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
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

			PartIcIterator thePartIcIterator = (PartIcIterator)findAncestorWithClass(this, PartIcIterator.class);

			if (thePartIcIterator != null) {
				ID = thePartIcIterator.getID();
				ic = thePartIcIterator.getIc();
			}

			if (thePartIcIterator == null && theGuideDoc == null && theNihIc == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new PartIc and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else if (thePartIcIterator == null && theGuideDoc != null && theNihIc == null) {
				// an ID was provided as an attribute - we need to load a PartIc from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select ic from NIH_FOA.part_ic where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (ic == null)
						ic = rs.getString(1);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			} else if (thePartIcIterator == null && theGuideDoc == null && theNihIc != null) {
				// an ID was provided as an attribute - we need to load a PartIc from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select id from NIH_FOA.part_ic where ic = ?");
				stmt.setString(1,ic);
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
				// an iterator or ID was provided as an attribute - we need to load a PartIc from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select 1 from NIH_FOA.part_ic where id = ? and ic = ?");
				stmt.setInt(1,ID);
				stmt.setString(2,ic);
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
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.part_ic set where id = ? and ic = ?");
				stmt.setInt(1,ID);
				stmt.setString(2,ic);
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
			PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.part_ic(id,ic) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,ic);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			log.error("Error: IOException while writing to the user", e);
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

	public String getIc () {
		if (commitNeeded)
			return "";
		else
			return ic;
	}

	public void setIc (String ic) {
		this.ic = ic;
	}

	public String getActualIc () {
		return ic;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String icValue() throws JspException {
		try {
			return currentInstance.getIc();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function icValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		ic = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();

	}

}

package org.cd2h.n3c.partIc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.cd2h.n3c.nihIc.NihIc;
import org.cd2h.n3c.guideDoc.GuideDoc;

import org.cd2h.n3c.NIHFOATagLibTagSupport;
import org.cd2h.n3c.Sequence;

@SuppressWarnings("serial")
public class PartIc extends NIHFOATagLibTagSupport {

	static PartIc currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Logger log = LogManager.getLogger(PartIc.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int ID = 0;
	String ic = null;

	private String var = null;

	private PartIc cachedPartIc = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {
			NihIc theNihIc = (NihIc)findAncestorWithClass(this, NihIc.class);
			if (theNihIc!= null)
				parentEntities.addElement(theNihIc);
			GuideDoc theGuideDoc = (GuideDoc)findAncestorWithClass(this, GuideDoc.class);
			if (theGuideDoc!= null)
				parentEntities.addElement(theGuideDoc);

			if (theNihIc == null) {
			} else {
				ic = theNihIc.getIc();
			}
			if (theGuideDoc == null) {
			} else {
				ID = theGuideDoc.getID();
			}

			PartIcIterator thePartIcIterator = (PartIcIterator)findAncestorWithClass(this, PartIcIterator.class);

			if (thePartIcIterator != null) {
				ID = thePartIcIterator.getID();
				ic = thePartIcIterator.getIc();
			}

			if (thePartIcIterator == null && theNihIc == null && theGuideDoc == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new PartIc and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else if (thePartIcIterator == null && theNihIc != null && theGuideDoc == null) {
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
			} else if (thePartIcIterator == null && theNihIc == null && theGuideDoc != null) {
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
			PartIc currentPartIc = (PartIc) pageContext.getAttribute("tag_partIc");
			if(currentPartIc != null){
				cachedPartIc = currentPartIc;
			}
			currentPartIc = this;
			pageContext.setAttribute((var == null ? "tag_partIc" : var), currentPartIc);
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(pageContext != null){
			if(this.cachedPartIc != null){
				pageContext.setAttribute((var == null ? "tag_partIc" : var), this.cachedPartIc);
			}else{
				pageContext.removeAttribute((var == null ? "tag_partIc" : var));
				this.cachedPartIc = null;
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
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.part_ic set where id = ?  and ic = ? ");
				stmt.setInt(1,ID);
				stmt.setString(2,ic);
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
		PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.part_ic(id,ic) values (?,?)");
		stmt.setInt(1,ID);
		stmt.setString(2,ic);
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
		this.var = null;

	}

}

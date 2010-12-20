package edu.uiowa.nihfoa.nihIc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")

public class NihIc extends NIHFOATagLibTagSupport {

	static NihIc currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log =LogFactory.getLog(NihIc.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	String ic = null;
	String title = null;
	String description = null;
	String logoLink = null;
	String infoLink = null;
	String category = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			NihIcIterator theNihIcIterator = (NihIcIterator)findAncestorWithClass(this, NihIcIterator.class);

			if (theNihIcIterator != null) {
				ic = theNihIcIterator.getIc();
			}

			if (theNihIcIterator == null && ic == null) {
				// no ic was provided - the default is to assume that it is a new NihIc and to generate a new ic
				ic = null;
				log.debug("generating new NihIc " + ic);
				insertEntity();
			} else {
				// an iterator or ic was provided as an attribute - we need to load a NihIc from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select title,description,logo_link,info_link,category from NIH_FOA.nih_ic where ic = ?");
				stmt.setString(1,ic);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (title == null)
						title = rs.getString(1);
					if (description == null)
						description = rs.getString(2);
					if (logoLink == null)
						logoLink = rs.getString(3);
					if (infoLink == null)
						infoLink = rs.getString(4);
					if (category == null)
						category = rs.getString(5);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error retrieving ic " + ic);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.nih_ic set title = ?, description = ?, logo_link = ?, info_link = ?, category = ? where ic = ?");
				stmt.setString(1,title);
				stmt.setString(2,description);
				stmt.setString(3,logoLink);
				stmt.setString(4,infoLink);
				stmt.setString(5,category);
				stmt.setString(6,ic);
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
			if (title == null)
				title = "";
			if (description == null)
				description = "";
			if (logoLink == null)
				logoLink = "";
			if (infoLink == null)
				infoLink = "";
			if (category == null)
				category = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.nih_ic(ic,title,description,logo_link,info_link,category) values (?,?,?,?,?,?)");
			stmt.setString(1,ic);
			stmt.setString(2,title);
			stmt.setString(3,description);
			stmt.setString(4,logoLink);
			stmt.setString(5,infoLink);
			stmt.setString(6,category);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
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

	public String getTitle () {
		if (commitNeeded)
			return "";
		else
			return title;
	}

	public void setTitle (String title) {
		this.title = title;
		commitNeeded = true;
	}

	public String getActualTitle () {
		return title;
	}

	public String getDescription () {
		if (commitNeeded)
			return "";
		else
			return description;
	}

	public void setDescription (String description) {
		this.description = description;
		commitNeeded = true;
	}

	public String getActualDescription () {
		return description;
	}

	public String getLogoLink () {
		if (commitNeeded)
			return "";
		else
			return logoLink;
	}

	public void setLogoLink (String logoLink) {
		this.logoLink = logoLink;
		commitNeeded = true;
	}

	public String getActualLogoLink () {
		return logoLink;
	}

	public String getInfoLink () {
		if (commitNeeded)
			return "";
		else
			return infoLink;
	}

	public void setInfoLink (String infoLink) {
		this.infoLink = infoLink;
		commitNeeded = true;
	}

	public String getActualInfoLink () {
		return infoLink;
	}

	public String getCategory () {
		if (commitNeeded)
			return "";
		else
			return category;
	}

	public void setCategory (String category) {
		this.category = category;
		commitNeeded = true;
	}

	public String getActualCategory () {
		return category;
	}

	public static String icValue() throws JspException {
		try {
			return currentInstance.getIc();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function icValue()");
		}
	}

	public static String titleValue() throws JspException {
		try {
			return currentInstance.getTitle();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function titleValue()");
		}
	}

	public static String descriptionValue() throws JspException {
		try {
			return currentInstance.getDescription();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function descriptionValue()");
		}
	}

	public static String logoLinkValue() throws JspException {
		try {
			return currentInstance.getLogoLink();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function logoLinkValue()");
		}
	}

	public static String infoLinkValue() throws JspException {
		try {
			return currentInstance.getInfoLink();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function infoLinkValue()");
		}
	}

	public static String categoryValue() throws JspException {
		try {
			return currentInstance.getCategory();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function categoryValue()");
		}
	}

	private void clearServiceState () {
		ic = null;
		title = null;
		description = null;
		logoLink = null;
		infoLink = null;
		category = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();

	}

}

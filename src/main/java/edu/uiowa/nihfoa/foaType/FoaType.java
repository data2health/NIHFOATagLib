package edu.uiowa.nihfoa.foaType;

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

public class FoaType extends NIHFOATagLibTagSupport {

	static FoaType currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log =LogFactory.getLog(FoaType.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	String code = null;
	String category = null;
	String title = null;
	String description = null;
	String infoLink = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			FoaTypeIterator theFoaTypeIterator = (FoaTypeIterator)findAncestorWithClass(this, FoaTypeIterator.class);

			if (theFoaTypeIterator != null) {
				code = theFoaTypeIterator.getCode();
			}

			if (theFoaTypeIterator == null && code == null) {
				// no code was provided - the default is to assume that it is a new FoaType and to generate a new code
				code = null;
				log.debug("generating new FoaType " + code);
				insertEntity();
			} else {
				// an iterator or code was provided as an attribute - we need to load a FoaType from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select category,title,description,info_link from NIH_FOA.foa_type where code = ?");
				stmt.setString(1,code);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (category == null)
						category = rs.getString(1);
					if (title == null)
						title = rs.getString(2);
					if (description == null)
						description = rs.getString(3);
					if (infoLink == null)
						infoLink = rs.getString(4);
					found = true;
				}
				stmt.close();

				if (!found) {
					insertEntity();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: JDBC error retrieving code " + code);
		} finally {
			freeConnection();
		}
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;
		try {
			if (commitNeeded) {
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.foa_type set category = ?, title = ?, description = ?, info_link = ? where code = ?");
				stmt.setString(1,category);
				stmt.setString(2,title);
				stmt.setString(3,description);
				stmt.setString(4,infoLink);
				stmt.setString(5,code);
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
			if (category == null)
				category = "";
			if (title == null)
				title = "";
			if (description == null)
				description = "";
			if (infoLink == null)
				infoLink = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.foa_type(code,category,title,description,info_link) values (?,?,?,?,?)");
			stmt.setString(1,code);
			stmt.setString(2,category);
			stmt.setString(3,title);
			stmt.setString(4,description);
			stmt.setString(5,infoLink);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new JspTagException("Error: IOException while writing to the user");
		} finally {
			freeConnection();
		}
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

	public static String codeValue() throws JspException {
		try {
			return currentInstance.getCode();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function codeValue()");
		}
	}

	public static String categoryValue() throws JspException {
		try {
			return currentInstance.getCategory();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function categoryValue()");
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

	public static String infoLinkValue() throws JspException {
		try {
			return currentInstance.getInfoLink();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function infoLinkValue()");
		}
	}

	private void clearServiceState () {
		code = null;
		category = null;
		title = null;
		description = null;
		infoLink = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();

	}

}

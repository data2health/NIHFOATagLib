package org.cd2h.n3c.nihIc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;


import org.cd2h.n3c.NIHFOATagLibTagSupport;

@SuppressWarnings("serial")
public class NihIc extends NIHFOATagLibTagSupport {

	static NihIc currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Logger log = LogManager.getLogger(NihIc.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	String ic = null;
	String title = null;
	String description = null;
	String logoLink = null;
	String infoLink = null;
	String category = null;

	private String var = null;

	private NihIc cachedNihIc = null;

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
			log.error("JDBC error retrieving ic " + ic, e);

			freeConnection();
			clearServiceState();

			Tag parent = getParent();
			if(parent != null){
				pageContext.setAttribute("tagError", true);
				pageContext.setAttribute("tagErrorException", e);
				pageContext.setAttribute("tagErrorMessage", "JDBC error retrieving ic " + ic);
				return parent.doEndTag();
			}else{
				throw new JspException("JDBC error retrieving ic " + ic,e);
			}

		} finally {
			freeConnection();
		}

		if(pageContext != null){
			NihIc currentNihIc = (NihIc) pageContext.getAttribute("tag_nihIc");
			if(currentNihIc != null){
				cachedNihIc = currentNihIc;
			}
			currentNihIc = this;
			pageContext.setAttribute((var == null ? "tag_nihIc" : var), currentNihIc);
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(pageContext != null){
			if(this.cachedNihIc != null){
				pageContext.setAttribute((var == null ? "tag_nihIc" : var), this.cachedNihIc);
			}else{
				pageContext.removeAttribute((var == null ? "tag_nihIc" : var));
				this.cachedNihIc = null;
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
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.nih_ic set title = ?, description = ?, logo_link = ?, info_link = ?, category = ? where ic = ? ");
				stmt.setString( 1, title );
				stmt.setString( 2, description );
				stmt.setString( 3, logoLink );
				stmt.setString( 4, infoLink );
				stmt.setString( 5, category );
				stmt.setString(6,ic);
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
		if (title == null){
			title = "";
		}
		if (description == null){
			description = "";
		}
		if (logoLink == null){
			logoLink = "";
		}
		if (infoLink == null){
			infoLink = "";
		}
		if (category == null){
			category = "";
		}
		PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.nih_ic(ic,title,description,logo_link,info_link,category) values (?,?,?,?,?,?)");
		stmt.setString(1,ic);
		stmt.setString(2,title);
		stmt.setString(3,description);
		stmt.setString(4,logoLink);
		stmt.setString(5,infoLink);
		stmt.setString(6,category);
		stmt.executeUpdate();
		stmt.close();
		freeConnection();
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

	public String getVar () {
		return var;
	}

	public void setVar (String var) {
		this.var = var;
	}

	public String getActualVar () {
		return var;
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
		this.var = null;

	}

}

package edu.uiowa.nihfoa.content;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import edu.uiowa.nihfoa.NIHFOATagLibTagSupport;
import edu.uiowa.nihfoa.Sequence;

@SuppressWarnings("serial")
public class Content extends NIHFOATagLibTagSupport {

	static Content currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Log log = LogFactory.getLog(Content.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int ID = 0;
	String html = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			ContentIterator theContentIterator = (ContentIterator)findAncestorWithClass(this, ContentIterator.class);

			if (theContentIterator != null) {
				ID = theContentIterator.getID();
			}

			if (theContentIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new Content and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a Content from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select html from NIH_FOA.content where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (html == null)
						html = rs.getString(1);
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
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.content set html = ? where id = ?");
				stmt.setString(1,html);
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
			if (ID == 0) {
				ID = Sequence.generateID();
				log.debug("generating new Content " + ID);
			}

			if (html == null)
				html = "";
			PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.content(id,html) values (?,?)");
			stmt.setInt(1,ID);
			stmt.setString(2,html);
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

	public String getHtml () {
		if (commitNeeded)
			return "";
		else
			return html;
	}

	public void setHtml (String html) {
		this.html = html;
		commitNeeded = true;
	}

	public String getActualHtml () {
		return html;
	}

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String htmlValue() throws JspException {
		try {
			return currentInstance.getHtml();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function htmlValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		html = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();

	}

}

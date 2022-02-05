package org.cd2h.n3c.guideDoc;

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
public class GuideDoc extends NIHFOATagLibTagSupport {

	static GuideDoc currentInstance = null;
	boolean commitNeeded = false;
	boolean newRecord = false;

	private static final Logger log = LogManager.getLogger(GuideDoc.class);

	Vector<NIHFOATagLibTagSupport> parentEntities = new Vector<NIHFOATagLibTagSupport>();

	int ID = 0;
	String primaryIc = null;
	String title = null;
	String purpose = null;
	String relNote = null;
	String faDirectCosts = null;
	String guideLink = null;
	Timestamp relDate = null;
	String intentDate = null;
	String appReceiptDate = null;
	Timestamp lard = null;
	String fileName = null;
	String docType = null;
	String docNum = null;
	Timestamp expirationDate = null;

	private String var = null;

	private GuideDoc cachedGuideDoc = null;

	public int doStartTag() throws JspException {
		currentInstance = this;
		try {


			GuideDocIterator theGuideDocIterator = (GuideDocIterator)findAncestorWithClass(this, GuideDocIterator.class);

			if (theGuideDocIterator != null) {
				ID = theGuideDocIterator.getID();
			}

			if (theGuideDocIterator == null && ID == 0) {
				// no ID was provided - the default is to assume that it is a new GuideDoc and to generate a new ID
				ID = Sequence.generateID();
				insertEntity();
			} else {
				// an iterator or ID was provided as an attribute - we need to load a GuideDoc from the database
				boolean found = false;
				PreparedStatement stmt = getConnection().prepareStatement("select primary_ic,title,purpose,rel_note,fa_direct_costs,guide_link,rel_date,intent_date,app_receipt_date,lard,file_name,doc_type,doc_num,expiration_date from NIH_FOA.guide_doc where id = ?");
				stmt.setInt(1,ID);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					if (primaryIc == null)
						primaryIc = rs.getString(1);
					if (title == null)
						title = rs.getString(2);
					if (purpose == null)
						purpose = rs.getString(3);
					if (relNote == null)
						relNote = rs.getString(4);
					if (faDirectCosts == null)
						faDirectCosts = rs.getString(5);
					if (guideLink == null)
						guideLink = rs.getString(6);
					if (relDate == null)
						relDate = rs.getTimestamp(7);
					if (intentDate == null)
						intentDate = rs.getString(8);
					if (appReceiptDate == null)
						appReceiptDate = rs.getString(9);
					if (lard == null)
						lard = rs.getTimestamp(10);
					if (fileName == null)
						fileName = rs.getString(11);
					if (docType == null)
						docType = rs.getString(12);
					if (docNum == null)
						docNum = rs.getString(13);
					if (expirationDate == null)
						expirationDate = rs.getTimestamp(14);
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
			GuideDoc currentGuideDoc = (GuideDoc) pageContext.getAttribute("tag_guideDoc");
			if(currentGuideDoc != null){
				cachedGuideDoc = currentGuideDoc;
			}
			currentGuideDoc = this;
			pageContext.setAttribute((var == null ? "tag_guideDoc" : var), currentGuideDoc);
		}

		return EVAL_PAGE;
	}

	public int doEndTag() throws JspException {
		currentInstance = null;

		if(pageContext != null){
			if(this.cachedGuideDoc != null){
				pageContext.setAttribute((var == null ? "tag_guideDoc" : var), this.cachedGuideDoc);
			}else{
				pageContext.removeAttribute((var == null ? "tag_guideDoc" : var));
				this.cachedGuideDoc = null;
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
				PreparedStatement stmt = getConnection().prepareStatement("update NIH_FOA.guide_doc set primary_ic = ?, title = ?, purpose = ?, rel_note = ?, fa_direct_costs = ?, guide_link = ?, rel_date = ?, intent_date = ?, app_receipt_date = ?, lard = ?, file_name = ?, doc_type = ?, doc_num = ?, expiration_date = ? where id = ? ");
				stmt.setString( 1, primaryIc );
				stmt.setString( 2, title );
				stmt.setString( 3, purpose );
				stmt.setString( 4, relNote );
				stmt.setString( 5, faDirectCosts );
				stmt.setString( 6, guideLink );
				stmt.setTimestamp( 7, relDate );
				stmt.setString( 8, intentDate );
				stmt.setString( 9, appReceiptDate );
				stmt.setTimestamp( 10, lard );
				stmt.setString( 11, fileName );
				stmt.setString( 12, docType );
				stmt.setString( 13, docNum );
				stmt.setTimestamp( 14, expirationDate );
				stmt.setInt(15,ID);
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
		if (ID == 0) {
			ID = Sequence.generateID();
			log.debug("generating new GuideDoc " + ID);
		}

		if (primaryIc == null){
			primaryIc = "";
		}
		if (title == null){
			title = "";
		}
		if (purpose == null){
			purpose = "";
		}
		if (relNote == null){
			relNote = "";
		}
		if (faDirectCosts == null){
			faDirectCosts = "";
		}
		if (guideLink == null){
			guideLink = "";
		}
		if (intentDate == null){
			intentDate = "";
		}
		if (appReceiptDate == null){
			appReceiptDate = "";
		}
		if (fileName == null){
			fileName = "";
		}
		if (docType == null){
			docType = "";
		}
		if (docNum == null){
			docNum = "";
		}
		PreparedStatement stmt = getConnection().prepareStatement("insert into NIH_FOA.guide_doc(id,primary_ic,title,purpose,rel_note,fa_direct_costs,guide_link,rel_date,intent_date,app_receipt_date,lard,file_name,doc_type,doc_num,expiration_date) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		stmt.setInt(1,ID);
		stmt.setString(2,primaryIc);
		stmt.setString(3,title);
		stmt.setString(4,purpose);
		stmt.setString(5,relNote);
		stmt.setString(6,faDirectCosts);
		stmt.setString(7,guideLink);
		stmt.setTimestamp(8,relDate);
		stmt.setString(9,intentDate);
		stmt.setString(10,appReceiptDate);
		stmt.setTimestamp(11,lard);
		stmt.setString(12,fileName);
		stmt.setString(13,docType);
		stmt.setString(14,docNum);
		stmt.setTimestamp(15,expirationDate);
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

	public String getPrimaryIc () {
		if (commitNeeded)
			return "";
		else
			return primaryIc;
	}

	public void setPrimaryIc (String primaryIc) {
		this.primaryIc = primaryIc;
		commitNeeded = true;
	}

	public String getActualPrimaryIc () {
		return primaryIc;
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

	public String getPurpose () {
		if (commitNeeded)
			return "";
		else
			return purpose;
	}

	public void setPurpose (String purpose) {
		this.purpose = purpose;
		commitNeeded = true;
	}

	public String getActualPurpose () {
		return purpose;
	}

	public String getRelNote () {
		if (commitNeeded)
			return "";
		else
			return relNote;
	}

	public void setRelNote (String relNote) {
		this.relNote = relNote;
		commitNeeded = true;
	}

	public String getActualRelNote () {
		return relNote;
	}

	public String getFaDirectCosts () {
		if (commitNeeded)
			return "";
		else
			return faDirectCosts;
	}

	public void setFaDirectCosts (String faDirectCosts) {
		this.faDirectCosts = faDirectCosts;
		commitNeeded = true;
	}

	public String getActualFaDirectCosts () {
		return faDirectCosts;
	}

	public String getGuideLink () {
		if (commitNeeded)
			return "";
		else
			return guideLink;
	}

	public void setGuideLink (String guideLink) {
		this.guideLink = guideLink;
		commitNeeded = true;
	}

	public String getActualGuideLink () {
		return guideLink;
	}

	public Timestamp getRelDate () {
		return relDate;
	}

	public void setRelDate (Timestamp relDate) {
		this.relDate = relDate;
		commitNeeded = true;
	}

	public Timestamp getActualRelDate () {
		return relDate;
	}

	public void setRelDateToNow ( ) {
		this.relDate = new java.sql.Timestamp(new java.util.Date().getTime());
		commitNeeded = true;
	}

	public String getIntentDate () {
		if (commitNeeded)
			return "";
		else
			return intentDate;
	}

	public void setIntentDate (String intentDate) {
		this.intentDate = intentDate;
		commitNeeded = true;
	}

	public String getActualIntentDate () {
		return intentDate;
	}

	public String getAppReceiptDate () {
		if (commitNeeded)
			return "";
		else
			return appReceiptDate;
	}

	public void setAppReceiptDate (String appReceiptDate) {
		this.appReceiptDate = appReceiptDate;
		commitNeeded = true;
	}

	public String getActualAppReceiptDate () {
		return appReceiptDate;
	}

	public Timestamp getLard () {
		return lard;
	}

	public void setLard (Timestamp lard) {
		this.lard = lard;
		commitNeeded = true;
	}

	public Timestamp getActualLard () {
		return lard;
	}

	public void setLardToNow ( ) {
		this.lard = new java.sql.Timestamp(new java.util.Date().getTime());
		commitNeeded = true;
	}

	public String getFileName () {
		if (commitNeeded)
			return "";
		else
			return fileName;
	}

	public void setFileName (String fileName) {
		this.fileName = fileName;
		commitNeeded = true;
	}

	public String getActualFileName () {
		return fileName;
	}

	public String getDocType () {
		if (commitNeeded)
			return "";
		else
			return docType;
	}

	public void setDocType (String docType) {
		this.docType = docType;
		commitNeeded = true;
	}

	public String getActualDocType () {
		return docType;
	}

	public String getDocNum () {
		if (commitNeeded)
			return "";
		else
			return docNum;
	}

	public void setDocNum (String docNum) {
		this.docNum = docNum;
		commitNeeded = true;
	}

	public String getActualDocNum () {
		return docNum;
	}

	public Timestamp getExpirationDate () {
		return expirationDate;
	}

	public void setExpirationDate (Timestamp expirationDate) {
		this.expirationDate = expirationDate;
		commitNeeded = true;
	}

	public Timestamp getActualExpirationDate () {
		return expirationDate;
	}

	public void setExpirationDateToNow ( ) {
		this.expirationDate = new java.sql.Timestamp(new java.util.Date().getTime());
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

	public static Integer IDValue() throws JspException {
		try {
			return currentInstance.getID();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function IDValue()");
		}
	}

	public static String primaryIcValue() throws JspException {
		try {
			return currentInstance.getPrimaryIc();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function primaryIcValue()");
		}
	}

	public static String titleValue() throws JspException {
		try {
			return currentInstance.getTitle();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function titleValue()");
		}
	}

	public static String purposeValue() throws JspException {
		try {
			return currentInstance.getPurpose();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function purposeValue()");
		}
	}

	public static String relNoteValue() throws JspException {
		try {
			return currentInstance.getRelNote();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function relNoteValue()");
		}
	}

	public static String faDirectCostsValue() throws JspException {
		try {
			return currentInstance.getFaDirectCosts();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function faDirectCostsValue()");
		}
	}

	public static String guideLinkValue() throws JspException {
		try {
			return currentInstance.getGuideLink();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function guideLinkValue()");
		}
	}

	public static Timestamp relDateValue() throws JspException {
		try {
			return currentInstance.getRelDate();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function relDateValue()");
		}
	}

	public static String intentDateValue() throws JspException {
		try {
			return currentInstance.getIntentDate();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function intentDateValue()");
		}
	}

	public static String appReceiptDateValue() throws JspException {
		try {
			return currentInstance.getAppReceiptDate();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function appReceiptDateValue()");
		}
	}

	public static Timestamp lardValue() throws JspException {
		try {
			return currentInstance.getLard();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function lardValue()");
		}
	}

	public static String fileNameValue() throws JspException {
		try {
			return currentInstance.getFileName();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function fileNameValue()");
		}
	}

	public static String docTypeValue() throws JspException {
		try {
			return currentInstance.getDocType();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function docTypeValue()");
		}
	}

	public static String docNumValue() throws JspException {
		try {
			return currentInstance.getDocNum();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function docNumValue()");
		}
	}

	public static Timestamp expirationDateValue() throws JspException {
		try {
			return currentInstance.getExpirationDate();
		} catch (Exception e) {
			 throw new JspTagException("Error in tag function expirationDateValue()");
		}
	}

	private void clearServiceState () {
		ID = 0;
		primaryIc = null;
		title = null;
		purpose = null;
		relNote = null;
		faDirectCosts = null;
		guideLink = null;
		relDate = null;
		intentDate = null;
		appReceiptDate = null;
		lard = null;
		fileName = null;
		docType = null;
		docNum = null;
		expirationDate = null;
		newRecord = false;
		commitNeeded = false;
		parentEntities = new Vector<NIHFOATagLibTagSupport>();
		this.var = null;

	}

}

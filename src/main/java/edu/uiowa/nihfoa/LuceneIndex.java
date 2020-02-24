package edu.uiowa.nihfoa;

import javax.naming.InitialContext;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.jsp.JspException;
import javax.sql.DataSource;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

@SuppressWarnings("serial")
public class LuceneIndex extends NIHFOATagLibTagSupport {
    boolean truncate = false;
    String lucenePath = null;
    IndexWriter theWriter = null;
    DataSource theDataSource = null;
    int documentCount = 0;

    public int doStartTag() throws JspException {
	try {
	    if (truncate) {
		theWriter = new IndexWriter(FSDirectory.open(new File(lucenePath)),
			new IndexWriterConfig(Version.LUCENE_43, new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_43)));
		for (int i = 0; i < theWriter.maxDoc(); i++)
		    theWriter.deleteAll();
		theWriter.close();
	    }
	    theWriter = new IndexWriter(FSDirectory.open(new File(lucenePath)),
		    new IndexWriterConfig(Version.LUCENE_43, new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_43)));
	    theDataSource = (DataSource) new InitialContext().lookup("java:/comp/env/jdbc/NIHFOATagLib");

	    loadIndex();

	    theWriter.close();
	    pageContext.getOut().print("NIH_FOA document count: " + documentCount);
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return SKIP_BODY;

    }

    public int doEndTag() throws JspException {
	clearServiceState();
	return super.doEndTag();
    }

    @SuppressWarnings("deprecation")
    public void loadIndex() throws Exception {
	Connection conn = null;
	try {
	    conn = getDataSource().getConnection();
	    PreparedStatement stmt = conn.prepareStatement("select id,title,purpose from NIH_FOA.guide_doc order by id");
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
		documentCount++;
		int ID = rs.getInt(1);
		String title = rs.getString(2);
		String purpose = rs.getString(3);
		// System.out.println("ID: " + ID + "\ttitle: " + title +
		// "\tpurpose:" + purpose);
		PreparedStatement contentStmt = conn.prepareStatement("select html from NIH_FOA.content where id = ?");
		contentStmt.setInt(1, ID);
		ResultSet crs = contentStmt.executeQuery();
		while (crs.next()) {
		    String content = crs.getString(1);
		    Document theDocument = new Document();
		    theDocument.add(new Field("id", ID + "", Field.Store.YES, Field.Index.NOT_ANALYZED));

		    theDocument.add(new Field("title", title, Field.Store.NO, Field.Index.ANALYZED));

		    theDocument.add(new Field("purpose", title, Field.Store.NO, Field.Index.ANALYZED));
		    theDocument.add(new Field("purpose", purpose, Field.Store.NO, Field.Index.ANALYZED));

		    theDocument.add(new Field("content", title, Field.Store.NO, Field.Index.ANALYZED));
		    theDocument.add(new Field("content", purpose, Field.Store.NO, Field.Index.ANALYZED));
		    theDocument.add(new Field("content", content, Field.Store.NO, Field.Index.ANALYZED));
		    theWriter.addDocument(theDocument);
		}
		contentStmt.close();
	    }
	    stmt.close();
	} catch (RuntimeException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (conn != null)
		    conn.close();
	    } catch (RuntimeException e) {
		e.printStackTrace();
	    }
	}
    }

    public boolean getTruncate() {
	return truncate;
    }

    public void setTruncate(boolean truncate) {
	this.truncate = truncate;
    }

    public String getLucenePath() {
	return lucenePath;
    }

    public void setLucenePath(String lucenePath) {
	this.lucenePath = lucenePath;
    }

    private void clearServiceState() {
	truncate = false;
	lucenePath = null;
	theWriter = null;
	theDataSource = null;
	documentCount = 0;
    }
}

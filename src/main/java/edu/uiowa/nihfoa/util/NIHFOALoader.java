package edu.uiowa.nihfoa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.XMLReader;

import edu.uiowa.util.PropertyLoader;

public class NIHFOALoader {
    static Logger logger = Logger.getLogger(NIHFOALoader.class);
    static Properties prop_file = PropertyLoader.loadProperties("cd2h");
    static XMLReader xr = null;
    static Connection conn = null;

    static String baseURL = "https://grants.nih.gov/web_services/XML/NIH_All_Active_FOAs.xml";

    public static void main(String[] args) throws Exception {
	PropertyConfigurator.configure(args[0]);
	conn = getConnection();

	PreparedStatement stmt = conn.prepareStatement("truncate nih_foa.guide_doc cascade");
	stmt.execute();
	stmt.close();

	processFile(baseURL);
	
	fetchContent();
    }

    @SuppressWarnings("unchecked")
    static void processFile(String file) throws Exception {
	logger.info("processing " + file);
	SAXReader reader = new SAXReader(false);
	Document document = reader.read((new InputStreamReader((new URL(file)).openConnection().getInputStream())));

	logger.info("document: " + document.asXML());
	for (Node guideDoc : (List<Node>) document.selectNodes("dataroot/GuideDocs")) {
	    int ID = Integer.parseInt(guideDoc.selectSingleNode("rowid").getText());
	    String primaryIC = guideDoc.selectSingleNode("PrimaryIC").getText();
	    String title = guideDoc.selectSingleNode("Title").getText();
	    String purpose = guideDoc.selectSingleNode("Purpose").getText();
	    String relNote = guideDoc.selectSingleNode("RelNote").getText();
	    String faDirectCosts = guideDoc.selectSingleNode("FA_DirectCosts").getText();
	    String guideLink = guideDoc.selectSingleNode("GuideLink").getText();
	    String relDate = guideDoc.selectSingleNode("RelDate").getText();
	    String intentDate = guideDoc.selectSingleNode("IntentDate").getText();
	    String appReceiptDate = guideDoc.selectSingleNode("AppReceiptDate").getText();
	    String lard = guideDoc.selectSingleNode("LARD").getText();
	    String fileName = guideDoc.selectSingleNode("FileName").getText();
	    String docType = guideDoc.selectSingleNode("DocType").getText();
	    String docNum = guideDoc.selectSingleNode("DocNum").getText();
	    String expirationDate = guideDoc.selectSingleNode("ExpirationDate").getText();
	    logger.info("id: " + ID + "\tdocType: " + docType);
	    logger.info("\tprimaryIC: " + primaryIC);
	    logger.info("\ttitle: " + title);

	    PreparedStatement stmt = conn.prepareStatement("insert into nih_foa.guide_doc values(?,?,?,?,?,?,?,?::date,?,?,?::date,?,?,?,?::date)");
	    stmt.setInt(1, ID);
	    stmt.setString(2, primaryIC);
	    stmt.setString(3, title);
	    stmt.setString(4, purpose);
	    stmt.setString(5, relNote);
	    stmt.setString(6, faDirectCosts);
	    stmt.setString(7, guideLink);
	    stmt.setString(8, relDate);
	    stmt.setString(9, intentDate);
	    stmt.setString(10, appReceiptDate);
	    stmt.setString(11, lard);
	    stmt.setString(12, fileName);
	    stmt.setString(13, docType);
	    stmt.setString(14, docNum);
	    stmt.setString(15, expirationDate);
	    stmt.execute();
	    stmt.close();

	    // PartIC
	    for (Node partICNode : (List<Node>) guideDoc.selectNodes("PartIC")) {
		String partIC = partICNode.getText();
		logger.info("\tpartIC: " + partIC);
		PreparedStatement substmt = conn.prepareStatement("insert into nih_foa.part_ic values(?,?)");
		substmt.setInt(1, ID);
		substmt.setString(2, partIC);
		substmt.execute();
		substmt.close();
	    }

	    // ActivityCodes
	    for (Node activityCodeNode : (List<Node>) guideDoc.selectNodes("ActivityCodes")) {
		StringTokenizer theTokenizer = new StringTokenizer(activityCodeNode.getText(), " ,/");
		while (theTokenizer.hasMoreTokens()) {
		    String activityCode = theTokenizer.nextToken();
		    logger.info("\tactivityCode: " + activityCode);
		    PreparedStatement substmt = conn.prepareStatement("insert into nih_foa.activity_code values(?,?)");
		    substmt.setInt(1, ID);
		    substmt.setString(2, activityCode);
		    substmt.execute();
		    substmt.close();
		}
	    }
	}
    }

    static void fetchContent() throws SQLException, MalformedURLException, IOException {
	logger.info("\nNew FOAs:\n");

	PreparedStatement pruneStmt = conn.prepareStatement("delete from nih_foa.content where id not in (select id from nih_foa.guide_doc)");
	pruneStmt.executeUpdate();
	pruneStmt.close();

	PreparedStatement stmt = conn.prepareStatement("select id,guide_link from nih_foa.guide_doc where id not in (select id from nih_foa.content)");
	ResultSet rs = stmt.executeQuery();
	while (rs.next()) {
	    int id = rs.getInt(1);
	    URL url = new URL(rs.getString(2));
	    if (url.getProtocol().equals("http"))
		url = new URL("https" + url.toString().substring(4));
	    logger.info("fetching ID: " + id + "\tURL: " + url);

	    try {
		BufferedReader IODesc = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()), 200000);
		StringBuffer content = new StringBuffer();
		String buffer = null;
		while ((buffer = IODesc.readLine()) != null)
		    content.append(buffer + "\n");
		logger.debug("ID: " + id + "\tcontent: " + content);

		PreparedStatement contentStmt = conn.prepareStatement("insert into nih_foa.content values (?,?)");
		contentStmt.setInt(1, id);
		contentStmt.setString(2, content.toString());
		contentStmt.executeUpdate();
		contentStmt.close();
	    } catch (Exception e) {
		logger.error("\t*** error fetching " + url);
	    }
	}
	stmt.close();
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
	Class.forName("org.postgresql.Driver");
	Properties props = new Properties();
	props.setProperty("user", prop_file.getProperty("jdbc.user"));
	props.setProperty("password", prop_file.getProperty("jdbc.password"));
	// if (use_ssl.equals("true")) {
	// props.setProperty("sslfactory",
	// "org.postgresql.ssl.NonValidatingFactory");
	// props.setProperty("ssl", "true");
	// }
	Connection conn = DriverManager.getConnection(prop_file.getProperty("jdbc.url"), props);
	// conn.setAutoCommit(false);
	return conn;
    }

}

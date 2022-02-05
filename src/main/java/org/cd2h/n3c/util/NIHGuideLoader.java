package org.cd2h.n3c.util;

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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.XMLReader;

public class NIHGuideLoader {
	private static final Logger logger = LogManager.getLogger(NIHGuideLoader.class);
	static Properties prop_file = PropertyLoader.loadProperties("cd2h");
	static XMLReader xr = null;
	static Connection conn = null;

	static String baseURL = "https://grants.nih.gov/grants/guide/newsfeed/fundingopps.xml";

	public static void main(String[] args) throws Exception {
		conn = getConnection();

		processFile(baseURL);
	}

	@SuppressWarnings("unchecked")
	static void processFile(String file) throws Exception {
		logger.info("processing " + file);
		SAXReader reader = new SAXReader(false);
		Document document = reader.read((new InputStreamReader((new URL(file)).openConnection().getInputStream())));

		for (Node guideDoc : (List<Node>) document.selectNodes("rss/channel/item")) {
			logger.info("doc: " + guideDoc.selectSingleNode("title").getText());
			PreparedStatement stmt = conn.prepareStatement("insert into nih_foa.raw_rss values(?::xml);");
			stmt.setString(1, guideDoc.asXML());
			stmt.execute();
			stmt.close();
		}
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

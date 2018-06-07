package ru.job4j.jdbc.magnet;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MagnetTest {
	private File conPrp;
	private File initScript;
	private File xml;
	private File transformed;
	private File scheme;
	
	private StoreXML sx;
	
	private final String xslt  = "<?xml version=\"1.0\"?>\n" 
            + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n" 
            + "<xsl:template match=\"/\">\n" 
            + "<entries>\n" 
            + "<xsl:for-each select=\"entries/*\">" 
            + "<entry>"  
            + "<xsl:attribute name=\"field\">" 
            + "<xsl:value-of select=\"field\"/>" 
            + "</xsl:attribute>" 
            + "</entry>" 
            + "</xsl:for-each>" 
            + "</entries>\n" 
            + "</xsl:template>\n" 
            + "</xsl:stylesheet>\n";

	@Before
	public void setUp() throws Exception {
		conPrp = new File("testprp");
		Properties prty = new Properties();
		prty.setProperty("url", "jdbc:sqlite:magnettest.db");
		try (FileOutputStream fos = new FileOutputStream(conPrp);) {
			prty.store(fos, "Properties for test");
		}
		
		initScript = new File("initScript.sql");
		try (FileOutputStream fos = new FileOutputStream(initScript);) {
			String query =  "CREATE TABLE IF NOT EXISTS entry (field integer);";
			fos.write(query.getBytes());
		}
		
			StoreSQL ss = new StoreSQL(conPrp.getName());
			ss.executeSqlScript(initScript, ";");
			ss.clearTable();
			ss.generate(2);
			
			xml = new File("entries.xml");
			sx = new StoreXML(xml);
			
			sx.save(ss.selectAll());

		
		transformed = new File("transformed.xml");
		scheme = new File("testscheme.xsl");
		try (FileOutputStream fos = new FileOutputStream(scheme);) {
			fos.write(xslt.getBytes());
			sx.convert(xml, transformed, scheme);
		}
	}
	
	@Test
	public void xmlMarshallingTest() {
		String expected = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" 
		+ "<entries>" 
		+    "<entry>" 
		+        "<field>0</field>" 
		+    "</entry>" 
		+    "<entry>" 
		+        "<field>1</field>" 
		+    "</entry>" 
		+ "</entries>";
		StringBuilder result = new StringBuilder();
		 try (Scanner scan = new Scanner(xml);) {
			while (scan.hasNextLine()) {
				result.append(scan.nextLine().trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertThat(expected, is(result.toString()));
	}
	
	@Test
	public void xsltTransformTest() {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" 
				 + "<entries>" 
				 +	"<entry field=\"0\"/>" 
				 +	"<entry field=\"1\"/>" 
				 + "</entries>";
		StringBuilder result = new StringBuilder();
		try (Scanner scan = new Scanner(transformed);) {
			while (scan.hasNextLine()) {
				result.append(scan.nextLine().trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		assertThat(expected, is(result.toString()));
	}
	
	@Test
	public void saxParsingTest() {
		int result = -1;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			System.setOut(new PrintStream(baos));
			sx.load(transformed);
			result = Integer.parseInt(new String(baos.toByteArray()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertThat(1, is(result)); 
	}
	
	@After
	public void tearDown() throws Exception {
		conPrp.delete();
		initScript.delete();
		xml.delete();
		transformed.delete();
		scheme.delete();
		new File("magnettest.db").delete();
	}
}

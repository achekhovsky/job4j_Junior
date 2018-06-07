package ru.job4j.jdbc.magnet;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MagnetMain {
	private static Logger log = Logger.getLogger(MagnetMain.class.getName());

	public static void main(String[] args) {
		MagnetMain mm = new MagnetMain();
		mm.init();
	}
	
	public void init() {
			StoreSQL ss = new StoreSQL("sqlitecon.property");
			ss.executeSqlScript(new File("sqliteinit.sql"), ";");
			ss.clearTable();
			System.out.println("Save to DB");
			ss.generate(100);
			
			File xml = new File("xml");
			StoreXML sx = new StoreXML(xml);
			
			System.out.println("Load from DB and save to xml");
			sx.save(ss.selectAll());
			
			File xslt = new File("xslt");
			System.out.println("Transform");
			sx.convert(xml, xslt, new File("scheme.xsl"));
			
			System.out.println("SAX parsing");
			sx.load(xslt);
	}

}

package ru.job4j.jdbc.htmlparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParserMain {
	private static Logger log = LogManager.getLogger(ParserMain.class.getName());
	private Properties prp;
	
	public ParserMain(String properties) {
		prp = this.loadProperties(properties);
	}

	public static void main(String[] args) {
		ParserMain pm = new ParserMain("vacancies.property");
		pm.init();
	}
	
	public void init() {
		ParseTimer pt = new ParseTimer(this.prp);
		pt.start();
	}
	
	private Properties loadProperties(String cfg) {
		Properties result = new Properties();
		File pFile = new File(cfg);
		try (InputStream is = new FileInputStream(pFile)) {
			result.load(is);	
		} catch (IOException e) {
			log.warn("ParserMain::loadProperties ", e);
		}
		return result; 
	}
}

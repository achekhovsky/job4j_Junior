package ru.job4j.jdbc.magnet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.job4j.jdbc.magnet.StoreXML.Entries;
import ru.job4j.jdbc.magnet.StoreXML.Entry;

public class StoreSQL {
	private static Logger log = Logger.getLogger(StoreSQL.class.getName());
	private String config;
	
	public StoreSQL(String cfg) {
		this.config = cfg;
	}
	
	public void generate(int n) {
		try (Connection conn = this.getConnection(config)) {
			conn.setAutoCommit(false);
			for (int i = 0; i < n; i++) {
				try (PreparedStatement ps = conn.prepareStatement("INSERT INTO entry (field) VALUES (?)")) {
					ps.setInt(1, i);
					ps.executeUpdate();
				} 
			}
			conn.commit();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "StoreSQL::generate", e);
		}
	}
	
	public void clearTable() {
		try (Connection conn = this.getConnection(config); Statement st = conn.createStatement()) {
			st.executeUpdate("DELETE FROM entry");
		} catch (SQLException e) {
			log.log(Level.SEVERE, "StoreSQL::clearTable", e);
		}
	}
	
	/**
	 * Executing sql script from specified file
	 * @param scriptFile - specified file with sql script
	 * @param delimiter - sign that separates requests
	 */
	public void executeSqlScript(File scriptFile, String delimiter) {
		try (Connection conn = this.getConnection(config); 
				Scanner scanner = new Scanner(scriptFile); 
				Statement currentStatement = conn.createStatement();) {
			scanner.useDelimiter(delimiter);
			while (scanner.hasNext()) {
				String statement = String.format("%s%s", scanner.next(), delimiter);
				if (!statement.trim().equals(";")) {
					currentStatement.execute(statement);
				}
			}
		} catch (FileNotFoundException | SQLException e) {
			log.log(Level.SEVERE, "StoreSQL::executeSqlScript ", e);
		}
	}
	
	/**
	 * Selected all entries from database
	 * @return all entries from database
	 */
	public Entries selectAll() {
		Entries result = new Entries();
		try (Connection conn = this.getConnection(config);
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM entry")) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					result.addEntry(new Entry(rs.getInt("field")));
				}
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "StoreSQL::selectAll", e);
		}
		return result;
	}
	
	/**
	 * Loading properties from file with specified name
	 * @param file - file name
	 * @return 
	 */
	private Properties loadProperties(String file) {
		Properties prp = new Properties();
		try (FileReader fr = new FileReader(new File(file));) {
			prp.load(fr);
		} catch (IOException e) {
			log.log(Level.SEVERE, "StoreSQL::loadProperties", e);
		}
		return prp;
	}
	
	/**
	 * Attempts to establish a connection to the database.
	 * @param config -  connection configuration file name
	 * @return
	 */
	private Connection getConnection(String config) {
		Connection conn = null;
		try {
			Properties prp = this.loadProperties(config);
			conn = DriverManager.getConnection(prp.getProperty("url"), prp);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "StoreSQL::getConnection", e);
		} 
		return conn;
	}
}

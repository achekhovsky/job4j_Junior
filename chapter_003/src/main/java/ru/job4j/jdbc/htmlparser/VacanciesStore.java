package ru.job4j.jdbc.htmlparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Saving vacancies in the database
 * @author achekhovsky
 * @version 1.0
 */
public class VacanciesStore {
	private static Logger log = LogManager.getLogger(VacanciesStore.class.getName());
	private Properties prp;
	
	public VacanciesStore(Properties cfg) {
		this.prp = cfg;
		this.initScript();
	}
	
	/**
	 * Saving vacancy in the database
	 * The saved vacancy is written to the log file
	 * @param vac
	 */
	public void save(Vacancies.Vacancy vac) {
		try (Connection conn = this.getConnection();
				PreparedStatement ps = conn.prepareStatement("INSERT INTO vacancy (url, description, pub_date) VALUES (?, ?, ?)")) {
			ps.setString(1, vac.getUrl());
			ps.setString(2, vac.getDescritpion());
			ps.setString(3, vac.getPublicationDate());
			ps.executeUpdate();
			if (log.isInfoEnabled()) {
				log.info(String.format("Vacancy: %s | %s | %s", vac.getUrl(), vac.getDescritpion(), vac.getPublicationDate()));
			}
		} catch (SQLException e) {
			log.warn("VacanciesStore::save ", e);
		}
	}
	
	/**
	 * Saving vacancies in the database
	 * @param vac
	 */
	public void saveAll(Vacancies vcs) {
		try (Connection conn = this.getConnection()) {
			conn.setAutoCommit(false);
			for (Vacancies.Vacancy vac : vcs.getVcs()) {
				this.save(vac);
			}
			conn.commit();
		} catch (SQLException e) {
			log.warn("VacanciesStore::saveAll", e);
		}
	}
	
	/**
	 * Creates a table if it does not exist
	 */
	private void initScript() {
		try (Connection conn = this.getConnection();
				Statement script = conn.createStatement()) {
			script.executeUpdate("CREATE TABLE IF NOT EXISTS vacancy (id integer, url text NOT NULL, description text, pub_date text, PRIMARY KEY (id), UNIQUE (url))");
			script.executeUpdate("DELETE FROM vacancy");
		} catch (SQLException e) {
			log.warn("VacanciesStore::initScript ", e);
		}
	}
	
	/**
	 * Attempts to establish a connection to the database.
	 * @param config -  connection configuration file name
	 * @return
	 */
	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(prp.getProperty("url"), prp);
		} catch (SQLException e) {
			log.warn("VacanciesStore::getConnection ", e);
		} 
		return conn;
	}
}

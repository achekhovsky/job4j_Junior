package ru.job4j.jdbc.tracker;

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
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackJdbc {
	private static Logger log = Logger.getLogger(TrackJdbc.class.getName());
	private Connection conn;

	public TrackJdbc(String cfg) {
		this.conn = getConnection(cfg);
	}
	
	public TrackJdbc(Connection con) {
		this.conn = con;
	}

	/**
	 * Executing sql script from specified file
	 * @param scriptFile - specified file with sql script
	 * @param delimiter - sign that separates requests
	 */
	public void executeSqlScript(File scriptFile, String delimiter) {
		try (Scanner scanner = new Scanner(scriptFile); Statement currentStatement = conn.createStatement();) {
			scanner.useDelimiter(delimiter);
			while (scanner.hasNext()) {
				String statement = String.format("%s%s", scanner.next(), delimiter);
				if (!statement.trim().equals(";")) {
					currentStatement.execute(statement);
				}
			}
		} catch (FileNotFoundException | SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::executeSqlScript ", e);
		}
	}
	
	/**
	 * Insert the item into the database
	 * @param itm - inserted item
	 */
	public void saveItem(Item itm) {
		try (PreparedStatement ps = 
				this.conn.prepareStatement("INSERT INTO items (id, name, description, created) VALUES (?, ?, ?, ?)")) {
				ps.setString(1, itm.getId());
				ps.setString(2, itm.getName());
				ps.setString(3, itm.getDescription());
				ps.setLong(4, itm.getCreated());
				ps.executeUpdate();	
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::saveItem ", e);
		}
	}
	
	/**
	 * Updating the item in the database
	 * @param itm - updated item
	 */
	public void updateItem(Item itm) {
		try (PreparedStatement ps = 
				this.conn.prepareStatement("UPDATE items SET name = ?, description = ? WHERE id = ?")) {
				ps.setString(1, itm.getName());
				ps.setString(2, itm.getDescription());
				ps.setString(3, itm.getId());
				ps.executeUpdate();	
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::updateItem ", e);
		}
	}
	
	/**
	 * Deleting the item from the database
	 * @param itm - deleted item
	 */
	public void deleteItem(Item itm) {
		try (PreparedStatement ps = 
				this.conn.prepareStatement("DELETE  FROM items WHERE id = ?")) {
				ps.setString(1, itm.getId());
				ps.executeUpdate();	
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::deleteItem ", e);
		}
	}
	
	/**
	 * Find item by id
	 * @param id - specified id
	 * @return item with specified id if it is found in the database otherwise null
	 */
	public Item findItemById(String id) {
		Item result = null;
		try (PreparedStatement ps = 
				this.conn.prepareStatement("SELECT * FROM items WHERE id = ?")) {
				ps.setString(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					result = new Item(rs.getString("name"), rs.getString("description"));
					result.setId(rs.getString("id"));
					result.setCreated(rs.getLong("created"));
				}
				rs.close();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::findItemById ", e);
		}
		return result;
	}

	/**
	 * Find items by name
	 * @param id - specified name
	 * @return items with specified name if they are found in the database otherwise null
	 */
	public ArrayList<Item> findItemsByName(String name) {
		ArrayList<Item> result = new ArrayList<>();
		try (PreparedStatement ps = 
				this.conn.prepareStatement("SELECT * FROM items WHERE name = ?")) {
				ps.setString(1, name);
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					Item itm = new Item(rs.getString("name"), rs.getString("description"));
					itm.setId(rs.getString("id"));
					itm.setCreated(rs.getLong("created"));
					result.add(itm);
				}
				rs.close();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::findItemsByName ", e);
		}
		return result;
	}	
	
	public ArrayList<Item> findAll() {
		ArrayList<Item> result = new ArrayList<>();
		try (Statement st = 
				this.conn.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM items");) {
				while (rs.next()) {
					Item itm = new Item(rs.getString("name"), rs.getString("description"));
					itm.setId(rs.getString("id"));
					itm.setCreated(rs.getLong("created"));
					result.add(itm);
				}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::findAll ", e);
		}
		return result;
	}
	
	/**
	 * Adds a comment to the item with the specified id
	 * @param id - specified id
	 * @param comment 
	 */
	public void saveComment(String id, String comment) {
		try (PreparedStatement ps = 
				this.conn.prepareStatement("INSERT INTO comments (comment, itemId) VALUES (?, ?)")) {
				ps.setString(1, id);
				ps.setString(2, comment);
				ps.executeUpdate();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::saveComment ", e);
		}
	}
	
	/**
	 * Execute specified query
	 * @param query
	 * @return ResultSet
	 */
	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try (Statement currentStatement = conn.createStatement()) {
			rs = currentStatement.executeQuery(query);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::saveComment ", e);
		}
		return rs;
	}
	
	/**
	 * Releases this connection object's database and JDBC resources
	 */
	public void closeConnection() {
		try {
			if (this.conn != null) {
				this.conn.close();
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE, "TrackJdbc::closeConnection ", e);
		}
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
			log.log(Level.SEVERE, "TrackJdbc::getConnection ", e);
		} 
		return conn;
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
			log.log(Level.SEVERE, "TrackJdbc::loadProperties ", e);
		}
		return prp;
	}
}

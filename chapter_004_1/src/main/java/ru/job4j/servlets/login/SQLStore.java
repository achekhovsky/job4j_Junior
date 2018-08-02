package ru.job4j.servlets.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class SQLStore implements Store {
	private static final Logger LOG = Logger.getLogger(SQLStore.class.getName());
	private static final SQLStore STORE = new SQLStore();
	private final DataSource source; 
	
	private SQLStore() {
		this.source = this.getSource();
		this.initTable();
	}
	
	public static SQLStore getInstance() {
		return STORE;
	}

	@Override
	public boolean add(User usr) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (id, create_date, name, email) VALUES (?, ?, ?, ?)");) {
			stmt.setInt(1, usr.getId());
			stmt.setString(2, usr.getCreateDate().toString());
			stmt.setString(3, usr.getName());
			stmt.setString(4, usr.getEmail());
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::add", e);
		}
		return true;
	}

	@Override
	public boolean update(User usr) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement("UPDATE users SET name = ?, email = ? WHERE id = ?");) {
			stmt.setString(1, usr.getName());
			stmt.setString(2, usr.getEmail());
			stmt.setInt(3, usr.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::update", e);
		}
		return true;
	}

	@Override
	public boolean delete(User usr) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");) {
			stmt.setInt(1, usr.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::update", e);
		}
		return false;
	}

	@Override
	public List<User> findAll() {
		ArrayList<User> users = new ArrayList<User>();
		try (Connection conn = this.source.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM users");) {
				while (rs.next())  {
					users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
				}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::findAll", e);
		}
		return users;
	}

	@Override
	public User findById(int id) {
		User usr = null;
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next())  {
					usr = new User(id, rs.getString("name"), rs.getString("email"));
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::findById", e);
		}
		return usr;
	}
	
	private DataSource getSource() {
		DataSource ds = null;
		try {
			InitialContext cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/SQLite/Login");
		} catch (NamingException e) {
			LOG.log(Level.SEVERE, "SQLStore::getSource", e);
		}
		return ds;
	}
	
	private void initTable() {
		try (Connection conn = this.source.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute("CREATE TABLE IF NOT EXISTS users (id integer, create_date text, name varchar(25), email varchar(30))");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "SQLStore::initTable", e);
		}
	}
}

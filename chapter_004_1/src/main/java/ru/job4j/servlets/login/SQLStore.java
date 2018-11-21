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
		this.add(new User(1, "Administrator", "admin@mail.ru", "", "", "Administrator", "123"));
	}

	public static SQLStore getInstance() {
		return STORE;
	}

	@Override
	public boolean add(User usr) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmtU = conn
						.prepareStatement("INSERT INTO users (id, create_date, name, email, country, city) VALUES (?, ?, ?, ?, ?, ?)");
				PreparedStatement stmtS = conn
						.prepareStatement("INSERT INTO secure (userId, rolename, password) VALUES (?, ?, ?)");) {
			stmtU.setInt(1, usr.getId());
			stmtU.setString(2, usr.getCreateDate().toString());
			stmtU.setString(3, usr.getName());
			stmtU.setString(4, usr.getEmail());
			stmtU.setString(5, usr.getCountry());
			stmtU.setString(6, usr.getCity());
			stmtU.executeUpdate();
			stmtS.setInt(1, usr.getId());
			stmtS.setString(2, usr.getRoleName());
			stmtS.setString(3, usr.getPassword());
			stmtS.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::add", e);
		}
		return true;
	}

	@Override
	public boolean update(User usr) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement("UPDATE users SET name = ?, email = ?, country = ?, city = ? WHERE id = ?");) {
			stmt.setString(1, usr.getName());
			stmt.setString(2, usr.getEmail());
			stmt.setString(3, usr.getCountry());
			stmt.setString(4, usr.getCity());
			stmt.setInt(5, usr.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::update", e);
		}
		return true;
	}

	@Override
	public boolean delete(User usr) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmtU = conn.prepareStatement("DELETE FROM users WHERE id = ?");
				PreparedStatement stmtS = conn.prepareStatement("DELETE FROM secure WHERE userId = ?");) {
			stmtU.setInt(1, usr.getId());
			stmtU.executeUpdate();
			stmtS.setInt(1, usr.getId());
			stmtS.executeUpdate();
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
				ResultSet rs = stmt.executeQuery(
						"SELECT users.id, users.name, users.email, users.country, users.city, secure.rolename, secure.password FROM users JOIN secure ON users.id = secure.userId");) {
			while (rs.next()) {
				users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
						 rs.getString("country"), rs.getString("city"), rs.getString("roleName"), rs.getString("password")));
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
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT users.id, users.name, users.email, users.country, users.city, secure.rolename, secure.password FROM users JOIN secure ON users.id = secure.userId AND users.id = ?");) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					usr = new User(id, rs.getString("name"), rs.getString("email"), rs.getString("country"), rs.getString("city"), rs.getString("roleName"), rs.getString("password"));
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::findById", e);
		}
		return usr;
	}

	public User authenticateUser(String name, String password) {
		User usr = null;
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT users.id, users.name, users.email, users.country, users.city, secure.rolename, secure.password FROM users JOIN secure ON users.id = secure.userId AND users.name LIKE ? AND secure.password LIKE ?");) {
			stmt.setString(1, name);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					usr = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("country"), rs.getString("city"), rs.getString("roleName"), rs.getString("password"));
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::authenticateUser", e);
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
			stmt.execute(
					"CREATE TABLE IF NOT EXISTS users (id integer PRIMARY KEY, create_date text, name varchar(25), email varchar(30), country varchar(25), city varchar(30), UNIQUE (email))");
			stmt.execute(
					"CREATE TABLE IF NOT EXISTS secure (id integer PRIMARY KEY AUTOINCREMENT, userId integer, rolename varchar(25), password varchar(30) NOT NULL, UNIQUE (userId), FOREIGN KEY (userId) REFERENCES users (id) ON UPDATE CASCADE)");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "SQLStore::initTable", e);
		}
	}
}

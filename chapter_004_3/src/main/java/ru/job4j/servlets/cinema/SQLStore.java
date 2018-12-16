package ru.job4j.servlets.cinema;

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
		this.add(new Place(11, 110, "", true));
		this.add(new Place(12, 120, "", true));
		this.add(new Place(13, 130, "", true));
		this.add(new Place(21, 210, "", true));
		this.add(new Place(22, 220, "", true));
		this.add(new Place(23, 230, "", true));
		this.add(new Place(31, 310, "", true));
		this.add(new Place(32, 320, "", true));
		this.add(new Place(33, 330, "", false));
	}

	public static SQLStore getInstance() {
		return STORE;
	}

	@Override
	public boolean add(Place plc) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmtU = conn
						.prepareStatement("INSERT INTO hall (place, price, customer, paid) VALUES (?, ?, ?, ?)")) {
			stmtU.setInt(1, plc.getPlace());
			stmtU.setInt(2, plc.getPrice());
			stmtU.setString(3, plc.getCustomer());
			stmtU.setBoolean(4, plc.isPaid());
			stmtU.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::add", e);
		}
		return true;
	}
	
	@Override
	public boolean addAccount(Account acc) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT * FROM accounts WHERE accounts.phone = ?");) {
			stmt.setString(1, acc.getPhone());
			try (ResultSet rs = stmt.executeQuery()) {
				if (!rs.next()) {
					this.insertAccount(conn, acc);
				} else {
					this.updateAccount(conn, acc);
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::addAccount", e);
		}
		return true;
	}
	
	private void insertAccount(Connection conn, Account acc) {
		try (PreparedStatement stmtU = conn.prepareStatement(
						"INSERT INTO accounts (name, phone) VALUES (?, ?)")) {
			stmtU.setString(1, acc.getName());
			stmtU.setString(2, acc.getPhone());
			stmtU.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::insertAccount", e);
		}
	} 

	private void updateAccount(Connection conn, Account acc) {
		try (PreparedStatement stmt = conn.prepareStatement(
						"UPDATE accounts SET name = ? WHERE phone = ?");) {
			stmt.setString(1, acc.getName());
			stmt.setString(2, acc.getPhone());
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::updateAccount", e);
		}
	} 
	
	@Override
	public boolean deleteAccount(Account acc) {
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmtU = conn.prepareStatement("DELETE FROM accounts WHERE phone = ?")) {
			stmtU.setString(1, acc.getPhone());
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::deleteAccount", e);
		}
		return false;
	}

	@Override
	public boolean updateHall(Place plc) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.source.getConnection();
			stmt = conn.prepareStatement(
					"UPDATE hall SET price = ?, customer = ?, paid = ? WHERE place = ?");
			conn.setAutoCommit(false);
			stmt.setInt(1, plc.getPrice());
			stmt.setString(2, plc.getCustomer());
			stmt.setBoolean(3, plc.isPaid());
			stmt.setInt(4, plc.getPlace());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOG.log(Level.SEVERE, "Rolling back data", e);
			}
			LOG.log(Level.SEVERE, "SQLStore::updateHall", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true);
					conn.close();					
				}
			} catch (SQLException e) {
				LOG.log(Level.SEVERE, "SQLStore::updateHall", e);
			}
		}
		return true;
	}
	
	@Override
	public Place findByPlaceNumber(int place) {
		Place plc = null;
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT * FROM hall WHERE hall.place = ?");) {
			stmt.setInt(1, place);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					plc = new Place(rs.getInt("place"), rs.getInt("price"), rs.getString("customer"), rs.getBoolean("paid"));
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::findByPlaceNumber", e);
		}
		return plc;
	}
	
	@Override
	public Place[] getOccupiedPlaces() {
		ArrayList<Place> plcs = new ArrayList<>();
		try (Connection conn = this.source.getConnection()) {
				Statement stmt = conn.createStatement(); 
			try (ResultSet rs = stmt.executeQuery("SELECT * FROM hall WHERE hall.paid = 0")) {
				while (rs.next()) {
					plcs.add(new Place(rs.getInt("place"), rs.getInt("price"), rs.getString("customer"), rs.getBoolean("paid")));
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::getOccupiedPlaces", e);
		}
		return plcs.toArray(new Place[plcs.size()]);
	}
	
	@Override
	public Account findByPhone(String phone) {
		Account acc = null;
		try (Connection conn = this.source.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT * FROM accounts WHERE accounts.phone = ?");) {
			stmt.setString(1, phone);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					acc = new Account(rs.getString("name"), rs.getString("phone"));
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "SQLStore::findByPhone", e);
		}
		return acc;
	}

	private DataSource getSource() {
		DataSource ds = null;
		try {
			InitialContext cxt = new InitialContext();
			ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/SQLite/Cinema");
		} catch (NamingException e) {
			LOG.log(Level.SEVERE, "SQLStore::getSource", e);
		}
		return ds;
	}

	private void initTable() {
		try (Connection conn = this.source.getConnection(); Statement stmt = conn.createStatement()) {
			stmt.execute(
					"CREATE TABLE IF NOT EXISTS hall (id integer PRIMARY KEY AUTOINCREMENT, place integer NOT NULL, price integer NOT NULL, customer varchar(25), paid boolean NOT NULL, UNIQUE (place), FOREIGN KEY (customer) REFERENCES accounts (name) ON UPDATE CASCADE)");
			stmt.execute(
					"CREATE TABLE IF NOT EXISTS accounts (id integer PRIMARY KEY AUTOINCREMENT, name varchar(25), phone varchar(15) NOT NULL, UNIQUE (phone))");
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "SQLStore::initTable", e);
		}
	}
}

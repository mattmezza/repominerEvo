package it.unisa.sesa.repominer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/remolicdev";

	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "matt";

	private static ConnectionPool INSTANCE;

	private List<Connection> pool = new ArrayList<>();

	private ConnectionPool() {
		this.fillConnectionPool(3);
	}

	public Connection getConnection() {
		if (pool.size() <= 2) {
			this.fillConnectionPool(3);
		}
		return this.pool.remove(0);
	}

	public void releaseConnection(Connection connection) {
		pool.add(connection);
	}

	public static ConnectionPool getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ConnectionPool();
		}
		return INSTANCE;
	}

	private void fillConnectionPool(int howMany) {
		for (int i = 0; i < howMany; i++) {
			Connection conn = this.createConnection();
			if (conn != null) {
				pool.add(conn);
			}
		}
	}

	private Connection createConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			return DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
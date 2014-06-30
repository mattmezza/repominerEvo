package it.unisa.sesa.repominer.db;

import it.unisa.sesa.repominer.Activator;
import it.unisa.sesa.repominer.preferences.PreferenceConstants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;

public class ConnectionPool {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static String DB_URL;

	// Database credentials
	private static String USER;
	private static String PASS;

	private static ConnectionPool INSTANCE;

	private List<Connection> pool = new ArrayList<>();

	private ConnectionPool() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String host = store.getString(PreferenceConstants.P_DBHOST);
		int port = store.getInt(PreferenceConstants.P_DBPORT);
		String dbname = store.getString(PreferenceConstants.P_DBNAME);
		String dburl = "jdbc:mysql://" + host;
		if (port != 3306) {
			dburl += ":" + port;
		}
		DB_URL = dburl + "/" + dbname;
		USER = store.getString(PreferenceConstants.P_DBUSER);
		PASS = store.getString(PreferenceConstants.P_DBPASS);
		try {
			return DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
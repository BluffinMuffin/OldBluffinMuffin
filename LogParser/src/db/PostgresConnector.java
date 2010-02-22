package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnector {

	private Connection c = null;

	@SuppressWarnings("unused")
	private PostgresConnector() {
	};

	public PostgresConnector(String serverName, String dbName, String user, String pass) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Couldn't find the driver! Check if you included the lib properly.");
			System.out.println(cnfe.getMessage());
			System.exit(1);
		}

		System.out.println("Connecting...");
		try {
			c = DriverManager.getConnection("jdbc:postgresql://" + serverName + "/" + dbName, user, pass);
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			System.exit(1);
		}

		System.out.println("Connected to database \"" + dbName + "\" on server(" + serverName + ")");
	}

	public ResultSet query(String sql) {
		Statement s = null;
		try {
			s = c.createStatement();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			System.exit(1);
		}

		ResultSet rs = null;
		try {
			rs = s.executeQuery(sql);
		} catch (SQLException se) {
			System.out.println("We got an exception while executing our query:" + "that probably means our SQL is invalid");
			System.out.println(se.getMessage());
			System.exit(1);
		}

		return rs;
	}

}

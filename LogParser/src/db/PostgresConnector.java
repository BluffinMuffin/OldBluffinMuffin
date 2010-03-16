package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PostgresConnector {

	// // EXAMPLE DB USE
	// PostgresConnector p = new PostgresConnector("srv-prj-05.dmi.usherb.ca", "BluffinWifEnum", "postgres", "27053");
	// ResultSet r = p.query("select * from domain");
	// try {
	// while (r.next()) {
	// System.out.println(r.getString(0));
	// }
	// } catch (SQLException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// System.exit(1);

	private Connection c = null;

	@SuppressWarnings("unused")
	private PostgresConnector() {
	};

	protected PostgresConnector(String serverName, String dbName, String user, String pass) {
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

		try {
			c.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Connected to database \"" + dbName + "\" on server(" + serverName + ")");
	}

	protected int rowCount(String colToCount, String table, String theRest) {
		ResultSet r = query("SELECT count(" + colToCount + ") FROM " + table + " " + theRest);
		int nbRow = 0;
		try {
			r.next();
			nbRow = r.getInt(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		return nbRow;
	}

	protected void rollback() {
		try {
			c.rollback();
		} catch (SQLException e) {
			System.out.println("ROLLBACK FAILED!");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	protected void commit() {
		try {
			c.commit();
		} catch (SQLException e) {
			System.out.println("COMMIT FAILED!");
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	protected ResultSet query(String sql) {
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

	protected PreparedStatement getPreparedStatement(String sql) {
		return getPreparedStatement(sql, true);
	}

	protected PreparedStatement getPreparedStatement(String sql, boolean returnGenerated) {
		PreparedStatement s = null;

		try {
			if (returnGenerated) {
				s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			} else {
				s = c.prepareStatement(sql);
			}
		} catch (SQLException se) {
			System.out.println(se.getMessage());
			System.exit(1);
		}

		return s;
	}
}

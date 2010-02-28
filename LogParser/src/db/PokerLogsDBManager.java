package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PokerLogsDBManager {

	private PostgresConnector db = null;
	private PreparedStatement insertPlayer = null;
	private PreparedStatement insertDomain = null;
	private PreparedStatement insertGameSet = null;
	private PreparedStatement insertGame = null;
	private PreparedStatement insertDeltCards = null;
	private PreparedStatement insertBettingRound = null;
	private PreparedStatement insertShowdown = null;
	private PreparedStatement selectPlayerID = null;

	public PokerLogsDBManager() {
		// db = new PostgresConnector("srv-prj-05.dmi.usherb.ca", "BluffinWifEnum", "postgres", "27053");
		db = new PostgresConnector("srv-prj-05.dmi.usherb.ca", "BluffinDB", "postgres", "27053");
		initStatement();
	}

	private void initStatement() {

		// INSERT DOMAIN
		insertDomain = db.getPreparedStatement("INSERT into Domain (domainName, domainURL) values(?,?)");

		// INSERT PLAYER
		insertPlayer = db.getPreparedStatement("INSERT into Player (playerName, idDomain) values(?,?)");

		// SELECT PLAYER ID
		selectPlayerID = db.getPreparedStatement("SELECT idPlayer from Player WHERE playerName = ?");
	}

	public void addDomain(String domainName, String domainUrl) {
		System.out.print("Adding 1 Domain to the database...");
		try {
			insertDomain.clearParameters();
			insertDomain.setString(1, domainName);
			insertDomain.setString(2, domainUrl);
			insertDomain.executeUpdate();
		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		db.commit();
		System.out.println("DONE");
	}

	public void addPlayer(String playerName, String idDomain) {
		System.out.print("Adding 1 Player to the database...");

		try {
			insertPlayer.clearParameters();
			insertPlayer.setString(1, playerName);
			insertPlayer.setInt(2, Integer.valueOf(idDomain));
			insertPlayer.executeUpdate();
		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		db.commit();
		System.out.println("DONE");
	}

	public void addPlayers(ArrayList<String[]> playerList) {
		System.out.print("Adding " + playerList.size() + " Player to the database...");

		try {
			insertPlayer.clearBatch();
			for (String[] p : playerList) {
				insertPlayer.clearParameters();
				insertPlayer.setString(1, p[0]);
				insertPlayer.setInt(2, Integer.valueOf(p[1]));
				insertPlayer.addBatch();
			}
			insertPlayer.executeBatch();
		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		db.commit();
		System.out.println("DONE");
	}

	private String[] getDomainX(String colName) {
		ResultSet r = db.query("SELECT " + colName + " FROM Domain");
		int nbRow = db.rowCount(colName, "Domain", "");

		String[] domainsName = new String[nbRow];
		try {
			int i = 0;
			while (r.next()) {
				domainsName[i] = r.getString(1);
				i++;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		return domainsName;
	}

	public String[] getDomainsName() {
		return getDomainX("domainName");
	}

	public String[] getDomainsID() {
		return getDomainX("idDomain");
	}

	public HashMap<String, String> getDomainNameIdMap() {
		ResultSet r = db.query("SELECT domainName, idDomain FROM Domain");
		int nbRow = db.rowCount("idDomain", "Domain", "");
		HashMap<String, String> domainMap = new HashMap<String, String>(nbRow);

		try {
			while (r.next()) {
				domainMap.put(r.getString(1), r.getString(2));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		return domainMap;
	}

	public Integer getPlayerID(String playerName) {

		try {

			selectPlayerID.setString(1, playerName);
			ResultSet rs = selectPlayerID.executeQuery();

			if (!rs.next()) {
				insertPlayer.clearParameters();
				insertPlayer.setString(1, playerName);
				insertPlayer.setInt(2, 2);
				insertPlayer.executeUpdate();
			}

			rs = selectPlayerID.executeQuery();
			rs.next();
			System.out.println("The attributed id for " + playerName + " was " + rs.getInt(1));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("There was a problen retriving player id");
			System.out.println(e.getMessage());
		}
		db.commit();
		return 0;

	}
}

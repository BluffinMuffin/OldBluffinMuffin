package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

//TODO (low priority): Refactor the insert functions to param the preparedStatement

public class PokerLogsDBManager {

	// TODO: don't make it public to avoid multiple commits;
	private PostgresConnector db = null;

	private PreparedStatement insertPlayer = null;
	private PreparedStatement insertDomain = null;
	private PreparedStatement insertGameSet = null;
	private PreparedStatement insertGame = null;
	private PreparedStatement insertDealtCards = null;
	private PreparedStatement insertBettingRound = null;
	private PreparedStatement insertShowdown = null;
	private PreparedStatement selectPlayerID = null;
	private PreparedStatement insertBet = null;
	private PreparedStatement insertForcedBet = null;
	private PreparedStatement insertSeats = null;
	private PreparedStatement insertWinner = null;

	public PokerLogsDBManager() {

		db = new PostgresConnector("srv-prj-05.dmi.usherb.ca", "PokerLog", "postgres", "27053");
		initStatement();
	}

	public void doCommit() {
		System.out.println("Committing ...");
		db.commit();
	}

	private void initStatement() {

		// INSERT DOMAIN
		insertDomain = db.getPreparedStatement("INSERT into Domain (domainName, domainURL) values(?,?)");

		// INSERT PLAYER
		insertPlayer = db.getPreparedStatement("INSERT into Player (playerName, idDomain) values(?,?)");

		// SELECT PLAYER ID
		selectPlayerID = db.getPreparedStatement("SELECT idPlayer from Player WHERE playerName = ?", false);

		// INSERT GAMESET

		insertGameSet = db.getPreparedStatement("INSERT INTO GAMESET (bbvalue, sbvalue, bettype, gametype, pokertype, source) VALUES (?, ?, ?::BetType, ?::GameType, ?::PokerType, ?)");

		// INSERT Game
		insertGame = db.getPreparedStatement("INSERT INTO game (idGameSet, idDealer, Flop1, Flop2, Flop3, turn, river) VALUES (?, ?, ?, ?, ?, ?, ?)");

		// INSERT a BET from a betting round
		insertBet = db.getPreparedStatement("INSERT INTO BETTINGROUND(idGame, idGameSet, idPlayer, round, seq, action, amount) VALUES (?, ?, ?, ?::BettingRoundType, ?, ?::ActionType, ?)");

		// INSERT ForcedBet
		insertForcedBet = db.getPreparedStatement("INSERT INTO FORCEDBETS(idGame, idGameSet, idPlayer, seq, forcedBetType, amount) VALUES (?, ?, ?, ?, ?::ForcedBetType, ?)");

		// INSERT SEATS
		insertSeats = db.getPreparedStatement("INSERT INTO SEATS (idGame, idGameSet, idPlayer, seatNo, sittingIn, chips) VALUES (?, ?, ?, ?, ?, ?)");

		// INSERT WINNER
		// TODO: Change table name? don't need winning hand column + enum anymore in DB
		insertWinner = db.getPreparedStatement("INSERT INTO SHOWDOWN (idGame, idGameSet, idPlayer, potAmountWon) VALUES (?, ?, ?, ?)");

		// INSERT DEALT CARDS
		insertDealtCards = db.getPreparedStatement("INSERT INTO DealtCards (idGame, idGameSet, idPlayer, pocket1, pocket2) VALUES (?, ?, ?, ?, ?)");
	}

	public void addDealtCards(Integer idGame, Integer idGameSet, Integer idPlayer, String pocket1, String pocket2) {

		try {
			insertDealtCards.setInt(1, idGame);
			insertDealtCards.setInt(2, idGameSet);
			insertDealtCards.setInt(3, idPlayer);
			insertDealtCards.setString(4, pocket1);
			insertDealtCards.setString(5, pocket2);

			insertDealtCards.executeUpdate();

		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// db.commit();
	}

	public void addWinner(Integer idGame, Integer idGameSet, Integer idPlayer, double amountWon) {

		// System.out.println("Adding Winner");

		try {
			insertWinner.setInt(1, idGame);
			insertWinner.setInt(2, idGameSet);
			insertWinner.setInt(3, idPlayer);
			insertWinner.setDouble(4, amountWon);

			insertWinner.executeUpdate();

		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// db.commit();
	}

	public Integer addGameSet(Double bigBlindValue, Double smallBlindValue, String bettype, String gametype, String pokertype, String source) {
		// System.out.println("Adding GameSet");
		Integer attributedID = null;

		try {
			insertGameSet.clearParameters();
			insertGameSet.setDouble(1, smallBlindValue);
			insertGameSet.setDouble(2, bigBlindValue);
			insertGameSet.setString(3, bettype);
			insertGameSet.setString(4, gametype);
			insertGameSet.setString(5, pokertype);
			insertGameSet.setString(6, source);

			insertGameSet.executeUpdate();
			// db.commit();

			ResultSet rs = insertGameSet.getGeneratedKeys();

			if (rs.next()) {
				attributedID = rs.getInt(1);
			} else {
				attributedID = -1;
			}

		} catch (SQLException e) {
			System.out.println("FAIL!!");
			System.out.println(insertGameSet.toString());
			db.rollback();
			e.printStackTrace();

			System.out.println(e.getMessage());
			System.exit(-1);
		}

		// System.out.println("DONE");
		return attributedID;
	}

	public Integer addGame(Integer idGameSet, Integer idDealer, String Flop1, String Flop2, String Flop3, String turn, String river) {
		// System.out.println("Adding Game");
		Integer attributedID = null;

		try {
			insertGame.setInt(1, idGameSet);
			insertGame.setInt(2, idDealer);
			insertGame.setString(3, Flop1);
			insertGame.setString(4, Flop2);
			insertGame.setString(5, Flop3);
			insertGame.setString(6, turn);
			insertGame.setString(7, river);

			insertGame.executeUpdate();

			ResultSet rs = insertGame.getGeneratedKeys();
			if (rs.next()) {
				attributedID = rs.getInt(1);
			} else {
				attributedID = -1;
			}

		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// db.commit();
		// System.out.println("DONE");
		return attributedID;

	}

	public void addBet(Integer idGame, Integer idGameSet, Integer idPlayer, String round, int seq, String action, Double amount) {
		// System.out.println("Adding Bet");
		try {
			insertBet.setInt(1, idGame);
			insertBet.setInt(2, idGameSet);
			insertBet.setInt(3, idPlayer);
			insertBet.setString(4, round);
			insertBet.setInt(5, seq);
			insertBet.setString(6, action);
			insertBet.setObject(7, amount);

			insertBet.executeUpdate();

		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// db.commit();
		// System.out.println("DONE");

	}

	public void addForcedBets(Integer idGame, Integer idGameSet, Integer idPlayer, int seq, String action, double amount) {
		// System.out.println("Adding ForcedBet");
		try {
			insertForcedBet.setInt(1, idGame);
			insertForcedBet.setInt(2, idGameSet);
			insertForcedBet.setInt(3, idPlayer);
			insertForcedBet.setInt(4, seq);
			insertForcedBet.setString(5, action);
			insertForcedBet.setDouble(6, amount);

			insertForcedBet.executeUpdate();

		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// db.commit();
		// System.out.println("DONE");
	}

	public void addSeats(Integer idGame, Integer idGameSet, Integer idPlayer, int seatNo, boolean sittingIn, double chips) {
		// System.out.println("Adding Seats");
		try {
			insertSeats.setInt(1, idGame);
			insertSeats.setInt(2, idGameSet);
			insertSeats.setInt(3, idPlayer);
			insertSeats.setInt(4, seatNo);
			insertSeats.setBoolean(5, sittingIn);
			insertSeats.setDouble(6, chips);

			insertSeats.executeUpdate();

		} catch (SQLException e) {
			System.out.println("FAIL!!");
			db.rollback();
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		// db.commit();
		// System.out.println("DONE");

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

		// insertPlayer.getGeneratedKeys().getBigDecimal(1);

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

		// db.commit();
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
		Integer pId = null;
		try {

			selectPlayerID.setString(1, playerName);

			ResultSet rs = selectPlayerID.executeQuery();

			if (!rs.next()) {
				insertPlayer.clearParameters();
				insertPlayer.setString(1, playerName);
				insertPlayer.setInt(2, 2);
				insertPlayer.executeUpdate();

				ResultSet rs1 = insertPlayer.getGeneratedKeys();

				if (rs1.next()) {
					pId = rs1.getInt(1);
				} else {
					pId = -1;
				}
			} else {
				pId = rs.getInt(1);

			}
			// System.out.println("The attributed id for " + playerName + " was " + pId);

		} catch (SQLException e) {
			System.out.println("There was a problen retriving player id");
			System.out.println(e.getMessage());
			db.rollback();
			System.exit(-1);
		}
		db.commit();
		return pId;

	}
}

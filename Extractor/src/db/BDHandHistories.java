package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class BDHandHistories
{
    private final PreparedStatement m_stmtGetAll;
    private final PreparedStatement m_stmtGetLimit;
    private final PreparedStatement m_stmtGetLater;
    private final PreparedStatement m_stmtGetLaterNoLimit;
    private final PreparedStatement m_stmtInterval;
    private final MyConnection m_connection;
    
    /**
     * Creation d'une instance. Précompilation d'énoncés SQL.
     */
    public BDHandHistories(MyConnection p_connection) throws SQLException
    {
        m_connection = p_connection;
        m_stmtGetAll = m_connection.getConnection().prepareStatement("SELECT handhistory " + "FROM handhistories " + "INNER JOIN pokerhands ON handhistories.pokerhand_id = pokerhands.pokerhand_id " + "INNER JOIN playerhandscashkeycolumns ON playerhandscashkeycolumns.pokerhand_id = pokerhands.pokerhand_id " + "WHERE pokerhands.gametype_id IN (1,3,4,12,22,10) " + // NoLimit
                " AND playerhandscashkeycolumns.player_id IN (6,36205,45937,45012,33396) " + // Louick IDs
                " AND pokerhands.site_id IN (1, 2) " + // FullTilt, Poker Start
                "ORDER BY pokerhands.handtimestamp");
        m_stmtGetLimit = m_connection.getConnection().prepareStatement("SELECT handhistory " + "FROM handhistories " + "INNER JOIN pokerhands ON handhistories.pokerhand_id = pokerhands.pokerhand_id " + "INNER JOIN playerhandscashkeycolumns ON playerhandscashkeycolumns.pokerhand_id = pokerhands.pokerhand_id " + "WHERE pokerhands.gametype_id IN (1,3,4,12,22,10) " + // NoLimit
                " AND playerhandscashkeycolumns.player_id IN (6,36205,45937,45012,33396) " + // Louick IDs
                " AND pokerhands.site_id IN (1, 2) " + // FullTilt, Poker Start
                "ORDER BY pokerhands.handtimestamp " + "LIMIT ?");
        m_stmtGetLaterNoLimit = m_connection.getConnection().prepareStatement("SELECT handhistory " + "FROM handhistories " + "INNER JOIN pokerhands ON handhistories.pokerhand_id = pokerhands.pokerhand_id " + "INNER JOIN playerhandscashkeycolumns ON playerhandscashkeycolumns.pokerhand_id = pokerhands.pokerhand_id " + "WHERE pokerhands.gametype_id IN (1,3,4,12,22,10) " + // NoLimit
                " AND playerhandscashkeycolumns.player_id IN (6,36205,45937,45012,33396) " + // Louick IDs
                " AND pokerhands.site_id IN (1, 2) " + // FullTilt, Poker Start
                " AND pokerhands.handtimestamp >= ? " + "ORDER BY pokerhands.handtimestamp");
        m_stmtGetLater = m_connection.getConnection().prepareStatement("SELECT handhistory " + "FROM handhistories " + "INNER JOIN pokerhands ON handhistories.pokerhand_id = pokerhands.pokerhand_id " + "INNER JOIN playerhandscashkeycolumns ON playerhandscashkeycolumns.pokerhand_id = pokerhands.pokerhand_id " + "WHERE pokerhands.gametype_id IN (1,3,4,12,22,10) " + // NoLimit
                " AND playerhandscashkeycolumns.player_id IN (6,36205,45937,45012,33396) " + // Louick IDs
                " AND pokerhands.site_id IN (1, 2) " + // FullTilt, Poker Start
                " AND pokerhands.handtimestamp >= ? " + "ORDER BY pokerhands.handtimestamp " + "LIMIT ?");
        m_stmtInterval = m_connection.getConnection().prepareStatement("SELECT handhistory " + "FROM handhistories " + "INNER JOIN pokerhands ON handhistories.pokerhand_id = pokerhands.pokerhand_id " + "INNER JOIN playerhandscashkeycolumns ON playerhandscashkeycolumns.pokerhand_id = pokerhands.pokerhand_id " + "WHERE pokerhands.gametype_id IN (1,3,4,12,22,10) " + // NoLimit
                " AND playerhandscashkeycolumns.player_id IN (6,36205,45937,45012,33396) " + // Louick IDs
                " AND pokerhands.site_id IN (1, 2) " + // FullTilt, Poker Start
                " AND pokerhands.handtimestamp >= ? " + " AND pokerhands.handtimestamp < ? " + "ORDER BY pokerhands.handtimestamp");
    }
    
    /**
     * Retourner la connexion associée.
     */
    public MyConnection getConnexion()
    {
        return m_connection;
    }
    
    public ArrayList<String> getHandHistories() throws SQLException
    {
        final ResultSet rset = m_stmtGetAll.executeQuery();
        final ArrayList<String> handHistories = new ArrayList<String>();
        while (rset.next())
        {
            handHistories.add(rset.getString(1));
        }
        
        rset.close();
        
        return handHistories;
    }
    
    public ArrayList<String> getHandHistories(Calendar p_minDate) throws SQLException
    {
        m_stmtGetLaterNoLimit.setTimestamp(1, new Timestamp(p_minDate.getTimeInMillis()));
        final ResultSet rset = m_stmtGetLaterNoLimit.executeQuery();
        
        final ArrayList<String> handHistories = new ArrayList<String>();
        while (rset.next())
        {
            handHistories.add(rset.getString(1));
        }
        
        rset.close();
        
        return handHistories;
    }
    
    public ArrayList<String> getHandHistories(Calendar p_minDate, Calendar p_maxDate) throws SQLException
    {
        m_stmtInterval.setTimestamp(1, new Timestamp(p_minDate.getTimeInMillis()));
        m_stmtInterval.setTimestamp(2, new Timestamp(p_maxDate.getTimeInMillis()));
        final ResultSet rset = m_stmtInterval.executeQuery();
        
        final ArrayList<String> handHistories = new ArrayList<String>();
        while (rset.next())
        {
            handHistories.add(rset.getString(1));
        }
        
        rset.close();
        
        return handHistories;
    }
    
    public ArrayList<String> getHandHistories(Calendar p_minDate, int p_limit) throws SQLException
    {
        m_stmtGetLater.setTimestamp(1, new Timestamp(p_minDate.getTimeInMillis()));
        m_stmtGetLater.setInt(2, p_limit);
        final ResultSet rset = m_stmtGetLater.executeQuery();
        
        final ArrayList<String> handHistories = new ArrayList<String>();
        while (rset.next())
        {
            handHistories.add(rset.getString(1));
        }
        
        rset.close();
        
        return handHistories;
    }
    
    public ArrayList<String> getHandHistories(int p_limit) throws SQLException
    {
        m_stmtGetLimit.setInt(1, p_limit);
        final ResultSet rset = m_stmtGetLimit.executeQuery();
        
        final ArrayList<String> handHistories = new ArrayList<String>();
        while (rset.next())
        {
            handHistories.add(rset.getString(1));
        }
        
        rset.close();
        
        return handHistories;
    }
    
    public ArrayList<String> getHandHistoriesOneDay(Calendar p_date) throws SQLException
    {
        final Calendar minDate = (Calendar) p_date.clone();
        minDate.set(Calendar.HOUR_OF_DAY, 0);
        minDate.set(Calendar.MINUTE, 0);
        minDate.set(Calendar.SECOND, 0);
        minDate.set(Calendar.MILLISECOND, 0);
        final Calendar maxDate = (Calendar) minDate.clone();
        maxDate.add(Calendar.DAY_OF_YEAR, 1);
        
        m_stmtInterval.setTimestamp(1, new Timestamp(minDate.getTimeInMillis()));
        m_stmtInterval.setTimestamp(2, new Timestamp(maxDate.getTimeInMillis()));
        final ResultSet rset = m_stmtInterval.executeQuery();
        
        final ArrayList<String> handHistories = new ArrayList<String>();
        while (rset.next())
        {
            handHistories.add(rset.getString(1));
        }
        
        rset.close();
        
        return handHistories;
    }
}

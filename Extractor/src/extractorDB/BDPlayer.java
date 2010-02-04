package extractorDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import utility.BDConnection;


public class BDPlayer
{
    
    private final PreparedStatement m_stmtGetAll;
    private final PreparedStatement m_stmtGetOverXCashHands;
    private final BDConnection m_connection;
    
    /**
     * Creation d'une instance. Précompilation d'énoncés SQL.
     */
    public BDPlayer(BDConnection p_connection) throws SQLException
    {
        m_connection = p_connection;
        m_stmtGetAll = m_connection.getConnection().prepareStatement("SELECT player_id, site_id, playername, lastplayeddate, " + " cashhands, tourneyhands, playertype_id " + "FROM players");
        m_stmtGetOverXCashHands = m_connection.getConnection().prepareStatement("SELECT player_id, site_id, playername, lastplayeddate, " + " cashhands, tourneyhands, playertype_id " + "FROM players " + "WHERE cashhands >= ? " + "ORDER BY cashhands DESC");
        
    }
    
    /**
     * Retourner la connexion associée.
     */
    public BDConnection getConnexion()
    {
        return m_connection;
    }
    
    public TuplePlayer getMembres() throws SQLException
    {
        final ResultSet rset = m_stmtGetAll.executeQuery();
        if (rset.next())
        {
            final TuplePlayer tuplePlayer = new TuplePlayer();
            tuplePlayer.m_idPlayer = rset.getInt(1);
            tuplePlayer.m_isSite = rset.getInt(2);
            tuplePlayer.m_playerName = rset.getString(3);
            
            tuplePlayer.m_lastPlayedDate = new GregorianCalendar();
            tuplePlayer.m_lastPlayedDate.setTime(rset.getDate(4));
            
            tuplePlayer.m_cashHands = rset.getInt(5);
            tuplePlayer.m_tourneyHands = rset.getInt(6);
            tuplePlayer.m_idPlayerType = rset.getInt(7);
            rset.close();
            
            return tuplePlayer;
        }
        else
        {
            return null;
        }
    }
    
    public ArrayList<TuplePlayer> getPlayers(int p_minCashHands) throws SQLException
    {
        m_stmtGetOverXCashHands.setInt(1, p_minCashHands);
        final ResultSet rset = m_stmtGetOverXCashHands.executeQuery();
        
        final ArrayList<TuplePlayer> tuples = new ArrayList<TuplePlayer>();
        
        while (rset.next())
        {
            final TuplePlayer tuplePlayer = new TuplePlayer();
            tuplePlayer.m_idPlayer = rset.getInt(1);
            tuplePlayer.m_isSite = rset.getInt(2);
            tuplePlayer.m_playerName = rset.getString(3);
            
            tuplePlayer.m_lastPlayedDate = new GregorianCalendar();
            tuplePlayer.m_lastPlayedDate.setTime(rset.getDate(4));
            
            tuplePlayer.m_cashHands = rset.getInt(5);
            tuplePlayer.m_tourneyHands = rset.getInt(6);
            tuplePlayer.m_idPlayerType = rset.getInt(7);
            
            tuples.add(tuplePlayer);
        }
        
        rset.close();
        
        return tuples;
    }
}

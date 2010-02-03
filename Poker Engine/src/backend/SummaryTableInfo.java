package backend;

import basePoker.TypePokerGame;

/**
 * @author Hocus
 *         This class represents a network table.
 */
public class SummaryTableInfo implements Comparable<SummaryTableInfo>
{
    public int m_noPort;
    public String m_tableName;
    public TypePokerGame m_gameType;
    public int m_bigBlind;
    public int m_nbPlayers;
    public int m_nbSeats;
    
    /**
     * Create a new table
     * 
     * @param p_noPort
     *            Port number of the table
     * @param p_tableName
     *            Table name
     * @param p_gameType
     *            Game type
     * @param p_bigBlind
     *            Number of chips of the big blind
     * @param p_nbPlayers
     *            Number of players
     * @param p_nbSeats
     *            Number of seat
     */
    public SummaryTableInfo(int p_noPort, String p_tableName, TypePokerGame p_gameType, int p_bigBlind, int p_nbPlayers, int p_nbSeats)
    {
        m_noPort = p_noPort;
        m_tableName = p_tableName;
        m_gameType = p_gameType;
        m_bigBlind = p_bigBlind;
        m_nbPlayers = p_nbPlayers;
        m_nbSeats = p_nbSeats;
    }
    
    public int compareTo(SummaryTableInfo p_table)
    {
        return ((Integer) m_noPort).compareTo(p_table.m_noPort);
    }
    
    /**
     * Return a string representing the table.
     * This string is used to be sent through the network
     * 
     * @param p_delimiter
     *            Delimiter of the fields
     * @return
     *         A string representing the table
     */
    public String toString(String p_delimiter)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(m_noPort);
        sb.append(p_delimiter);
        sb.append(m_tableName);
        sb.append(p_delimiter);
        sb.append(m_gameType.name());
        sb.append(p_delimiter);
        sb.append(m_bigBlind);
        sb.append(p_delimiter);
        sb.append(m_nbPlayers);
        sb.append(p_delimiter);
        sb.append(m_nbSeats);
        sb.append(p_delimiter);
        
        return sb.toString();
    }
}

package pokerExtractor;

import java.text.DateFormat;
import java.util.Calendar;

public class TuplePlayer
{
    public int m_idPlayer;
    public int m_isSite;
    public String m_playerName;
    public Calendar m_lastPlayedDate;
    public int m_cashHands;
    public int m_tourneyHands;
    public int m_idPlayerType;
    
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(m_idPlayer);
        sb.append("\t");
        sb.append(m_isSite);
        sb.append("\t");
        sb.append(m_playerName);
        sb.append("\t");
        sb.append(DateFormat.getDateInstance().format(m_lastPlayedDate.getTime()));
        sb.append("\t");
        sb.append(m_cashHands);
        sb.append("\t");
        sb.append(m_tourneyHands);
        sb.append("\t");
        sb.append(m_idPlayerType);
        
        return sb.toString();
    }
}

// player_id serial NOT NULL,
// site_id integer NOT NULL,
// playername text,
// lastplayeddate timestamp without time zone,
// cashhands integer NOT NULL,
// tourneyhands integer NOT NULL,
// playertype_id smallint NOT NULL,

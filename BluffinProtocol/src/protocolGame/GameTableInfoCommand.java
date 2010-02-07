package protocolGame;

import java.util.ArrayList;
import java.util.StringTokenizer;

import protocolGameTools.SummarySeatInfo;
import protocolTools.IBluffinCommand;
import utility.Constants;

public class GameTableInfoCommand implements IBluffinCommand
{
    private final int m_totalPotAmount;
    private final int m_nbSeats;
    private final ArrayList<Integer> m_potsAmount;
    private final ArrayList<Integer> m_boardCardIDs;
    private final int m_nbPlayers;
    private final ArrayList<SummarySeatInfo> m_seats;
    
    public static String COMMAND_NAME = "gameTABLE_INFO";
    
    public GameTableInfoCommand(StringTokenizer argsToken)
    {
        m_potsAmount = new ArrayList<Integer>();
        m_boardCardIDs = new ArrayList<Integer>();
        m_seats = new ArrayList<SummarySeatInfo>();
        
        m_totalPotAmount = Integer.parseInt(argsToken.nextToken());
        m_nbSeats = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < m_nbSeats; ++i)
        {
            m_potsAmount.add(Integer.parseInt(argsToken.nextToken()));
        }
        for (int i = 0; i < 5; ++i)
        {
            m_boardCardIDs.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_nbPlayers = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < m_nbPlayers; ++i)
        {
            m_seats.add(new SummarySeatInfo(argsToken));
        }
    }
    
    public GameTableInfoCommand(int totalPotAmount, int nbSeats, ArrayList<Integer> potsAmount, ArrayList<Integer> boardCardIDs, int nbPlayers, ArrayList<SummarySeatInfo> seats)
    {
        m_totalPotAmount = totalPotAmount;
        m_nbSeats = nbSeats;
        m_potsAmount = potsAmount;
        m_boardCardIDs = boardCardIDs;
        m_nbPlayers = nbPlayers;
        m_seats = seats;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameTableInfoCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_totalPotAmount);
        sb.append(Constants.DELIMITER);
        sb.append(m_nbSeats);
        sb.append(Constants.DELIMITER);
        for (int i = 0; i < m_potsAmount.size(); ++i)
        {
            sb.append(m_potsAmount.get(i));
            sb.append(Constants.DELIMITER);
        }
        for (int i = 0; i < m_boardCardIDs.size(); ++i)
        {
            sb.append(m_boardCardIDs.get(i));
            sb.append(Constants.DELIMITER);
        }
        sb.append(m_nbPlayers);
        sb.append(Constants.DELIMITER);
        for (int i = 0; i < m_seats.size(); ++i)
        {
            sb.append(m_seats.get(i).toString(Constants.DELIMITER));
        }
        return sb.toString();
    }
    
    public int getTotalPotAmount()
    {
        return m_totalPotAmount;
    }
    
    public int getNbSeats()
    {
        return m_nbSeats;
    }
    
    public ArrayList<Integer> getPotsAmount()
    {
        return m_potsAmount;
    }
    
    public ArrayList<Integer> getBoardCardIDs()
    {
        return m_boardCardIDs;
    }
    
    public int getNbPlayers()
    {
        return m_nbPlayers;
    }
    
    public ArrayList<SummarySeatInfo> getSeats()
    {
        return m_seats;
    }
}

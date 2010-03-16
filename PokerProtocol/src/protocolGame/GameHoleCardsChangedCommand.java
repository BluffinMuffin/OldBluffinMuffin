package protocolGame;

import java.util.ArrayList;
import java.util.StringTokenizer;

import protocolTools.IPokerCommand;
import protocolTools.PokerCommand;

public class GameHoleCardsChangedCommand implements IPokerCommand
{
    private final ArrayList<Integer> m_cardsID = new ArrayList<Integer>();
    private final int m_playerPos;
    public static String COMMAND_NAME = "gameHOLE_CARDS_CHANGED";
    
    public GameHoleCardsChangedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < 2; ++i)
        {
            m_cardsID.add(Integer.parseInt(argsToken.nextToken()));
        }
    }
    
    public GameHoleCardsChangedCommand(Integer pos, Integer card1, Integer card2)
    {
        m_playerPos = pos;
        m_cardsID.add(card1);
        m_cardsID.add(card2);
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameHoleCardsChangedCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_playerPos);
        sb.append(PokerCommand.DELIMITER);
        for (int i = 0; i < 2; ++i)
        {
            sb.append(m_cardsID.get(i));
            sb.append(PokerCommand.DELIMITER);
        }
        return sb.toString();
    }
    
    public ArrayList<Integer> getCardsId()
    {
        return m_cardsID;
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
}

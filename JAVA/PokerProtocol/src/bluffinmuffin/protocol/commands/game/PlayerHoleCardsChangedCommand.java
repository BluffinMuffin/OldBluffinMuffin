package bluffinmuffin.protocol.commands.game;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.AbstractCommand;

public class PlayerHoleCardsChangedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return PlayerHoleCardsChangedCommand.COMMAND_NAME;
    }
    
    private final ArrayList<Integer> m_cardsID = new ArrayList<Integer>();
    private final int m_playerPos;
    private final boolean m_isPlaying;
    
    public static String COMMAND_NAME = "gameHOLE_CARDS_CHANGED";
    
    public PlayerHoleCardsChangedCommand(StringTokenizer argsToken)
    {
        m_playerPos = Integer.parseInt(argsToken.nextToken());
        for (int i = 0; i < 2; ++i)
        {
            m_cardsID.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_isPlaying = Boolean.parseBoolean(argsToken.nextToken());
    }
    
    public PlayerHoleCardsChangedCommand(Integer pos, Integer card1, Integer card2, boolean playing)
    {
        m_playerPos = pos;
        m_cardsID.add(card1);
        m_cardsID.add(card2);
        m_isPlaying = playing;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_playerPos);
        for (int i = 0; i < 2; ++i)
        {
            append(sb, m_cardsID.get(i));
        }
        append(sb, m_isPlaying);
    }
    
    public ArrayList<Integer> getCardsId()
    {
        return m_cardsID;
    }
    
    public int getPlayerPos()
    {
        return m_playerPos;
    }
    
    public boolean isPlaying()
    {
        return m_isPlaying;
    }
}

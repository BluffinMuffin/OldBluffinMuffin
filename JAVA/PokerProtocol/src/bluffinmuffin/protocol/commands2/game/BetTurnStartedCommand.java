package bluffinmuffin.protocol.commands2.game;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameRoundType;
import bluffinmuffin.protocol.commands2.AbstractCommand;

public class BetTurnStartedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return BetTurnStartedCommand.COMMAND_NAME;
    }
    
    private final ArrayList<Integer> m_cardsID = new ArrayList<Integer>();
    private final GameRoundType m_round;
    
    public static String COMMAND_NAME = "gameBET_TURN_STARTED";
    
    public BetTurnStartedCommand(StringTokenizer argsToken)
    {
        for (int i = 0; i < 5; ++i)
        {
            m_cardsID.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_round = GameRoundType.values()[Integer.parseInt(argsToken.nextToken())];
    }
    
    public BetTurnStartedCommand(Integer flop1, Integer flop2, Integer flop3, Integer turn, Integer river, GameRoundType round)
    {
        m_cardsID.add(flop1);
        m_cardsID.add(flop2);
        m_cardsID.add(flop3);
        m_cardsID.add(turn);
        m_cardsID.add(river);
        m_round = round;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        for (int i = 0; i < 5; ++i)
        {
            append(sb, m_cardsID.get(i));
        }
        append(sb, m_round.ordinal());
    }
    
    public ArrayList<Integer> getCardsId()
    {
        return m_cardsID;
    }
    
    public GameRoundType getRound()
    {
        return m_round;
    }
}

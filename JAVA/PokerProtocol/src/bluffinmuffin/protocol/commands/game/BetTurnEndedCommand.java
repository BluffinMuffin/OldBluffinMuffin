package bluffinmuffin.protocol.commands.game;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameRoundType;
import bluffinmuffin.protocol.commands.AbstractCommand;

public class BetTurnEndedCommand extends AbstractCommand
{
    @Override
    protected String getCommandName()
    {
        return BetTurnEndedCommand.COMMAND_NAME;
    }
    
    private final ArrayList<Integer> m_potsAmounts;
    private final GameRoundType m_round;
    
    public static String COMMAND_NAME = "gameBET_TURN_ENDED";
    
    public BetTurnEndedCommand(StringTokenizer argsToken)
    {
        final int count = Integer.parseInt(argsToken.nextToken());
        m_potsAmounts = new ArrayList<Integer>();
        for (int i = 0; i < count; ++i)
        {
            m_potsAmounts.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_round = GameRoundType.values()[Integer.parseInt(argsToken.nextToken())];
    }
    
    public BetTurnEndedCommand(ArrayList<Integer> potsAmounts, GameRoundType round)
    {
        m_potsAmounts = potsAmounts;
        m_round = round;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        append(sb, m_potsAmounts.size());
        for (final int pa : m_potsAmounts)
        {
            append(sb, pa);
        }
        append(sb, m_round.ordinal());
    }
    
    public ArrayList<Integer> getPotsAmounts()
    {
        return m_potsAmounts;
    }
    
    public GameRoundType getRound()
    {
        return m_round;
    }
}

package bluffinmuffin.protocol.commands.game;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameRoundType;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;


public class BetTurnEndedCommand implements ICommand
{
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
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(BetTurnEndedCommand.COMMAND_NAME);
        sb.append(Command.G_DELIMITER);
        sb.append(m_potsAmounts.size());
        sb.append(Command.G_DELIMITER);
        for (int i = 0; i < m_potsAmounts.size(); ++i)
        {
            sb.append(m_potsAmounts.get(i));
            sb.append(Command.G_DELIMITER);
        }
        sb.append(m_round.ordinal());
        return sb.toString();
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

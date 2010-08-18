package protocol.commands.game;

import java.util.ArrayList;
import java.util.StringTokenizer;

import poker.game.TypeRound;
import protocol.commands.Command;
import protocol.commands.ICommand;

public class BetTurnEndedCommand implements ICommand
{
    private final ArrayList<Integer> m_potsAmounts;
    private final TypeRound m_round;
    public static String COMMAND_NAME = "gameBET_TURN_ENDED";
    
    public BetTurnEndedCommand(StringTokenizer argsToken)
    {
        final int count = Integer.parseInt(argsToken.nextToken());
        m_potsAmounts = new ArrayList<Integer>();
        for (int i = 0; i < count; ++i)
        {
            m_potsAmounts.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_round = TypeRound.values()[Integer.parseInt(argsToken.nextToken())];
    }
    
    public BetTurnEndedCommand(ArrayList<Integer> potsAmounts, TypeRound round)
    {
        m_potsAmounts = potsAmounts;
        m_round = round;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(BetTurnEndedCommand.COMMAND_NAME);
        sb.append(Command.DELIMITER);
        sb.append(m_potsAmounts.size());
        sb.append(Command.DELIMITER);
        for (int i = 0; i < m_potsAmounts.size(); ++i)
        {
            sb.append(m_potsAmounts.get(i));
            sb.append(Command.DELIMITER);
        }
        sb.append(m_round.ordinal());
        return sb.toString();
    }
    
    public ArrayList<Integer> getPotsAmounts()
    {
        return m_potsAmounts;
    }
    
    public TypeRound getRound()
    {
        return m_round;
    }
}

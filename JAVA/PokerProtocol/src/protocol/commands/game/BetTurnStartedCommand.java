package protocol.commands.game;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bluffinmuffin.poker.game.TypeRound;

import protocol.commands.Command;
import protocol.commands.ICommand;

public class BetTurnStartedCommand implements ICommand
{
    private final ArrayList<Integer> m_cardsID = new ArrayList<Integer>();
    private final TypeRound m_round;
    public static String COMMAND_NAME = "gameBET_TURN_STARTED";
    
    public BetTurnStartedCommand(StringTokenizer argsToken)
    {
        for (int i = 0; i < 5; ++i)
        {
            m_cardsID.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_round = TypeRound.values()[Integer.parseInt(argsToken.nextToken())];
    }
    
    public BetTurnStartedCommand(Integer flop1, Integer flop2, Integer flop3, Integer turn, Integer river, TypeRound round)
    {
        m_cardsID.add(flop1);
        m_cardsID.add(flop2);
        m_cardsID.add(flop3);
        m_cardsID.add(turn);
        m_cardsID.add(river);
        m_round = round;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(BetTurnStartedCommand.COMMAND_NAME);
        sb.append(Command.G_DELIMITER);
        for (int i = 0; i < 5; ++i)
        {
            sb.append(m_cardsID.get(i));
            sb.append(Command.G_DELIMITER);
        }
        sb.append(m_round.ordinal());
        sb.append(Command.G_DELIMITER);
        return sb.toString();
    }
    
    public ArrayList<Integer> getCardsId()
    {
        return m_cardsID;
    }
    
    public TypeRound getRound()
    {
        return m_round;
    }
}

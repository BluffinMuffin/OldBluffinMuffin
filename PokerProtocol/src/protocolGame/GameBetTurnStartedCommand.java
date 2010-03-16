package protocolGame;

import java.util.ArrayList;
import java.util.StringTokenizer;

import pokerGameLogic.TypePokerGameRound;
import protocolTools.IPokerCommand;
import protocolTools.PokerCommand;

public class GameBetTurnStartedCommand implements IPokerCommand
{
    private final ArrayList<Integer> m_cardsID = new ArrayList<Integer>();
    private final TypePokerGameRound m_round;
    public static String COMMAND_NAME = "gameBET_TURN_STARTED";
    
    public GameBetTurnStartedCommand(StringTokenizer argsToken)
    {
        for (int i = 0; i < 5; ++i)
        {
            m_cardsID.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_round = TypePokerGameRound.valueOf(argsToken.nextToken());
    }
    
    public GameBetTurnStartedCommand(Integer flop1, Integer flop2, Integer flop3, Integer turn, Integer river, TypePokerGameRound round)
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
        sb.append(GameBetTurnStartedCommand.COMMAND_NAME);
        sb.append(PokerCommand.DELIMITER);
        for (int i = 0; i < 5; ++i)
        {
            sb.append(m_cardsID.get(i));
            sb.append(PokerCommand.DELIMITER);
        }
        sb.append(PokerCommand.DELIMITER);
        sb.append(m_round.toString());
        sb.append(PokerCommand.DELIMITER);
        return sb.toString();
    }
    
    public ArrayList<Integer> getCardsId()
    {
        return m_cardsID;
    }
    
    public TypePokerGameRound getRound()
    {
        return m_round;
    }
}

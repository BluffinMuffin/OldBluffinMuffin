package protocolGame;

import java.util.ArrayList;
import java.util.StringTokenizer;

import pokerLogic.OldTypePokerRound;
import protocolTools.IPokerCommand;
import utility.Constants;

public class GameBetTurnEndedCommand implements IPokerCommand
{
    private final ArrayList<Integer> m_potsAmounts;
    private final OldTypePokerRound m_round;
    public static String COMMAND_NAME = "gameBET_TURN_ENDED";
    
    public GameBetTurnEndedCommand(StringTokenizer argsToken)
    {
        final int count = Integer.parseInt(argsToken.nextToken());
        m_potsAmounts = new ArrayList<Integer>();
        for (int i = 0; i < count; ++i)
        {
            m_potsAmounts.add(Integer.parseInt(argsToken.nextToken()));
        }
        m_round = OldTypePokerRound.valueOf(argsToken.nextToken());
    }
    
    public GameBetTurnEndedCommand(ArrayList<Integer> potsAmounts, OldTypePokerRound round)
    {
        m_potsAmounts = potsAmounts;
        m_round = round;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameBetTurnEndedCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_potsAmounts.size());
        sb.append(Constants.DELIMITER);
        for (int i = 0; i < m_potsAmounts.size(); ++i)
        {
            sb.append(m_potsAmounts.get(i));
            sb.append(Constants.DELIMITER);
        }
        sb.append(m_round.toString());
        return sb.toString();
    }
    
    public ArrayList<Integer> getPotsAmounts()
    {
        return m_potsAmounts;
    }
    
    public OldTypePokerRound getRound()
    {
        return m_round;
    }
}

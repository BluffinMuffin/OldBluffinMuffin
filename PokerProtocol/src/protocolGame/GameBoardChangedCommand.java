package protocolGame;

import java.util.ArrayList;
import java.util.StringTokenizer;

import protocolTools.IBluffinCommand;
import utility.Constants;

public class GameBoardChangedCommand implements IBluffinCommand
{
    private final ArrayList<Integer> m_cardsID = new ArrayList<Integer>();
    public static String COMMAND_NAME = "gameBOARD_CHANGED";
    
    public GameBoardChangedCommand(StringTokenizer argsToken)
    {
        for (int i = 0; i < 5; ++i)
        {
            m_cardsID.add(Integer.parseInt(argsToken.nextToken()));
        }
    }
    
    public GameBoardChangedCommand(Integer flop1, Integer flop2, Integer flop3, Integer turn, Integer river)
    {
        m_cardsID.add(flop1);
        m_cardsID.add(flop2);
        m_cardsID.add(flop3);
        m_cardsID.add(turn);
        m_cardsID.add(river);
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameBoardChangedCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        for (int i = 0; i < 5; ++i)
        {
            sb.append(m_cardsID.get(i));
            sb.append(Constants.DELIMITER);
        }
        return sb.toString();
    }
    
    public ArrayList<Integer> getCardsId()
    {
        return m_cardsID;
    }
}

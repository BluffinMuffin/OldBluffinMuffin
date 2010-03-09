package protocolGame;

import java.util.StringTokenizer;

import pokerLogic.OldPokerPlayerAction;
import pokerLogic.OldTypePlayerAction;
import protocolTools.IPokerCommand;
import utility.Constants;

public class GameSendActionCommand implements IPokerCommand
{
    
    private final OldPokerPlayerAction m_action;
    public static String COMMAND_NAME = "gameSEND_ACTION";
    
    public GameSendActionCommand(StringTokenizer argsToken)
    {
        m_action = new OldPokerPlayerAction();
        m_action.setType(OldTypePlayerAction.valueOf(argsToken.nextToken()));
        m_action.setAmount(Integer.parseInt(argsToken.nextToken()));
    }
    
    public GameSendActionCommand(OldPokerPlayerAction action)
    {
        m_action = action;
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(GameSendActionCommand.COMMAND_NAME);
        sb.append(Constants.DELIMITER);
        sb.append(m_action.getType().name());
        sb.append(Constants.DELIMITER);
        sb.append(m_action.getAmount());
        return sb.toString();
    }
    
    public OldPokerPlayerAction getAction()
    {
        return m_action;
    }
}

package protocolGame;

import java.util.StringTokenizer;

import pokerLogic.PokerPlayerAction;
import pokerLogic.TypePlayerAction;
import protocolLogic.IBluffinCommand;
import utility.Constants;

public class GameSendActionCommand implements IBluffinCommand
{
    
    private final PokerPlayerAction m_action;
    public static String COMMAND_NAME = "gameSEND_ACTION";
    
    public GameSendActionCommand(StringTokenizer argsToken)
    {
        m_action = new PokerPlayerAction();
        m_action.setType(TypePlayerAction.valueOf(argsToken.nextToken()));
        m_action.setAmount(Integer.parseInt(argsToken.nextToken()));
    }
    
    public GameSendActionCommand(PokerPlayerAction action)
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
    
    public PokerPlayerAction getAction()
    {
        return m_action;
    }
}

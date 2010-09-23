package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameBetLimitType;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;
import bluffinmuffin.protocol.commands.lobby.AbstractCreateTableCommand;

public class CreateCareerTableCommand extends AbstractCreateTableCommand implements ICommand
{
    public static String COMMAND_NAME = "lobbyCAREER_CREATE_TABLE";
    
    public CreateCareerTableCommand(StringTokenizer argsToken)
    {
        super(argsToken);
    }
    
    public CreateCareerTableCommand(String p_tableName, int p_bigBlind, int p_maxPlayers, String p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, GameBetLimitType limit)
    {
        super(p_tableName, p_bigBlind, p_maxPlayers, p_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit);
    }
    
    @Override
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CreateCareerTableCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(super.encodeCommand());
        return sb.toString();
    }
    
    public String encodeResponse(Integer noPort)
    {
        return new CreateCareerTableResponse(m_tableName, m_bigBlind, m_maxPlayers, m_playerName, m_WaitingTimeAfterPlayerAction, m_WaitingTimeAfterBoardDealed, m_WaitingTimeAfterPotWon, m_limit, noPort).encodeCommand();
    }
    
}

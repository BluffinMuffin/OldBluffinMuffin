package bluffinmuffin.protocol.commands.lobby.career;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameBetLimitType;
import bluffinmuffin.protocol.commands.lobby.AbstractCreateTableCommand;

public class CreateCareerTableCommand extends AbstractCreateTableCommand
{
    @Override
    protected String getCommandName()
    {
        return CreateCareerTableCommand.COMMAND_NAME;
    }
    
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
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
    }
    
    public String encodeResponse(Integer noPort)
    {
        return new CreateCareerTableResponse(this, noPort).encode();
    }
    
}

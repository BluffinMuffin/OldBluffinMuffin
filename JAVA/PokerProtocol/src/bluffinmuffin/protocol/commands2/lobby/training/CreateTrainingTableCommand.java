package bluffinmuffin.protocol.commands2.lobby.training;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameBetLimitType;
import bluffinmuffin.protocol.commands2.lobby.AbstractCreateTableCommand;

public class CreateTrainingTableCommand extends AbstractCreateTableCommand
{
    @Override
    protected String getCommandName()
    {
        return CreateTrainingTableCommand.COMMAND_NAME;
    }
    
    public static String COMMAND_NAME = "lobbyTRAINING_CREATE_TABLE";
    protected final int m_startingMoney;
    
    public CreateTrainingTableCommand(StringTokenizer argsToken)
    {
        super(argsToken);
        m_startingMoney = Integer.parseInt(argsToken.nextToken());
    }
    
    public CreateTrainingTableCommand(String p_tableName, int p_bigBlind, int p_maxPlayers, String p_playerName, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, GameBetLimitType limit, int startingMoney)
    {
        super(p_tableName, p_bigBlind, p_maxPlayers, p_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit);
        m_startingMoney = startingMoney;
    }
    
    @Override
    public void encode(StringBuilder sb)
    {
        super.encode(sb);
        append(sb, m_startingMoney);
    }
    
    public String encodeResponse(Integer noPort)
    {
        return new CreateTrainingTableResponse(this, noPort).encode();
    }
    
    public int getStartingMoney()
    {
        return m_startingMoney;
    }
}

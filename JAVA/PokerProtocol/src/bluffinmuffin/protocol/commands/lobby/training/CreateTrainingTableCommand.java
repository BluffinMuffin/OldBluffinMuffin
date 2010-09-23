package bluffinmuffin.protocol.commands.lobby.training;

import java.util.StringTokenizer;

import bluffinmuffin.poker.entities.type.GameBetLimitType;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.ICommand;
import bluffinmuffin.protocol.commands.lobby.AbstractCreateTableCommand;

public class CreateTrainingTableCommand extends AbstractCreateTableCommand implements ICommand
{
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
    public String encodeCommand()
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(CreateTrainingTableCommand.COMMAND_NAME);
        sb.append(Command.L_DELIMITER);
        sb.append(super.encodeCommand());
        sb.append(m_startingMoney);
        sb.append(Command.L_DELIMITER);
        return sb.toString();
    }
    
    public String encodeResponse(Integer noPort)
    {
        return new CreateTrainingTableResponse(m_tableName, m_bigBlind, m_maxPlayers, m_playerName, m_WaitingTimeAfterPlayerAction, m_WaitingTimeAfterBoardDealed, m_WaitingTimeAfterPotWon, m_limit, m_startingMoney, noPort).encodeCommand();
    }
    
    public int getStartingMoney()
    {
        return m_startingMoney;
    }
}

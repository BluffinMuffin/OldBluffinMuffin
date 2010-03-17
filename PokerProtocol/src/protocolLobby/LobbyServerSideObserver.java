package protocolLobby;

import java.util.StringTokenizer;

import protocol.PokerCommand;
import protocol.PokerCommandObserver;
import protocolLobbyCommands.LobbyIdentifyCommand;
import protocolLobbyCommands.LobbyCreateTableCommand;
import protocolLobbyCommands.LobbyDisconnectCommand;
import protocolLobbyCommands.LobbyJoinTableCommand;
import protocolLobbyCommands.LobbyListTableCommand;

public class LobbyServerSideObserver extends PokerCommandObserver<LobbyServerSideListener>
{
    @Override
    protected void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, PokerCommand.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(LobbyIdentifyCommand.COMMAND_NAME))
        {
            authentification(new LobbyIdentifyCommand(token));
        }
        else if (commandName.equals(LobbyDisconnectCommand.COMMAND_NAME))
        {
            disconnect(new LobbyDisconnectCommand(token));
        }
        else if (commandName.equals(LobbyCreateTableCommand.COMMAND_NAME))
        {
            createTable(new LobbyCreateTableCommand(token));
        }
        else if (commandName.equals(LobbyListTableCommand.COMMAND_NAME))
        {
            listTables(new LobbyListTableCommand(token));
        }
        else if (commandName.equals(LobbyJoinTableCommand.COMMAND_NAME))
        {
            joinTables(new LobbyJoinTableCommand(token));
        }
        else
        {
            super.commandReceived(line);
        }
    }
    
    private void joinTables(LobbyJoinTableCommand lobbyJoinTableCommand)
    {
        for (final LobbyServerSideListener listener : getSubscribers())
        {
            listener.joinTableCommandReceived(lobbyJoinTableCommand);
        }
    }
    
    private void listTables(LobbyListTableCommand lobbyListTableCommand)
    {
        for (final LobbyServerSideListener listener : getSubscribers())
        {
            listener.listTableCommandReceived(lobbyListTableCommand);
        }
    }
    
    private void createTable(LobbyCreateTableCommand lobbyCreateTableCommand)
    {
        for (final LobbyServerSideListener listener : getSubscribers())
        {
            listener.createTableCommandReceived(lobbyCreateTableCommand);
        }
    }
    
    private void disconnect(LobbyDisconnectCommand lobbyDisconnectCommand)
    {
        for (final LobbyServerSideListener listener : getSubscribers())
        {
            listener.disconnectCommandReceived(lobbyDisconnectCommand);
        }
    }
    
    private void authentification(LobbyIdentifyCommand lobbyConnectCommand)
    {
        for (final LobbyServerSideListener listener : getSubscribers())
        {
            listener.connectCommandReceived(lobbyConnectCommand);
        }
    }
}

package protocolLobbyTools;

import java.util.StringTokenizer;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobby.LobbyListTableCommand;
import protocolTools.PokerCommandObserver;
import utility.Constants;

public class LobbyServerSideObserver extends PokerCommandObserver<LobbyServerSideListener>
{
    @Override
    protected void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, Constants.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(LobbyConnectCommand.COMMAND_NAME))
        {
            authentification(new LobbyConnectCommand(token));
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
    
    private void authentification(LobbyConnectCommand lobbyConnectCommand)
    {
        for (final LobbyServerSideListener listener : getSubscribers())
        {
            listener.connectCommandReceived(lobbyConnectCommand);
        }
    }
}

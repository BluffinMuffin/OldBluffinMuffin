package protocol.lobby.observer;

import java.util.StringTokenizer;

import protocol.Command;
import protocol.commands.DisconnectCommand;
import protocol.lobby.commands.CreateTableCommand;
import protocol.lobby.commands.IdentifyCommand;
import protocol.lobby.commands.JoinTableCommand;
import protocol.lobby.commands.ListTableCommand;
import protocol.observer.CommandObserver;

public class LobbyServerObserver extends CommandObserver<ILobbyServerListener>
{
    @Override
    protected void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, Command.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(IdentifyCommand.COMMAND_NAME))
        {
            authentification(new IdentifyCommand(token));
        }
        else if (commandName.equals(DisconnectCommand.COMMAND_NAME))
        {
            disconnect(new DisconnectCommand(token));
        }
        else if (commandName.equals(CreateTableCommand.COMMAND_NAME))
        {
            createTable(new CreateTableCommand(token));
        }
        else if (commandName.equals(ListTableCommand.COMMAND_NAME))
        {
            listTables(new ListTableCommand(token));
        }
        else if (commandName.equals(JoinTableCommand.COMMAND_NAME))
        {
            joinTables(new JoinTableCommand(token));
        }
        else
        {
            super.commandReceived(line);
        }
    }
    
    private void joinTables(JoinTableCommand lobbyJoinTableCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.joinTableCommandReceived(lobbyJoinTableCommand);
        }
    }
    
    private void listTables(ListTableCommand lobbyListTableCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.listTableCommandReceived(lobbyListTableCommand);
        }
    }
    
    private void createTable(CreateTableCommand lobbyCreateTableCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.createTableCommandReceived(lobbyCreateTableCommand);
        }
    }
    
    private void disconnect(DisconnectCommand lobbyDisconnectCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.disconnectCommandReceived(lobbyDisconnectCommand);
        }
    }
    
    private void authentification(IdentifyCommand lobbyConnectCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.connectCommandReceived(lobbyConnectCommand);
        }
    }
}

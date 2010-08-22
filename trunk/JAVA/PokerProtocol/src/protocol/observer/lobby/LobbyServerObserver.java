package protocol.observer.lobby;

import java.util.StringTokenizer;

import protocol.commands.Command;
import protocol.commands.DisconnectCommand;
import protocol.commands.lobby.CreateTableCommand;
import protocol.commands.lobby.GameCommand;
import protocol.commands.lobby.IdentifyCommand;
import protocol.commands.lobby.JoinTableCommand;
import protocol.commands.lobby.ListTableCommand;
import protocol.observer.CommandObserver;

public class LobbyServerObserver extends CommandObserver<ILobbyServerListener>
{
    @Override
    protected void commandReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, Command.L_DELIMITER);
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
        else if (commandName.equals(GameCommand.COMMAND_NAME))
        {
            gameCommand(new GameCommand(token));
        }
        else
        {
            super.commandReceived(line);
        }
    }
    
    private void gameCommand(GameCommand gameCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.gameCommandReceived(gameCommand);
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

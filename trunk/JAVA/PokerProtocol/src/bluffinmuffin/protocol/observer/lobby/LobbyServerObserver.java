package bluffinmuffin.protocol.observer.lobby;

import java.util.StringTokenizer;

import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.DisconnectCommand;
import bluffinmuffin.protocol.commands.lobby.CreateTableCommand;
import bluffinmuffin.protocol.commands.lobby.GameCommand;
import bluffinmuffin.protocol.commands.lobby.JoinTableCommand;
import bluffinmuffin.protocol.commands.lobby.ListTableCommand;
import bluffinmuffin.protocol.commands.lobby.career.AuthenticateUserCommand;
import bluffinmuffin.protocol.commands.lobby.career.CheckDisplayExistCommand;
import bluffinmuffin.protocol.commands.lobby.career.CheckUserExistCommand;
import bluffinmuffin.protocol.commands.lobby.career.CreateUserCommand;
import bluffinmuffin.protocol.commands.lobby.career.GetUserCommand;
import bluffinmuffin.protocol.commands.lobby.training.IdentifyCommand;
import bluffinmuffin.protocol.observer.CommandObserver;

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
            game(new GameCommand(token));
        }
        else if (commandName.equals(CreateUserCommand.COMMAND_NAME))
        {
            createUser(new CreateUserCommand(token));
        }
        else if (commandName.equals(CheckUserExistCommand.COMMAND_NAME))
        {
            checkUserExist(new CheckUserExistCommand(token));
        }
        else if (commandName.equals(CheckDisplayExistCommand.COMMAND_NAME))
        {
            checkDisplayExist(new CheckDisplayExistCommand(token));
        }
        else if (commandName.equals(AuthenticateUserCommand.COMMAND_NAME))
        {
            authenticateUser(new AuthenticateUserCommand(token));
        }
        else if (commandName.equals(GetUserCommand.COMMAND_NAME))
        {
            getUser(new GetUserCommand(token));
        }
        else
        {
            super.commandReceived(line);
        }
    }
    
    private void createUser(CreateUserCommand createUserCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.createUserCommandReceived(createUserCommand);
        }
    }
    
    private void checkUserExist(CheckUserExistCommand checkUserExistCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.checkUserExistCommandReceived(checkUserExistCommand);
        }
    }
    
    private void checkDisplayExist(CheckDisplayExistCommand checkDisplayExistCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.checkDisplayExistCommandReceived(checkDisplayExistCommand);
        }
    }
    
    private void authenticateUser(AuthenticateUserCommand authenticateUserCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.authenticateUserCommandReceived(authenticateUserCommand);
        }
    }
    
    private void getUser(GetUserCommand getUserCommand)
    {
        for (final ILobbyServerListener listener : getSubscribers())
        {
            listener.getUserCommandReceived(getUserCommand);
        }
    }
    
    private void game(GameCommand gameCommand)
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
            listener.identifyCommandReceived(lobbyConnectCommand);
        }
    }
}

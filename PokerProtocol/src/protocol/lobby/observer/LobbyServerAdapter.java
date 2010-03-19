package protocol.lobby.observer;

import protocol.commands.DisconnectCommand;
import protocol.lobby.commands.CreateTableCommand;
import protocol.lobby.commands.IdentifyCommand;
import protocol.lobby.commands.JoinTableCommand;
import protocol.lobby.commands.ListTableCommand;

public abstract class LobbyServerAdapter implements ILobbyServerListener
{
    
    @Override
    public void commandReceived(String command)
    {
    }
    
    @Override
    public void connectCommandReceived(IdentifyCommand command)
    {
    }
    
    @Override
    public void createTableCommandReceived(CreateTableCommand command)
    {
    }
    
    @Override
    public void disconnectCommandReceived(DisconnectCommand command)
    {
    }
    
    @Override
    public void joinTableCommandReceived(JoinTableCommand command)
    {
    }
    
    @Override
    public void listTableCommandReceived(ListTableCommand command)
    {
    }
}

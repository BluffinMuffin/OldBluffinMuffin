package protocol.observer.lobby;

import protocol.commands.DisconnectCommand;
import protocol.commands.lobby.CreateTableCommand;
import protocol.commands.lobby.GameCommand;
import protocol.commands.lobby.IdentifyCommand;
import protocol.commands.lobby.JoinTableCommand;
import protocol.commands.lobby.ListTableCommand;

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
    
    @Override
    public void gameCommandReceived(GameCommand command)
    {
    }
}

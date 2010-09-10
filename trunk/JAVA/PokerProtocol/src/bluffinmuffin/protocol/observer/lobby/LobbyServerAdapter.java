package bluffinmuffin.protocol.observer.lobby;

import bluffinmuffin.protocol.commands.DisconnectCommand;
import bluffinmuffin.protocol.commands.lobby.CreateTableCommand;
import bluffinmuffin.protocol.commands.lobby.GameCommand;
import bluffinmuffin.protocol.commands.lobby.IdentifyCommand;
import bluffinmuffin.protocol.commands.lobby.JoinTableCommand;
import bluffinmuffin.protocol.commands.lobby.ListTableCommand;

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

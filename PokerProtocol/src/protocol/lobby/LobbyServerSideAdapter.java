package protocol.lobby;

import protocol.lobby.commands.LobbyCreateTableCommand;
import protocol.lobby.commands.LobbyDisconnectCommand;
import protocol.lobby.commands.LobbyIdentifyCommand;
import protocol.lobby.commands.LobbyJoinTableCommand;
import protocol.lobby.commands.LobbyListTableCommand;

public abstract class LobbyServerSideAdapter implements LobbyServerSideListener
{
    
    @Override
    public void commandReceived(String command)
    {
    }
    
    @Override
    public void connectCommandReceived(LobbyIdentifyCommand command)
    {
    }
    
    @Override
    public void createTableCommandReceived(LobbyCreateTableCommand command)
    {
    }
    
    @Override
    public void disconnectCommandReceived(LobbyDisconnectCommand command)
    {
    }
    
    @Override
    public void joinTableCommandReceived(LobbyJoinTableCommand command)
    {
    }
    
    @Override
    public void listTableCommandReceived(LobbyListTableCommand command)
    {
    }
}

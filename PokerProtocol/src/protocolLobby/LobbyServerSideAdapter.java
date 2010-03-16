package protocolLobby;

import protocolLobbyCommands.LobbyConnectCommand;
import protocolLobbyCommands.LobbyCreateTableCommand;
import protocolLobbyCommands.LobbyDisconnectCommand;
import protocolLobbyCommands.LobbyJoinTableCommand;
import protocolLobbyCommands.LobbyListTableCommand;

public abstract class LobbyServerSideAdapter implements LobbyServerSideListener
{
    
    @Override
    public void commandReceived(String command)
    {
    }
    
    @Override
    public void connectCommandReceived(LobbyConnectCommand command)
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

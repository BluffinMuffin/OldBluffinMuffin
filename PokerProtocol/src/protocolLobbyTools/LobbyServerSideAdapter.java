package protocolLobbyTools;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobby.LobbyListTableCommand;

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

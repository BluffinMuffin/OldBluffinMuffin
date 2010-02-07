package protocolLobbyTools;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobby.LobbyListTableCommand;

public abstract class LobbyServerSideAdapter implements LobbyServerSideListener
{
    
    public void commandReceived(String command)
    {
    }
    
    public void connectCommandReceived(LobbyConnectCommand command)
    {
    }
    
    public void createTableCommandReceived(LobbyCreateTableCommand command)
    {
    }
    
    public void disconnectCommandReceived(LobbyDisconnectCommand command)
    {
    }
    
    public void joinTableCommandReceived(LobbyJoinTableCommand command)
    {
    }
    
    public void listTableCommandReceived(LobbyListTableCommand command)
    {
    }
}

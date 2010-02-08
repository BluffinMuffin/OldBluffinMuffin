package protocolLobbyTools;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobby.LobbyListTableCommand;
import protocolTools.BluffinCommandListener;

public interface LobbyServerSideListener extends BluffinCommandListener
{
    void connectCommandReceived(LobbyConnectCommand command);
    
    void createTableCommandReceived(LobbyCreateTableCommand command);
    
    void disconnectCommandReceived(LobbyDisconnectCommand command);
    
    void joinTableCommandReceived(LobbyJoinTableCommand command);
    
    void listTableCommandReceived(LobbyListTableCommand command);
}

package protocolLobbyTools;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobby.LobbyListTableCommand;
import protocolTools.PokerCommandListener;

public interface LobbyServerSideListener extends PokerCommandListener
{
    void connectCommandReceived(LobbyConnectCommand command);
    
    void createTableCommandReceived(LobbyCreateTableCommand command);
    
    void disconnectCommandReceived(LobbyDisconnectCommand command);
    
    void joinTableCommandReceived(LobbyJoinTableCommand command);
    
    void listTableCommandReceived(LobbyListTableCommand command);
}

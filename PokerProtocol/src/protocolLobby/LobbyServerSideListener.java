package protocolLobby;

import protocol.PokerCommandListener;
import protocolLobbyCommands.LobbyConnectCommand;
import protocolLobbyCommands.LobbyCreateTableCommand;
import protocolLobbyCommands.LobbyDisconnectCommand;
import protocolLobbyCommands.LobbyJoinTableCommand;
import protocolLobbyCommands.LobbyListTableCommand;

public interface LobbyServerSideListener extends PokerCommandListener
{
    void connectCommandReceived(LobbyConnectCommand command);
    
    void createTableCommandReceived(LobbyCreateTableCommand command);
    
    void disconnectCommandReceived(LobbyDisconnectCommand command);
    
    void joinTableCommandReceived(LobbyJoinTableCommand command);
    
    void listTableCommandReceived(LobbyListTableCommand command);
}

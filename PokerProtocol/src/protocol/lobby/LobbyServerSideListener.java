package protocol.lobby;

import protocol.PokerCommandListener;
import protocol.lobby.commands.LobbyCreateTableCommand;
import protocol.lobby.commands.LobbyDisconnectCommand;
import protocol.lobby.commands.LobbyIdentifyCommand;
import protocol.lobby.commands.LobbyJoinTableCommand;
import protocol.lobby.commands.LobbyListTableCommand;

public interface LobbyServerSideListener extends PokerCommandListener
{
    void connectCommandReceived(LobbyIdentifyCommand command);
    
    void createTableCommandReceived(LobbyCreateTableCommand command);
    
    void disconnectCommandReceived(LobbyDisconnectCommand command);
    
    void joinTableCommandReceived(LobbyJoinTableCommand command);
    
    void listTableCommandReceived(LobbyListTableCommand command);
}

package protocol.observer.lobby;

import protocol.commands.DisconnectCommand;
import protocol.commands.lobby.CreateTableCommand;
import protocol.commands.lobby.GameCommand;
import protocol.commands.lobby.IdentifyCommand;
import protocol.commands.lobby.JoinTableCommand;
import protocol.commands.lobby.ListTableCommand;
import protocol.observer.ICommandListener;

public interface ILobbyServerListener extends ICommandListener
{
    void connectCommandReceived(IdentifyCommand command);
    
    void createTableCommandReceived(CreateTableCommand command);
    
    void disconnectCommandReceived(DisconnectCommand command);
    
    void joinTableCommandReceived(JoinTableCommand command);
    
    void listTableCommandReceived(ListTableCommand command);
    
    void gameCommandReceived(GameCommand command);
}

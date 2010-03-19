package protocol.lobby.observer;

import protocol.commands.DisconnectCommand;
import protocol.lobby.commands.CreateTableCommand;
import protocol.lobby.commands.IdentifyCommand;
import protocol.lobby.commands.JoinTableCommand;
import protocol.lobby.commands.ListTableCommand;
import protocol.observer.ICommandListener;

public interface ILobbyServerListener extends ICommandListener
{
    void connectCommandReceived(IdentifyCommand command);
    
    void createTableCommandReceived(CreateTableCommand command);
    
    void disconnectCommandReceived(DisconnectCommand command);
    
    void joinTableCommandReceived(JoinTableCommand command);
    
    void listTableCommandReceived(ListTableCommand command);
}

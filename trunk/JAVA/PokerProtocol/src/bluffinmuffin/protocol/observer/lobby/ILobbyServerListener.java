package bluffinmuffin.protocol.observer.lobby;

import bluffinmuffin.protocol.commands.DisconnectCommand;
import bluffinmuffin.protocol.commands.lobby.CreateTableCommand;
import bluffinmuffin.protocol.commands.lobby.GameCommand;
import bluffinmuffin.protocol.commands.lobby.IdentifyCommand;
import bluffinmuffin.protocol.commands.lobby.JoinTableCommand;
import bluffinmuffin.protocol.commands.lobby.ListTableCommand;
import bluffinmuffin.protocol.observer.ICommandListener;

public interface ILobbyServerListener extends ICommandListener
{
    void connectCommandReceived(IdentifyCommand command);
    
    void createTableCommandReceived(CreateTableCommand command);
    
    void disconnectCommandReceived(DisconnectCommand command);
    
    void joinTableCommandReceived(JoinTableCommand command);
    
    void listTableCommandReceived(ListTableCommand command);
    
    void gameCommandReceived(GameCommand command);
}

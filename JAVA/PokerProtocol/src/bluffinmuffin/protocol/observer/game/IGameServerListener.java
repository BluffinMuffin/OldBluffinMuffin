package bluffinmuffin.protocol.observer.game;

import bluffinmuffin.protocol.commands2.DisconnectCommand;
import bluffinmuffin.protocol.commands2.game.PlayerPlayMoneyCommand;
import bluffinmuffin.protocol.observer.ICommandListener;

public interface IGameServerListener extends ICommandListener
{
    void playMoneyCommandReceived(PlayerPlayMoneyCommand command);
    
    void disconnectCommandReceived(DisconnectCommand command);
}

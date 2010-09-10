package bluffinmuffin.protocol.observer.game;

import bluffinmuffin.protocol.commands.DisconnectCommand;
import bluffinmuffin.protocol.commands.game.PlayerPlayMoneyCommand;
import bluffinmuffin.protocol.observer.ICommandListener;

public interface IGameServerListener extends ICommandListener
{
    void playMoneyCommandReceived(PlayerPlayMoneyCommand command);
    
    void disconnectCommandReceived(DisconnectCommand command);
}

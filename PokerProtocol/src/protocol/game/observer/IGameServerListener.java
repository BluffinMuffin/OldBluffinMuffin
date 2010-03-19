package protocol.game.observer;

import protocol.game.commands.PlayerPlayMoneyCommand;
import protocol.observer.ICommandListener;

public interface IGameServerListener extends ICommandListener
{
    void playMoneyCommandReceived(PlayerPlayMoneyCommand command);
    
    void disconnectCommandReceived(PlayerPlayMoneyCommand command);
}

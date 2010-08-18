package protocol.observer.game;

import protocol.commands.DisconnectCommand;
import protocol.commands.game.PlayerPlayMoneyCommand;
import protocol.observer.ICommandListener;

public interface IGameServerListener extends ICommandListener
{
    void playMoneyCommandReceived(PlayerPlayMoneyCommand command);
    
    void disconnectCommandReceived(DisconnectCommand command);
}

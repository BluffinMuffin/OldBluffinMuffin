package protocol.game;

import protocol.PokerCommandListener;
import protocol.game.commands.GamePlayMoneyCommand;

public interface GameServerSideListener extends PokerCommandListener
{
    void playMoneyCommandReceived(GamePlayMoneyCommand command);
    
    void disconnectCommandReceived(GamePlayMoneyCommand command);
}

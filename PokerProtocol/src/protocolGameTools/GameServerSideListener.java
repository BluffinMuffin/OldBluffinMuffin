package protocolGameTools;

import protocol.PokerCommandListener;
import protocolGameCommands.GamePlayMoneyCommand;

public interface GameServerSideListener extends PokerCommandListener
{
    void playMoneyCommandReceived(GamePlayMoneyCommand command);
    
    void disconnectCommandReceived(GamePlayMoneyCommand command);
}

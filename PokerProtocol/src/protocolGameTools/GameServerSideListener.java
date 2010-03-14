package protocolGameTools;

import protocolGame.GamePlayMoneyCommand;
import protocolTools.PokerCommandListener;

public interface GameServerSideListener extends PokerCommandListener
{
    void playMoneyCommandReceived(GamePlayMoneyCommand command);
    
    void disconnectCommandReceived(GamePlayMoneyCommand command);
}

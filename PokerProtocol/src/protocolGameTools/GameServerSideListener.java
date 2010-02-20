package protocolGameTools;

import protocolGame.GameSendActionCommand;
import protocolTools.PokerCommandListener;

public interface GameServerSideListener extends PokerCommandListener
{
    void sendActionCommandReceived(GameSendActionCommand command);
}

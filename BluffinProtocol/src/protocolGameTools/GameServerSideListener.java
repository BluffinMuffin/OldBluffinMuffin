package protocolGameTools;

import protocolGame.GameSendActionCommand;
import protocolTools.BluffinCommandListener;

public interface GameServerSideListener extends BluffinCommandListener
{
    void sendActionCommandReceived(GameSendActionCommand command);
}

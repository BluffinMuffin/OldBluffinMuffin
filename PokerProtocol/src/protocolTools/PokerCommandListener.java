package protocolTools;

import java.util.EventListener;

public interface PokerCommandListener extends EventListener
{
    void commandReceived(String command);
}

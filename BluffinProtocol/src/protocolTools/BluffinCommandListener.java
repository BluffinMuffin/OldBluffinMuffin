package protocolTools;

import java.util.EventListener;

public interface BluffinCommandListener extends EventListener
{
    void commandReceived(String command);
}

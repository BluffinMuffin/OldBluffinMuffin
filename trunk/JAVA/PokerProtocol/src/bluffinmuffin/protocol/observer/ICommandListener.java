package bluffinmuffin.protocol.observer;

import java.util.EventListener;

public interface ICommandListener extends EventListener
{
    void commandReceived(String command);
}

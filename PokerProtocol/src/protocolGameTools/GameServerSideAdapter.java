package protocolGameTools;

import protocolGame.GameSendActionCommand;

public abstract class GameServerSideAdapter implements GameServerSideListener
{
    
    @Override
    public void commandReceived(String command)
    {
    }
    
    @Override
    public void sendActionCommandReceived(GameSendActionCommand command)
    {
    }
}

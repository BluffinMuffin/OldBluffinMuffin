package protocol.game.observer;

import protocol.game.commands.PlayerPlayMoneyCommand;

public class GameServerAdapter implements IGameServerListener
{
    
    @Override
    public void commandReceived(String command)
    {
    }
    
    @Override
    public void playMoneyCommandReceived(PlayerPlayMoneyCommand command)
    {
    }
    
    @Override
    public void disconnectCommandReceived(PlayerPlayMoneyCommand command)
    {
    }
}

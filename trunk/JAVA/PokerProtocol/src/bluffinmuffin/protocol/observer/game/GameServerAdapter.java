package bluffinmuffin.protocol.observer.game;

import bluffinmuffin.protocol.commands2.DisconnectCommand;
import bluffinmuffin.protocol.commands2.game.PlayerPlayMoneyCommand;

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
    public void disconnectCommandReceived(DisconnectCommand command)
    {
    }
}

package protocol.observer.game;

import protocol.commands.DisconnectCommand;
import protocol.commands.game.PlayerPlayMoneyCommand;

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

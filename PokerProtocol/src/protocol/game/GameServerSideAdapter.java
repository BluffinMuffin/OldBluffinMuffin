package protocol.game;

import protocol.game.commands.GamePlayMoneyCommand;

public class GameServerSideAdapter implements GameServerSideListener
{
    
    @Override
    public void commandReceived(String command)
    {
    }
    
    @Override
    public void playMoneyCommandReceived(GamePlayMoneyCommand command)
    {
    }
    
    @Override
    public void disconnectCommandReceived(GamePlayMoneyCommand command)
    {
    }
}

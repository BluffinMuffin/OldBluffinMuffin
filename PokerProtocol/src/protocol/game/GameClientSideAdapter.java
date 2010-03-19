package protocol.game;

import protocol.game.commands.GameBetTurnEndedCommand;
import protocol.game.commands.GameBetTurnStartedCommand;
import protocol.game.commands.GameEndedCommand;
import protocol.game.commands.GameHoleCardsChangedCommand;
import protocol.game.commands.GamePlayerJoinedCommand;
import protocol.game.commands.GamePlayerLeftCommand;
import protocol.game.commands.GamePlayerMoneyChangedCommand;
import protocol.game.commands.GamePlayerTurnBeganCommand;
import protocol.game.commands.GamePlayerTurnEndedCommand;
import protocol.game.commands.GamePlayerWonPotCommand;
import protocol.game.commands.GameStartedCommand;
import protocol.game.commands.GameTableClosedCommand;
import protocol.game.commands.GameTableInfoCommand;

public class GameClientSideAdapter implements GameClientSideListener
{
    
    @Override
    public void betTurnEndedCommandReceived(GameBetTurnEndedCommand command)
    {
    }
    
    @Override
    public void betTurnStartedCommandReceived(GameBetTurnStartedCommand command)
    {
    }
    
    @Override
    public void gameEndedCommandReceived(GameEndedCommand command)
    {
    }
    
    @Override
    public void gameStartedCommandReceived(GameStartedCommand command)
    {
    }
    
    @Override
    public void holeCardsChangedCommandReceived(GameHoleCardsChangedCommand command)
    {
    }
    
    @Override
    public void playerJoinedCommandReceived(GamePlayerJoinedCommand command)
    {
    }
    
    @Override
    public void playerLeftCommandReceived(GamePlayerLeftCommand command)
    {
    }
    
    @Override
    public void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command)
    {
    }
    
    @Override
    public void playerTurnBeganCommandReceived(GamePlayerTurnBeganCommand command)
    {
    }
    
    @Override
    public void playerTurnEndedCommandReceived(GamePlayerTurnEndedCommand command)
    {
    }
    
    @Override
    public void playerWonPotCommandReceived(GamePlayerWonPotCommand command)
    {
    }
    
    @Override
    public void tableClosedCommandReceived(GameTableClosedCommand command)
    {
    }
    
    @Override
    public void tableInfoCommandReceived(GameTableInfoCommand command)
    {
    }
    
    @Override
    public void commandReceived(String command)
    {
    }
}

package protocolGame;

import protocolGameCommands.GameBetTurnEndedCommand;
import protocolGameCommands.GameBetTurnStartedCommand;
import protocolGameCommands.GameEndedCommand;
import protocolGameCommands.GameHoleCardsChangedCommand;
import protocolGameCommands.GamePlayerJoinedCommand;
import protocolGameCommands.GamePlayerLeftCommand;
import protocolGameCommands.GamePlayerMoneyChangedCommand;
import protocolGameCommands.GamePlayerTurnBeganCommand;
import protocolGameCommands.GamePlayerTurnEndedCommand;
import protocolGameCommands.GamePlayerWonPotCommand;
import protocolGameCommands.GameStartedCommand;
import protocolGameCommands.GameTableClosedCommand;
import protocolGameCommands.GameTableInfoCommand;

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

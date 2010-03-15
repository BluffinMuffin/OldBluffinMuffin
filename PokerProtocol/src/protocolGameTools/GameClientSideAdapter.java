package protocolGameTools;

import protocolGame.GameAskActionCommand;
import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBetTurnStartedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GameHoleCardsChangedCommand;
import protocolGame.GamePlayerJoinedCommand;
import protocolGame.GamePlayerLeftCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GamePlayerTurnBeganCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GamePlayerWonPotCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableClosedCommand;
import protocolGame.GameTableInfoCommand;

public class GameClientSideAdapter implements GameClientSideListener
{
    
    @Override
    public void askActionCommandReceived(GameAskActionCommand command)
    {
    }
    
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

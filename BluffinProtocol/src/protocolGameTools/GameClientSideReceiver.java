package protocolGameTools;

import java.util.StringTokenizer;

import protocolGame.GameAskActionCommand;
import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBoardChangedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GameHoleCardsChangedCommand;
import protocolGame.GamePINGCommand;
import protocolGame.GamePlayerJoinedCommand;
import protocolGame.GamePlayerLeftCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GamePlayerTurnBeganCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GamePlayerWonPotCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableClosedCommand;
import protocolGame.GameTableInfoCommand;
import protocolGame.GameWaitingCommand;
import protocolTools.BluffinCommandReceiver;
import utility.Constants;

public class GameClientSideReceiver extends BluffinCommandReceiver<GameClientSideListener>
{
    @Override
    protected void onLineReceived(String line)
    {
        final StringTokenizer token = new StringTokenizer(line, Constants.DELIMITER);
        final String commandName = token.nextToken();
        
        if (commandName.equals(GameBetTurnEndedCommand.COMMAND_NAME))
        {
            betTurnEnded(new GameBetTurnEndedCommand(token));
        }
        else if (commandName.equals(GameBoardChangedCommand.COMMAND_NAME))
        {
            boardChanged(new GameBoardChangedCommand(token));
        }
        else if (commandName.equals(GameEndedCommand.COMMAND_NAME))
        {
            gameEnded(new GameEndedCommand(token));
        }
        else if (commandName.equals(GameStartedCommand.COMMAND_NAME))
        {
            gameStarted(new GameStartedCommand(token));
        }
        else if (commandName.equals(GameHoleCardsChangedCommand.COMMAND_NAME))
        {
            playerCardChanged(new GameHoleCardsChangedCommand(token));
        }
        else if (commandName.equals(GamePlayerJoinedCommand.COMMAND_NAME))
        {
            playerJoined(new GamePlayerJoinedCommand(token));
        }
        else if (commandName.equals(GamePlayerLeftCommand.COMMAND_NAME))
        {
            playerLeft(new GamePlayerLeftCommand(token));
        }
        else if (commandName.equals(GamePlayerMoneyChangedCommand.COMMAND_NAME))
        {
            playerMoneyChanged(new GamePlayerMoneyChangedCommand(token));
        }
        else if (commandName.equals(GamePlayerTurnBeganCommand.COMMAND_NAME))
        {
            playerTurnBegan(new GamePlayerTurnBeganCommand(token));
        }
        else if (commandName.equals(GamePlayerTurnEndedCommand.COMMAND_NAME))
        {
            playerTurnEnded(new GamePlayerTurnEndedCommand(token));
        }
        else if (commandName.equals(GamePlayerWonPotCommand.COMMAND_NAME))
        {
            potWon(new GamePlayerWonPotCommand(token));
        }
        else if (commandName.equals(GameTableClosedCommand.COMMAND_NAME))
        {
            tableClosed(new GameTableClosedCommand(token));
        }
        else if (commandName.equals(GameTableInfoCommand.COMMAND_NAME))
        {
            tableInfos(new GameTableInfoCommand(token));
        }
        else if (commandName.equals(GameAskActionCommand.COMMAND_NAME))
        {
            takeAction(new GameAskActionCommand(token));
        }
        else if (commandName.equals(GameWaitingCommand.COMMAND_NAME))
        {
            waitingForPlayers(new GameWaitingCommand(token));
        }
        else if (commandName.equals(GamePINGCommand.COMMAND_NAME))
        {
            ping(new GamePINGCommand(token));
        }
        else
        {
            super.onLineReceived(line);
        }
    }
    
    private void ping(GamePINGCommand gamePINGCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.pingCommandReceived(gamePINGCommand);
        }
    }
    
    private void waitingForPlayers(GameWaitingCommand gameWaitingCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.waitingCommandReceived(gameWaitingCommand);
        }
    }
    
    private void takeAction(GameAskActionCommand gameAskActionCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.askActionCommandReceived(gameAskActionCommand);
        }
    }
    
    private void tableInfos(GameTableInfoCommand gameTableInfoCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.tableInfoCommandReceived(gameTableInfoCommand);
        }
    }
    
    private void tableClosed(GameTableClosedCommand gameTableClosedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.tableClosedCommandReceived(gameTableClosedCommand);
        }
    }
    
    private void potWon(GamePlayerWonPotCommand gamePlayerWonPotCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerWonPotCommandReceived(gamePlayerWonPotCommand);
        }
    }
    
    private void playerTurnEnded(GamePlayerTurnEndedCommand gamePlayerTurnEndedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerTurnEndedCommandReceived(gamePlayerTurnEndedCommand);
        }
    }
    
    private void playerTurnBegan(GamePlayerTurnBeganCommand gamePlayerTurnBeganCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerTurnBeganCommandReceived(gamePlayerTurnBeganCommand);
        }
    }
    
    private void playerMoneyChanged(GamePlayerMoneyChangedCommand gamePlayerMoneyChangedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerMoneyChangedCommandReceived(gamePlayerMoneyChangedCommand);
        }
    }
    
    private void playerLeft(GamePlayerLeftCommand gamePlayerLeftCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerLeftCommandReceived(gamePlayerLeftCommand);
        }
    }
    
    private void playerJoined(GamePlayerJoinedCommand gamePlayerJoinedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.playerJoinedCommandReceived(gamePlayerJoinedCommand);
        }
    }
    
    private void playerCardChanged(GameHoleCardsChangedCommand gameHoleCardsChangedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.holeCardsChangedCommandReceived(gameHoleCardsChangedCommand);
        }
    }
    
    private void gameStarted(GameStartedCommand gameStartedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.gameStartedCommandReceived(gameStartedCommand);
        }
    }
    
    private void gameEnded(GameEndedCommand gameEndedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.gameEndedCommandReceived(gameEndedCommand);
        }
    }
    
    private void boardChanged(GameBoardChangedCommand gameBoardChangedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.boardChangedCommandReceived(gameBoardChangedCommand);
        }
    }
    
    private void betTurnEnded(GameBetTurnEndedCommand gameBetTurnEndedCommand)
    {
        for (final GameClientSideListener listener : getSubscribers())
        {
            listener.betTurnEndedCommandReceived(gameBetTurnEndedCommand);
        }
    }
}

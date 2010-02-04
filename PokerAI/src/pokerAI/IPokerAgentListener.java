package pokerAI;

import java.lang.reflect.Method;
import java.util.ArrayList;

import pokerLogic.PokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;


/**
 * @author Hocus
 *         This interface represents a poker agent that can
 *         only listens to the game (cannot take actions).
 */
public interface IPokerAgentListener extends IPokerAgent
{
    // Methods corresponding to messages type sent by the server.
    public static final Method BET_TURN_ENDED = Util.getIPokerAgentListenerMethod("betTurnEnded", ArrayList.class, TypePokerRound.class);
    public static final Method BOARD_CHANGED = Util.getIPokerAgentListenerMethod("boardChanged", ArrayList.class);
    public static final Method GAME_ENDED = Util.getIPokerAgentListenerMethod("gameEnded");
    public static final Method GAME_STARTED = Util.getIPokerAgentListenerMethod("gameStarted", PokerPlayerInfo.class, PokerPlayerInfo.class, PokerPlayerInfo.class);
    public static final Method PLAYER_CARD_CHANGED = Util.getIPokerAgentListenerMethod("playerCardChanged", PokerPlayerInfo.class);
    public static final Method PLAYER_JOINED = Util.getIPokerAgentListenerMethod("playerJoined", PokerPlayerInfo.class);
    public static final Method PLAYER_LEFT = Util.getIPokerAgentListenerMethod("playerLeft", PokerPlayerInfo.class);
    public static final Method PLAYER_MONEY_CHANGED = Util.getIPokerAgentListenerMethod("playerMoneyChanged", PokerPlayerInfo.class, int.class);
    public static final Method PLAYER_TURN_BEGAN = Util.getIPokerAgentListenerMethod("playerTurnBegan", PokerPlayerInfo.class);
    public static final Method PLAYER_TURN_ENDED = Util.getIPokerAgentListenerMethod("playerTurnEnded", PokerPlayerInfo.class, TypePlayerAction.class, int.class);
    public static final Method POT_WON = Util.getIPokerAgentListenerMethod("potWon", PokerPlayerInfo.class, int.class, int.class);
    public static final Method TABLE_CLOSED = Util.getIPokerAgentListenerMethod("tableClosed");
    public static final Method TABLE_INFOS = Util.getIPokerAgentListenerMethod("tableInfos");
    public static final Method WAITING_FOR_PLAYERS = Util.getIPokerAgentListenerMethod("waitingForPlayers");
    
    /**
     * Happens when a betting turn ends.
     * 
     * @param p_potIndices
     *            contains all indices of pots that have been modified.
     */
    public void betTurnEnded(ArrayList<Integer> p_potIndices, TypePokerRound p_gameStat);
    
    /**
     * Happens when cards on the board changes.
     * 
     * @param p_boardCardIndices
     *            contains all indices of the board cards that have been
     *            modified.
     */
    public void boardChanged(ArrayList<Integer> p_boardCardIndices);
    
    /**
     * Happens when a game ends.
     */
    public void gameEnded();
    
    /**
     * Happens when a game starts.
     * 
     * @param p_oldDealer
     *            is the previous dealer.
     * @param p_oldSmallBlind
     *            is the previous player with the small blind.
     * @param p_oldBigBlind
     *            is the previous player with the big blind.
     */
    public void gameStarted(PokerPlayerInfo p_oldDealer, PokerPlayerInfo p_oldSmallBlind, PokerPlayerInfo p_oldBigBlind);
    
    /**
     * Happens when the cards of a player changes.
     * 
     * @param p_player
     *            is the player for whom his cards have been changed.
     */
    public void playerCardChanged(PokerPlayerInfo p_player);
    
    /**
     * Happens when a player joined the table.
     * 
     * @param p_player
     *            is the player that has joined the table.
     */
    public void playerJoined(PokerPlayerInfo p_player);
    
    /**
     * Happens when a player left the table.
     * 
     * @param p_player
     *            is the player that has left the table.
     */
    public void playerLeft(PokerPlayerInfo p_player);
    
    /**
     * Happens when the money amount of a player changes.
     * 
     * @param p_player
     *            is the player for whom his money has been changed.
     * @param p_oldMoneyAmount
     *            is the previous money amount he had.
     */
    public void playerMoneyChanged(PokerPlayerInfo p_player, int p_oldMoneyAmount);
    
    /**
     * Happens when the turn of a player begins.
     * 
     * @param p_oldCurrentPlayer
     *            is the previous player that had played.
     */
    public void playerTurnBegan(PokerPlayerInfo p_oldCurrentPlayer);
    
    /**
     * Happens when the turn of a player ends.
     * 
     * @param p_player
     *            is the player for whom his turn ended.
     * @param p_action
     *            is the action taken by the player.
     * @param p_actionAmount
     *            is the amount related to the action taken.
     */
    public void playerTurnEnded(PokerPlayerInfo p_player, TypePlayerAction p_action, int p_actionAmount);
    
    /**
     * Happens when a player wins and receives his share.
     * 
     * @param p_player
     *            is the player that won.
     * @param p_potAmountWon
     *            is the share that the player won.
     * @param p_potIndex
     *            is the index of the pot that player won.
     */
    public void potWon(PokerPlayerInfo p_player, int p_potAmountWon, int p_potIndex);
    
    /**
     * Happens when the table closes.
     */
    public void tableClosed();
    
    /**
     * Happens when all infos of a table need to be updated.
     */
    public void tableInfos();
    
    /**
     * Happens when waiting for other players to join.
     */
    public void waitingForPlayers();
}

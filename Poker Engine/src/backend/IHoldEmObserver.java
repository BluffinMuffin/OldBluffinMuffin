package backend;

import java.lang.reflect.Method;

import basePoker.Card;
import basePoker.PokerPlayerAction;
import basePoker.Pot;

import player.ServerPokerPlayerInfo;
import tools.PokerUtil;

/**
 * @author Hocus
 *         This interface define an observer of an Hold'Em table
 */
public interface IHoldEmObserver
{
    public static final Method BIG_BLIND_POSTED = PokerUtil.getIHoldEmObserverMethod("bigBlindPosted", HoldemTableServer.class, ServerPokerPlayerInfo.class, int.class);
    public static final Method END_BETTING_TURN = PokerUtil.getIHoldEmObserverMethod("endBettingTurn", HoldemTableServer.class);
    public static final Method FLOP_DEALT = PokerUtil.getIHoldEmObserverMethod("flopDealt", HoldemTableServer.class, Card[].class);
    public static final Method GAME_ENDED = PokerUtil.getIHoldEmObserverMethod("gameEnded", HoldemTableServer.class);
    public static final Method GAME_STARTED = PokerUtil.getIHoldEmObserverMethod("gameStarted", HoldemTableServer.class);
    public static final Method HOLE_CARD_DEAL = PokerUtil.getIHoldEmObserverMethod("holeCardDeal", HoldemTableServer.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_END_TURN = PokerUtil.getIHoldEmObserverMethod("playerEndTurn", HoldemTableServer.class, ServerPokerPlayerInfo.class, PokerPlayerAction.class);
    public static final Method PLAYER_JOINED_TABLE = PokerUtil.getIHoldEmObserverMethod("playerJoinedTable", HoldemTableServer.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_LEFT_TABLE = PokerUtil.getIHoldEmObserverMethod("playerLeftTable", HoldemTableServer.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_MONEY_CHANGED = PokerUtil.getIHoldEmObserverMethod("playerMoneyChanged", HoldemTableServer.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_SHOW_CARD = PokerUtil.getIHoldEmObserverMethod("playerShowCard", HoldemTableServer.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_TURN_STARTED = PokerUtil.getIHoldEmObserverMethod("playerTurnStarted", HoldemTableServer.class, ServerPokerPlayerInfo.class);
    public static final Method POT_WON = PokerUtil.getIHoldEmObserverMethod("potWon", HoldemTableServer.class, ServerPokerPlayerInfo.class, Pot.class, int.class);
    public static final Method RIVER_DEAL = PokerUtil.getIHoldEmObserverMethod("riverDeal", HoldemTableServer.class, Card[].class);
    public static final Method SMALL_BLIND_POSTED = PokerUtil.getIHoldEmObserverMethod("smallBlindPosted", HoldemTableServer.class, ServerPokerPlayerInfo.class, int.class);
    public static final Method TABLE_ENDED = PokerUtil.getIHoldEmObserverMethod("tableEnded", HoldemTableServer.class);
    public static final Method TABLE_INFOS = PokerUtil.getIHoldEmObserverMethod("tableInfos", HoldemTableServer.class);
    public static final Method TABLE_STARTED = PokerUtil.getIHoldEmObserverMethod("tableStarted", HoldemTableServer.class);
    public static final Method TURN_DEAL = PokerUtil.getIHoldEmObserverMethod("turnDeal", HoldemTableServer.class, Card[].class);
    public static final Method WAITING_FOR_PLAYERS = PokerUtil.getIHoldEmObserverMethod("waitingForPlayers", HoldemTableServer.class);
    
    /**
     * The big blind is posted.
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that posted the big bling
     * @param p_bigBlind
     *            Amount put in the pot
     */
    public void bigBlindPosted(HoldemTableServer p_table, ServerPokerPlayerInfo p_player, int p_bigBlind);
    
    /**
     * A betting turn ended
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void endBettingTurn(HoldemTableServer p_table);
    
    /**
     * The flop cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void flopDealt(HoldemTableServer p_table, Card[] p_board);
    
    /**
     * A game is finished
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void gameEnded(HoldemTableServer p_table);
    
    /**
     * A new game is starting
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void gameStarted(HoldemTableServer p_table);
    
    /**
     * Hole cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that received cards
     */
    public void holeCardDeal(HoldemTableServer p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A player took an action
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that took the action
     * @param p_action
     *            The action taken
     */
    public void playerEndTurn(HoldemTableServer p_table, ServerPokerPlayerInfo p_player, PokerPlayerAction p_action);
    
    /**
     * A player joined the table
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The new player
     */
    public void playerJoinedTable(HoldemTableServer p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A player left the table
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that left
     */
    public void playerLeftTable(HoldemTableServer p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * The number of chips of a player changed
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that changed his chips
     */
    public void playerMoneyChanged(HoldemTableServer p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A player showed his card
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that showed his card
     */
    public void playerShowCard(HoldemTableServer p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A is taking an action
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that is taking an action
     */
    public void playerTurnStarted(HoldemTableServer p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A player won a share of a pot
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that won the share
     * @param p_pot
     *            The pot containing the share
     * @param p_share
     *            The number of chips won
     */
    public void potWon(HoldemTableServer p_table, ServerPokerPlayerInfo p_player, Pot p_pot, int p_share);
    
    /**
     * The river cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void riverDeal(HoldemTableServer p_table, Card[] p_board);
    
    /**
     * The small blind were posted
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that posted the small blind
     * @param p_smallBlind
     *            The number of chips put in the pot
     */
    public void smallBlindPosted(HoldemTableServer p_table, ServerPokerPlayerInfo p_player, int p_smallBlind);
    
    /**
     * The table is closing
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void tableEnded(HoldemTableServer p_table);
    
    /**
     * The observer were successfully added
     * 
     * @param p_table
     */
    public void tableInfos(HoldemTableServer p_table);
    
    /**
     * The table is starting.
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void tableStarted(HoldemTableServer p_table);
    
    /**
     * The turn cards were dealt.
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void turnDeal(HoldemTableServer p_table, Card[] p_board);
    
    /**
     * The table is waiting for new player before starting a new game
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void waitingForPlayers(HoldemTableServer p_table);
}

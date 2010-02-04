package serverLogic;

import java.lang.reflect.Method;

import pokerLogic.Card;
import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;

/**
 * @author Hocus
 *         This interface define an observer of an Hold'Em table
 */
public interface IServerPokerObserver
{
    public static final Method BIG_BLIND_POSTED = ServerPokerObserverUtil.getIHoldEmObserverMethod("bigBlindPosted", ServerTableCommunicator.class, ServerPokerPlayerInfo.class, int.class);
    public static final Method END_BETTING_TURN = ServerPokerObserverUtil.getIHoldEmObserverMethod("endBettingTurn", ServerTableCommunicator.class);
    public static final Method FLOP_DEALT = ServerPokerObserverUtil.getIHoldEmObserverMethod("flopDealt", ServerTableCommunicator.class, Card[].class);
    public static final Method GAME_ENDED = ServerPokerObserverUtil.getIHoldEmObserverMethod("gameEnded", ServerTableCommunicator.class);
    public static final Method GAME_STARTED = ServerPokerObserverUtil.getIHoldEmObserverMethod("gameStarted", ServerTableCommunicator.class);
    public static final Method HOLE_CARD_DEAL = ServerPokerObserverUtil.getIHoldEmObserverMethod("holeCardDeal", ServerTableCommunicator.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_END_TURN = ServerPokerObserverUtil.getIHoldEmObserverMethod("playerEndTurn", ServerTableCommunicator.class, ServerPokerPlayerInfo.class, PokerPlayerAction.class);
    public static final Method PLAYER_JOINED_TABLE = ServerPokerObserverUtil.getIHoldEmObserverMethod("playerJoinedTable", ServerTableCommunicator.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_LEFT_TABLE = ServerPokerObserverUtil.getIHoldEmObserverMethod("playerLeftTable", ServerTableCommunicator.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_MONEY_CHANGED = ServerPokerObserverUtil.getIHoldEmObserverMethod("playerMoneyChanged", ServerTableCommunicator.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_SHOW_CARD = ServerPokerObserverUtil.getIHoldEmObserverMethod("playerShowCard", ServerTableCommunicator.class, ServerPokerPlayerInfo.class);
    public static final Method PLAYER_TURN_STARTED = ServerPokerObserverUtil.getIHoldEmObserverMethod("playerTurnStarted", ServerTableCommunicator.class, ServerPokerPlayerInfo.class);
    public static final Method POT_WON = ServerPokerObserverUtil.getIHoldEmObserverMethod("potWon", ServerTableCommunicator.class, ServerPokerPlayerInfo.class, Pot.class, int.class);
    public static final Method RIVER_DEAL = ServerPokerObserverUtil.getIHoldEmObserverMethod("riverDeal", ServerTableCommunicator.class, Card[].class);
    public static final Method SMALL_BLIND_POSTED = ServerPokerObserverUtil.getIHoldEmObserverMethod("smallBlindPosted", ServerTableCommunicator.class, ServerPokerPlayerInfo.class, int.class);
    public static final Method TABLE_ENDED = ServerPokerObserverUtil.getIHoldEmObserverMethod("tableEnded", ServerTableCommunicator.class);
    public static final Method TABLE_INFOS = ServerPokerObserverUtil.getIHoldEmObserverMethod("tableInfos", ServerTableCommunicator.class);
    public static final Method TABLE_STARTED = ServerPokerObserverUtil.getIHoldEmObserverMethod("tableStarted", ServerTableCommunicator.class);
    public static final Method TURN_DEAL = ServerPokerObserverUtil.getIHoldEmObserverMethod("turnDeal", ServerTableCommunicator.class, Card[].class);
    public static final Method WAITING_FOR_PLAYERS = ServerPokerObserverUtil.getIHoldEmObserverMethod("waitingForPlayers", ServerTableCommunicator.class);
    
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
    public void bigBlindPosted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, int p_bigBlind);
    
    /**
     * A betting turn ended
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void endBettingTurn(ServerTableCommunicator p_table);
    
    /**
     * The flop cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void flopDealt(ServerTableCommunicator p_table, Card[] p_board);
    
    /**
     * A game is finished
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void gameEnded(ServerTableCommunicator p_table);
    
    /**
     * A new game is starting
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void gameStarted(ServerTableCommunicator p_table);
    
    /**
     * Hole cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that received cards
     */
    public void holeCardDeal(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player);
    
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
    public void playerEndTurn(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, PokerPlayerAction p_action);
    
    /**
     * A player joined the table
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The new player
     */
    public void playerJoinedTable(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A player left the table
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that left
     */
    public void playerLeftTable(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * The number of chips of a player changed
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that changed his chips
     */
    public void playerMoneyChanged(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A player showed his card
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that showed his card
     */
    public void playerShowCard(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player);
    
    /**
     * A is taking an action
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that is taking an action
     */
    public void playerTurnStarted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player);
    
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
    public void potWon(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, Pot p_pot, int p_share);
    
    /**
     * The river cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void riverDeal(ServerTableCommunicator p_table, Card[] p_board);
    
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
    public void smallBlindPosted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, int p_smallBlind);
    
    /**
     * The table is closing
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void tableEnded(ServerTableCommunicator p_table);
    
    /**
     * The observer were successfully added
     * 
     * @param p_table
     */
    public void tableInfos(ServerTableCommunicator p_table);
    
    /**
     * The table is starting.
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void tableStarted(ServerTableCommunicator p_table);
    
    /**
     * The turn cards were dealt.
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void turnDeal(ServerTableCommunicator p_table, Card[] p_board);
    
    /**
     * The table is waiting for new player before starting a new game
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void waitingForPlayers(ServerTableCommunicator p_table);
}

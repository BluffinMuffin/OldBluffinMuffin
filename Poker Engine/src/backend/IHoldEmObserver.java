package backend;

import java.lang.reflect.Method;

import player.IPlayer;
import tools.PokerUtil;
import utility.Card;

/**
 * @author Hocus
 *         This interface define an observer of an Hold'Em table
 */
public interface IHoldEmObserver
{
    public static final Method BIG_BLIND_POSTED = PokerUtil.getIHoldEmObserverMethod("bigBlindPosted", HoldEmTable.class, IPlayer.class, int.class);
    public static final Method END_BETTING_TURN = PokerUtil.getIHoldEmObserverMethod("endBettingTurn", HoldEmTable.class);
    public static final Method FLOP_DEALT = PokerUtil.getIHoldEmObserverMethod("flopDealt", HoldEmTable.class, Card[].class);
    public static final Method GAME_ENDED = PokerUtil.getIHoldEmObserverMethod("gameEnded", HoldEmTable.class);
    public static final Method GAME_STARTED = PokerUtil.getIHoldEmObserverMethod("gameStarted", HoldEmTable.class);
    public static final Method HOLE_CARD_DEAL = PokerUtil.getIHoldEmObserverMethod("holeCardDeal", HoldEmTable.class, IPlayer.class);
    public static final Method PLAYER_END_TURN = PokerUtil.getIHoldEmObserverMethod("playerEndTurn", HoldEmTable.class, IPlayer.class, Action.class);
    public static final Method PLAYER_JOINED_TABLE = PokerUtil.getIHoldEmObserverMethod("playerJoinedTable", HoldEmTable.class, IPlayer.class);
    public static final Method PLAYER_LEFT_TABLE = PokerUtil.getIHoldEmObserverMethod("playerLeftTable", HoldEmTable.class, IPlayer.class);
    public static final Method PLAYER_MONEY_CHANGED = PokerUtil.getIHoldEmObserverMethod("playerMoneyChanged", HoldEmTable.class, IPlayer.class);
    public static final Method PLAYER_SHOW_CARD = PokerUtil.getIHoldEmObserverMethod("playerShowCard", HoldEmTable.class, IPlayer.class);
    public static final Method PLAYER_TURN_STARTED = PokerUtil.getIHoldEmObserverMethod("playerTurnStarted", HoldEmTable.class, IPlayer.class);
    public static final Method POT_WON = PokerUtil.getIHoldEmObserverMethod("potWon", HoldEmTable.class, IPlayer.class, Pot.class, int.class);
    public static final Method RIVER_DEAL = PokerUtil.getIHoldEmObserverMethod("riverDeal", HoldEmTable.class, Card[].class);
    public static final Method SMALL_BLIND_POSTED = PokerUtil.getIHoldEmObserverMethod("smallBlindPosted", HoldEmTable.class, IPlayer.class, int.class);
    public static final Method TABLE_ENDED = PokerUtil.getIHoldEmObserverMethod("tableEnded", HoldEmTable.class);
    public static final Method TABLE_INFOS = PokerUtil.getIHoldEmObserverMethod("tableInfos", HoldEmTable.class);
    public static final Method TABLE_STARTED = PokerUtil.getIHoldEmObserverMethod("tableStarted", HoldEmTable.class);
    public static final Method TURN_DEAL = PokerUtil.getIHoldEmObserverMethod("turnDeal", HoldEmTable.class, Card[].class);
    public static final Method WAITING_FOR_PLAYERS = PokerUtil.getIHoldEmObserverMethod("waitingForPlayers", HoldEmTable.class);
    
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
    public void bigBlindPosted(HoldEmTable p_table, IPlayer p_player, int p_bigBlind);
    
    /**
     * A betting turn ended
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void endBettingTurn(HoldEmTable p_table);
    
    /**
     * The flop cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void flopDealt(HoldEmTable p_table, Card[] p_board);
    
    /**
     * A game is finished
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void gameEnded(HoldEmTable p_table);
    
    /**
     * A new game is starting
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void gameStarted(HoldEmTable p_table);
    
    /**
     * Hole cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that received cards
     */
    public void holeCardDeal(HoldEmTable p_table, IPlayer p_player);
    
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
    public void playerEndTurn(HoldEmTable p_table, IPlayer p_player, Action p_action);
    
    /**
     * A player joined the table
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The new player
     */
    public void playerJoinedTable(HoldEmTable p_table, IPlayer p_player);
    
    /**
     * A player left the table
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that left
     */
    public void playerLeftTable(HoldEmTable p_table, IPlayer p_player);
    
    /**
     * The number of chips of a player changed
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that changed his chips
     */
    public void playerMoneyChanged(HoldEmTable p_table, IPlayer p_player);
    
    /**
     * A player showed his card
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that showed his card
     */
    public void playerShowCard(HoldEmTable p_table, IPlayer p_player);
    
    /**
     * A is taking an action
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_player
     *            The player that is taking an action
     */
    public void playerTurnStarted(HoldEmTable p_table, IPlayer p_player);
    
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
    public void potWon(HoldEmTable p_table, IPlayer p_player, Pot p_pot, int p_share);
    
    /**
     * The river cards were dealt
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void riverDeal(HoldEmTable p_table, Card[] p_board);
    
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
    public void smallBlindPosted(HoldEmTable p_table, IPlayer p_player, int p_smallBlind);
    
    /**
     * The table is closing
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void tableEnded(HoldEmTable p_table);
    
    /**
     * The observer were successfully added
     * 
     * @param p_table
     */
    public void tableInfos(HoldEmTable p_table);
    
    /**
     * The table is starting.
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void tableStarted(HoldEmTable p_table);
    
    /**
     * The turn cards were dealt.
     * 
     * @param p_table
     *            The table where the event occurred
     * @param p_board
     *            The cards on the board
     */
    public void turnDeal(HoldEmTable p_table, Card[] p_board);
    
    /**
     * The table is waiting for new player before starting a new game
     * 
     * @param p_table
     *            The table where the event occurred
     */
    public void waitingForPlayers(HoldEmTable p_table);
}

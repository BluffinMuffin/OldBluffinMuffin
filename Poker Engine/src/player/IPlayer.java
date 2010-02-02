package player;

import java.util.ArrayList;

import backend.Action;
import backend.HoldEmTable;
import basePoker.Card;

/**
 * @author HOCUS
 *         Interface used by Hold'Em Table to communicate with the player
 */
public interface IPlayer
{
    
    /**
     * Add some chips to the player
     * 
     * @param p_amount
     *            The number of chips to add
     */
    public void addMoney(int p_amount);
    
    /**
     * Check if the player can play a new game
     * 
     * @return
     *         True if the player can play a new game
     */
    public boolean canStartGame();
    
    /**
     * The game is finished
     */
    public void endGame();
    
    /**
     * A betting turn has ended
     */
    public void endTurn();
    
    /**
     * Return the bet of the current betting turn
     * 
     * @return
     *         The number of chips of the bet
     */
    public int getBet();
    
    /**
     * Return the cards of the player
     * 
     * @return
     *         The cards of the player
     */
    public ArrayList<Card> getHand();
    
    /**
     * Return the number of chips of the player
     * 
     * @return
     *         The number of chips of the player
     */
    public int getMoney();
    
    /**
     * Return the name of the player
     * 
     * @return
     *         The name of the player
     */
    public String getName();
    
    /**
     * Return the Hold'Em table where this player is sitting
     * 
     * @return
     *         The table of the player
     */
    public HoldEmTable getTable();
    
    /**
     * Return the seat number of this player
     * 
     * @return
     *         The seat number
     */
    public int getTablePosition();
    
    /**
     * Calculate the hand strength of the player
     * 
     * @param board
     *            The cards on the board
     * @return
     *         The hand value
     */
    public long handValue(Card[] board);
    
    /**
     * Check if the player is all in
     * 
     * @return
     *         True if the player is all in
     */
    public boolean isAllIn();
    
    /**
     * Check if the player is the big blind
     * 
     * @return
     *         True if the player is the big blind
     */
    public boolean isBigBlind();
    
    /**
     * Check if the player is the dealer
     * 
     * @return
     *         True if the player is the dealer
     */
    public boolean isDealer();
    
    /**
     * Check if the player is folded
     * 
     * @return
     *         True if the player is folded
     */
    public boolean isFolded();
    
    /**
     * Check if the player is playing
     * 
     * @return
     *         True if the player is playing
     */
    public boolean isPlaying();
    
    /**
     * Check if the cards of the player are open
     * 
     * @return
     *         True if the player's cards are open
     */
    public boolean isShowingCard();
    
    /**
     * Check if the player is the small blind
     * 
     * @return
     *         True if the player is the small blind
     */
    public boolean isSmallBlind();
    
    /**
     * Ask to the player to place the big blind
     * 
     * @param p_bigBlind
     *            Amount of the big blind
     * @return
     *         True if the blind was posted
     */
    public boolean placeBigBlind(int p_bigBlind);
    
    /**
     * Ask to the player to place the small blind
     * 
     * @param p_smallBlind
     *            Amount of the small blind
     * @return
     *         True if the blind was posted
     */
    public boolean placeSmallBlind(int p_smallBlind);
    
    /**
     * Add a new card to the player hand
     * 
     * @param p_card
     *            The new card
     */
    public void receiveCard(Card p_card);
    
    /**
     * Set the big blind attribute
     * 
     * @param p_flag
     *            The new big blind attribute
     */
    public void setIsBigBlind(boolean p_flag);
    
    /**
     * Set the Dealer attribute
     * 
     * @param p_flag
     *            The new Dealer value
     */
    public void setIsDealer(boolean p_flag);
    
    /**
     * Set the Folded attribute
     * 
     * @param p_flag
     *            The new Folded value
     */
    public void setIsFolded(boolean p_flag);
    
    /**
     * Set the small blind attribute
     * 
     * @param p_flag
     *            The new small blind value
     */
    public void setIsSmallBlind(boolean p_flag);
    
    /**
     * Change the table of the player
     * 
     * @param p_table
     *            The new table
     */
    public void setTable(HoldEmTable p_table);
    
    /**
     * Change the seat number of the player
     * 
     * @param p_position
     *            The new seat number
     */
    public void setTablePosition(int p_position);
    
    /**
     * Ask to the player to show his cards
     */
    public void showCards();
    
    /**
     * Check if the player will sit out at the next hand
     */
    public boolean sitOutNextHand();
    
    /**
     * Start a new game
     */
    public void startGame();
    
    /**
     * Request an action to the player
     * 
     * @param p_betOnTable
     *            The current bet amount
     * @param p_minimumBet
     *            The minimum raise amount
     * @param p_maximumBet
     *            The maximum raise amount
     * @return
     *         The action taken by the player
     */
    public Action takeAction(int p_betOnTable, int p_minimumBet, int p_maximumBet);
    
    /**
     * The player won a pot
     * 
     * @param p_potValue
     *            The number of chips won
     */
    public void winPot(int p_potValue);
}

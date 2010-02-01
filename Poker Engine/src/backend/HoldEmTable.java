package backend;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

import player.IPlayer;
import utility.Card;
import utility.ClosingListener;
import utility.Constants;
import utility.Deck;
import utility.TypeGameState;
import utility.TypeHoldEmGame;
import utility.TypePlayerAction;

/**
 * @author Hocus
 *         This class represent a Hold'Em Table.
 *         It simulate all the poker game of the table.
 */
public class HoldEmTable implements Runnable
{
    
    /*
     * ######################################################
     * Class Variables
     * ######################################################
     */

    public static final int NB_HOLE_CARDS = 2;
    
    // Listeners
    private final List<ClosingListener<HoldEmTable>> m_closingListeners = Collections.synchronizedList(new ArrayList<ClosingListener<HoldEmTable>>());
    private final List<IHoldEmObserver> m_observers;
    
    // Table Definition variables
    private final String m_name;
    private final TypeHoldEmGame m_gameType;
    private final int m_nbSeat;
    private final int m_bigBlind;
    private final int m_smallBlind;
    
    // Players variables
    private final List<IPlayer> m_players;
    private int m_nbPlayer;
    private int m_nbPlaying;
    private int m_nbFolded;
    private int m_nbAllIn;
    private int m_nbBetting;
    
    // Thread variables
    private boolean m_waitForFirstPlayer;
    private boolean m_isRunning = false;
    private boolean m_stopTable;
    private final Object m_mutex = new Object();
    
    // Game variables
    private TypeGameState m_gameState;
    private Deck m_deck;
    private Card[] m_board;
    private int m_dealer;
    private int m_smallBlindPlayer;
    private int m_bigBlindPlayer;
    private int m_lastRaiser;
    private int m_currentBet;
    private int m_playerTurn;
    private int m_bettingPlayer;
    private boolean m_firstTurn;
    private Stack<Pot> m_pots;
    private int m_totalPot;
    
    /*
     * ######################################################
     * Constructors
     * ######################################################
     */

    /**
     * Create a new table.
     * The start method must be call to start the table thread.
     * 
     * @param p_name
     *            Name of the table
     * @param p_gameType
     *            Hold'Em Poker type
     * @param p_bigBlind
     *            Amount of the Big Blind
     * @param p_playerCapacity
     *            Number of seat available
     */
    public HoldEmTable(String p_name, TypeHoldEmGame p_gameType, int p_bigBlind, int p_playerCapacity)
    {
        m_stopTable = false;
        m_name = p_name;
        m_gameType = p_gameType;
        m_nbSeat = p_playerCapacity;
        m_players = Collections.synchronizedList(new ArrayList<IPlayer>(m_nbSeat));
        for (int i = 0; i < m_nbSeat; i++)
        {
            m_players.add(null);
        }
        
        m_isRunning = true;
        m_observers = new ArrayList<IHoldEmObserver>();
        m_waitForFirstPlayer = true;
        
        m_bigBlind = p_bigBlind;
        m_smallBlind = p_bigBlind / 2;
        m_bettingPlayer = -1;
        m_pots = new Stack<Pot>();
        m_board = new Card[5];
    }
    
    /*
     * ######################################################
     * Getters and Setters
     * ######################################################
     */

    /**
     * Add a new ClosingListener. The listener is notify when the table close
     * 
     * @param p_listener
     *            The new listener
     */
    public void addClosingListener(ClosingListener<HoldEmTable> p_listener)
    {
        m_closingListeners.add(p_listener);
    }
    
    /**
     * Add a new observer to the table
     * 
     * @param p_observer
     *            The new observer
     */
    public void attach(IHoldEmObserver p_observer)
    {
        synchronized (m_mutex)
        {
            p_observer.tableInfos(this);
            m_observers.add(p_observer);
        }
    }
    
    /**
     * Check if we can continue betting
     * 
     * @return
     *         True if we can continue betting
     */
    private boolean continueBetting()
    {
        boolean canContinue = false;
        if ((m_firstTurn && (m_nbBetting > 1)) || (m_players.get(m_playerTurn).getBet() < m_currentBet))
        {
            canContinue = true;
        }
        
        return canContinue;
    }
    
    /**
     * Deal the flop cards
     */
    private void dealFlop()
    {
        m_gameState = TypeGameState.FLOP;
        m_board[0] = m_deck.pop();
        m_board[1] = m_deck.pop();
        m_board[2] = m_deck.pop();
        
        notifyObserver(IHoldEmObserver.FLOP_DEALT, this, m_board);
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Deal the holes card.
     */
    private void dealHole()
    {
        final int firstPlayer = nextPlayingPlayer(m_dealer);
        m_playerTurn = firstPlayer;
        
        do
        {
            m_players.get(m_playerTurn).receiveCard(m_deck.pop());
            m_players.get(m_playerTurn).receiveCard(m_deck.pop());
            
            notifyObserver(IHoldEmObserver.HOLE_CARD_DEAL, this, m_players.get(m_playerTurn));
            m_playerTurn = nextPlayingPlayer(m_playerTurn);
        }
        while (m_playerTurn != firstPlayer);
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Deal the River cards
     */
    private void dealRiver()
    {
        m_gameState = TypeGameState.RIVER;
        m_board[4] = m_deck.pop();
        
        notifyObserver(IHoldEmObserver.RIVER_DEAL, this, m_board);
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Deal the turn cards
     */
    private void dealTurn()
    {
        m_gameState = TypeGameState.TURN;
        m_board[3] = m_deck.pop();
        
        notifyObserver(IHoldEmObserver.TURN_DEAL, this, m_board);
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Remove an observer to the table
     * 
     * @param p_observer
     *            The observer to remove
     */
    public void detach(IHoldEmObserver p_observer)
    {
        synchronized (m_mutex)
        {
            m_observers.remove(p_observer);
        }
    }
    
    /**
     * End a game
     */
    private void endGame()
    {
        m_gameState = TypeGameState.GAME_ENDED;
        
        final int start = m_dealer;
        int i = start;
        do
        {
            m_players.get(i).endGame();
            i = nextPlayer(i);
        }
        while (i != start);
        
        notifyObserver(IHoldEmObserver.GAME_ENDED, this);
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * return the value of the big blind
     * 
     * @return
     *         the big blind amount
     */
    public int getBigBlind()
    {
        return m_bigBlind;
    }
    
    /**
     * Return the cards of the boards.
     * This is an array of five card, a null value indicate that no card was dealt.
     * 
     * @return
     *         The cards of the boards
     */
    public Card[] getBoard()
    {
        return m_board;
    }
    
    /**
     * Return the state of the game (Preflop, flop, turn ...)
     * 
     * @return
     *         The state of the game.
     */
    public TypeGameState getGameState()
    {
        return m_gameState;
    }
    
    /**
     * Return the type of the game
     * 
     * @return
     *         The type of the game
     */
    public TypeHoldEmGame getGameType()
    {
        return m_gameType;
    }
    
    /**
     * Find the maximum raise amount of a player who have to take an action.
     * 
     * @param p_player
     *            The player that is taking an action
     * @return
     *         The maximum raise amount
     */
    private int getMaximumRaise(IPlayer p_player)
    {
        int maximumRaise = Integer.MAX_VALUE;
        if (m_gameType == TypeHoldEmGame.FIXED_LIMIT)
        {
            if ((m_gameState == TypeGameState.PREFLOP) || (m_gameState == TypeGameState.FLOP))
            {
                maximumRaise = m_bigBlind + m_currentBet;
            }
            else
            {
                maximumRaise = m_bigBlind * 2 + m_currentBet;
            }
        }
        else if (m_gameType == TypeHoldEmGame.POT_LIMIT)
        {
            maximumRaise = m_totalPot + 2 * (m_currentBet - p_player.getBet()) + p_player.getBet();
        }
        
        if (maximumRaise > p_player.getMoney() + p_player.getBet())
        {
            maximumRaise = p_player.getMoney() + p_player.getBet();
        }
        
        return maximumRaise;
    }
    
    /**
     * Find the minimum raise amount of a player who have to take an action.
     * 
     * @param p_player
     *            The player that is taking an action
     * @return
     *         The minimum raise amount
     */
    private int getMinimumRaise(IPlayer p_player)
    {
        int minimumRaise = m_currentBet + m_bigBlind;
        if (m_gameType == TypeHoldEmGame.FIXED_LIMIT)
        {
            if (!((m_gameState == TypeGameState.PREFLOP) || (m_gameState == TypeGameState.FLOP)))
            {
                minimumRaise += m_bigBlind;
            }
        }
        
        if ((minimumRaise > p_player.getMoney() + p_player.getBet()) && (p_player.getMoney() + p_player.getBet() > m_currentBet))
        {
            minimumRaise = p_player.getMoney() + p_player.getBet();
        }
        
        return minimumRaise;
    }
    
    /**
     * Return the name of the table
     * 
     * @return
     *         The name of the table
     */
    public String getName()
    {
        return m_name;
    }
    
    /*
     * ######################################################
     * Public Methods
     * ######################################################
     */

    /**
     * Return the number of player sitting on the table
     * 
     * @return
     *         The number of player
     */
    public int getNbPlayers()
    {
        return m_nbPlayer;
    }
    
    /**
     * Return the number of player playing.
     * A player is considered playing if he (will) received hole cards in the current game.
     * 
     * @return
     *         The number of player playing
     */
    public int getNbPlaying()
    {
        return m_nbPlaying;
    }
    
    /**
     * Return the number of player that can still have a share of the pot.
     * i.e. A player that is playing and that is not folded.
     * 
     * @return
     *         The remaining number of player playing
     */
    public int getNbRemainingPlayer()
    {
        return m_nbPlaying - m_nbFolded;
    }
    
    /**
     * Return the total number of seat
     * 
     * @return
     *         the total number of seat
     */
    public int getNbSeats()
    {
        return m_nbSeat;
    }
    
    /**
     * Get the next available seat
     * 
     * @return
     *         The next available seat
     */
    public synchronized int getNextSeat()
    {
        if (m_nbPlayer > m_nbSeat)
        {
            return -1;
        }
        
        ++m_nbPlayer;
        int i = 0;
        while (m_players.get(i) != null)
        {
            ++i;
        }
        
        return i;
    }
    
    /**
     * Return the seat number of the Big Blind in the current game
     * 
     * @return
     *         The Big Blind's seat
     */
    public int getNoSeatBigBlind()
    {
        return m_bigBlindPlayer;
    }
    
    /**
     * Return the seat number of the dealer in the current game
     * 
     * @return
     *         The dealer's seat
     */
    public int getNoSeatDealer()
    {
        return m_dealer;
    }
    
    /**
     * Return the seat number of the Small Blind in the current game
     * 
     * @return
     *         The Small Blind's seat
     */
    public int getNoSeatSmallBlind()
    {
        return m_smallBlindPlayer;
    }
    
    /**
     * Return the list of player of this table.
     * 
     * @return
     *         The list of player
     */
    public List<IPlayer> getPlayers()
    {
        return m_players;
    }
    
    /**
     * Return the pots on the table
     * 
     * @return
     *         The pots
     */
    public Stack<Pot> getPots()
    {
        return m_pots;
    }
    
    /**
     * return the value of the small blind
     * 
     * @return
     *         the small blind amount
     */
    public int getSmallBlind()
    {
        return m_smallBlind;
    }
    
    /*
     * ######################################################
     * Private Methods
     * ######################################################
     */

    /**
     * Return the total amount of chips on the table.
     * This include the pots and the bets of the players.
     * It does NOT include the chips each player own.
     * 
     * @return
     *         The amount of chips on the table
     */
    public int getTotalPot()
    {
        return m_totalPot;
    }
    
    /**
     * Initialize a game.
     * Called at the start of each game.
     * 
     * @return
     */
    private boolean initializeGame()
    {
        // Reset the games variables
        m_deck = new Deck();
        m_lastRaiser = -1;
        m_board = new Card[5];
        m_currentBet = 0;
        m_pots = new Stack<Pot>();
        m_pots.push(new Pot(0));
        m_totalPot = 0;
        m_gameState = TypeGameState.PREFLOP;
        m_nbFolded = 0;
        m_nbAllIn = 0;
        m_nbPlaying = 0;
        
        // check if we can start a new game.
        boolean canStartGame = false;
        while (!canStartGame)
        {
            int nbPlayingPlayer = 0;
            
            if (m_nbPlayer > 0)
            {
                for (int i = 0; i < m_nbSeat; i++)
                {
                    if (m_players.get(i) != null)
                    {
                        if (m_players.get(i).sitOutNextHand())
                        {
                            final IPlayer deletedPlayer = m_players.get(i);
                            m_players.set(i, null);
                            m_nbPlayer--;
                            
                            notifyObserver(IHoldEmObserver.PLAYER_LEFT_TABLE, this, deletedPlayer);
                        }
                        else if (m_players.get(i).getMoney() > 0)
                        {
                            if (m_players.get(i).canStartGame())
                            {
                                nbPlayingPlayer++;
                            }
                        }
                    }
                }
            }
            
            if (m_stopTable || ((m_nbPlayer <= 0) && !m_waitForFirstPlayer))
            {
                return false;
            }
            
            if (nbPlayingPlayer >= 2)
            {
                canStartGame = true;
            }
            else
            {
                notifyObserver(IHoldEmObserver.WAITING_FOR_PLAYERS, this);
                
                try
                {
                    Thread.sleep(Constants.SERVER_WAIT_TIME_FOR_NEW_PLAYERS);
                }
                catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
        // notify each player that can play that a new game is starting
        final int start = nextPlayer(m_dealer);
        int i = start;
        do
        {
            if (m_players.get(i).canStartGame())
            {
                m_players.get(i).startGame();
                ++m_nbPlaying;
            }
            i = nextPlayer(i);
        }
        while (i != start);
        
        // Reset the player position variables
        m_nbBetting = m_nbPlaying;
        m_dealer = nextPlayingPlayer(m_dealer);
        m_players.get(m_dealer).setIsDealer(true);
        if (m_nbPlaying == 2)
        {
            m_bigBlindPlayer = nextPlayingPlayer(m_dealer);
            m_players.get(m_bigBlindPlayer).setIsBigBlind(true);
            m_smallBlindPlayer = nextPlayingPlayer(m_bigBlindPlayer);
            m_players.get(m_smallBlindPlayer).setIsSmallBlind(true);
        }
        else
        {
            m_smallBlindPlayer = nextPlayingPlayer(m_dealer);
            m_players.get(m_smallBlindPlayer).setIsSmallBlind(true);
            m_bigBlindPlayer = nextPlayingPlayer(m_smallBlindPlayer);
            m_players.get(m_bigBlindPlayer).setIsBigBlind(true);
        }
        
        // notify the observer that a new game is starting
        notifyObserver(IHoldEmObserver.GAME_STARTED, this);
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
        
        return true;
    }
    
    /**
     * Initialize the table.
     * Called when the table is started
     */
    private void initializeTable()
    {
        m_dealer = m_nbSeat - 1;
        notifyObserver(IHoldEmObserver.TABLE_STARTED, this);
    }
    
    /**
     * Return True if the table is running
     * 
     * @return
     *         True if the table is running
     */
    public boolean isRunning()
    {
        return m_isRunning;
    }
    
    /**
     * Add a player to the game.
     * 
     * @param p_player
     *            The player to add.
     * @param p_noSeat
     *            The seat number of the player
     */
    public void join(IPlayer p_player, int p_noSeat)
    {
        m_waitForFirstPlayer = false;
        m_players.set(p_noSeat, p_player);
        p_player.setTable(this);
        p_player.setTablePosition(p_noSeat);
        notifyObserver(IHoldEmObserver.PLAYER_JOINED_TABLE, this, p_player);
    }
    
    /**
     * Create a string that represent the table.
     * This method is used to send the table over the network
     * 
     * @param p_player
     *            The player that will received the table informations
     * @return
     *         A string representing the table
     */
    public String marshal(IPlayer p_player)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(m_totalPot);
        sb.append(Constants.DELIMITER);
        
        sb.append(m_nbSeat);
        sb.append(Constants.DELIMITER);
        
        for (final Pot pot : m_pots)
        {
            sb.append(pot.getAmount() + Constants.DELIMITER);
        }
        
        for (int i = m_pots.size(); i < m_nbSeat; i++)
        {
            sb.append(0 + Constants.DELIMITER);
        }
        
        for (int i = 0; i != m_board.length; ++i)
        {
            if (m_board[i] == null)
            {
                sb.append(Card.NO_CARD + Constants.DELIMITER);
            }
            else
            {
                sb.append(m_board[i].getId() + Constants.DELIMITER);
            }
        }
        
        sb.append(m_nbSeat + Constants.DELIMITER);
        
        for (int i = 0; i != m_players.size(); ++i)
        {
            sb.append(i + Constants.DELIMITER); // noSeat
            final IPlayer player = m_players.get(i);
            sb.append((player == null) + Constants.DELIMITER); // isEmpty
            
            if (player == null)
            {
                continue;
            }
            
            sb.append(player.getName() + Constants.DELIMITER); // playerName
            sb.append(player.getMoney() + Constants.DELIMITER); // playerMoney
            
            final boolean showCard = (player.getTablePosition() == p_player.getTablePosition()) || player.isShowingCard();
            
            // Player cards
            int j = 0;
            for (; j != player.getHand().size(); ++j)
            {
                if (showCard && !player.isFolded())
                {
                    sb.append(player.getHand().get(j).getId() + Constants.DELIMITER);
                }
                else if (!player.isFolded())
                {
                    sb.append(Card.HIDDEN_CARD + Constants.DELIMITER);
                }
                else
                {
                    sb.append(Card.NO_CARD + Constants.DELIMITER);
                }
            }
            
            for (; j < HoldEmTable.NB_HOLE_CARDS; ++j)
            {
                sb.append(Card.NO_CARD + Constants.DELIMITER);
            }
            
            sb.append(player.isDealer() + Constants.DELIMITER); // isDealer
            sb.append(player.isSmallBlind() + Constants.DELIMITER); // isSmallBlind
            sb.append(player.isBigBlind() + Constants.DELIMITER); // isBigBlind
            sb.append((i == m_bettingPlayer) + Constants.DELIMITER); // isCurrentPlayer
            sb.append(0 + Constants.DELIMITER); // timeRemaining
            sb.append(player.getBet() + Constants.DELIMITER); // betAmount
        }
        
        return sb.toString();
    }
    
    /**
     * Return the seat number of the player to the left of the specified seat.
     * 
     * @param p_player
     *            A seat number
     * @return
     *         The seat of the player to the left of the seat.
     */
    private int nextPlayer(int p_player)
    {
        int player = p_player;
        do
        {
            player = (player + 1) % m_nbSeat;
        }
        while (m_players.get(player) == null);
        return player;
    }
    
    /**
     * Return the seat number of the next playing player.
     * 
     * @param p_player
     *            A seat number
     * @return
     *         The nest playing player
     */
    private int nextPlayingPlayer(int p_player)
    {
        int player = p_player;
        do
        {
            player = nextPlayer(player);
        }
        while (!m_players.get(player).isPlaying());
        return player;
        
    }
    
    /**
     * Notify the observers that an events occured
     * 
     * @param p_method
     *            The method to call on the observers
     * @param p_objects
     *            the parameters of the method
     */
    private void notifyObserver(Method p_method, Object... p_objects)
    {
        synchronized (m_mutex)
        {
            for (final IHoldEmObserver observer : m_observers)
            {
                try
                {
                    p_method.invoke(observer, p_objects);
                }
                catch (final IllegalArgumentException e)
                {
                    e.printStackTrace();
                }
                catch (final IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (final InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * The last player the pot
     */
    private void onePlayerStanding()
    {
        IPlayer winner;
        
        final Iterator<IPlayer> it = m_pots.peek().getParticipant().iterator();
        winner = it.next();
        
        while (!m_pots.empty())
        {
            final Pot pot = m_pots.pop();
            
            winner.winPot(pot.getAmount());
            notifyObserver(IHoldEmObserver.POT_WON, this, winner, pot, pot.getAmount());
            
            try
            {
                Thread.sleep(Constants.SERVER_WAIT_TIME_SHOWDOWN);
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Place the blinds on the table
     */
    private void placeBlinds()
    {
        // Place the small blind
        IPlayer player = m_players.get(m_smallBlindPlayer);
        player.placeSmallBlind(m_smallBlind);
        m_currentBet = player.getBet();
        m_totalPot += m_currentBet;
        if (player.isAllIn())
        {
            m_nbBetting--;
            m_nbAllIn++;
        }
        
        notifyObserver(IHoldEmObserver.SMALL_BLIND_POSTED, this, m_players.get(m_smallBlindPlayer), m_currentBet);
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
        
        // place the Big Blind
        player = m_players.get(m_bigBlindPlayer);
        player.placeBigBlind(m_bigBlind);
        final int bet = player.getBet();
        m_currentBet = Math.max(m_currentBet, bet);
        m_totalPot += bet;
        if (player.isAllIn())
        {
            m_nbBetting--;
            m_nbAllIn++;
        }
        
        notifyObserver(IHoldEmObserver.BIG_BLIND_POSTED, this, m_players.get(m_bigBlindPlayer), bet);
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Remove a ClosingListener from the list of listener
     * 
     * @param p_listener
     *            The listener to remove
     */
    public void removeClosingListener(ClosingListener<HoldEmTable> p_listener)
    {
        m_closingListeners.remove(p_listener);
    }
    
    /**
     * Start the table's loop
     */
    public void run()
    {
        initializeTable();
        while (initializeGame())
        {
            placeBlinds();
            dealHole();
            if (startBettingRound())
            {
                dealFlop();
                if (startBettingRound())
                {
                    dealTurn();
                    if (startBettingRound())
                    {
                        dealRiver();
                        if (startBettingRound())
                        {
                            showdown();
                        }
                    }
                }
            }
            endGame();
        }
        
        m_isRunning = false;
        
        notifyObserver(IHoldEmObserver.TABLE_ENDED, this);
        
        synchronized (m_closingListeners)
        {
            for (final ClosingListener<HoldEmTable> listener : m_closingListeners)
            {
                listener.closing(this);
            }
        }
    }
    
    /**
     * Show the hands of all remaining player
     */
    private void showAllHands()
    {
        for (int i = 0; i < m_nbSeat; i++)
        {
            final IPlayer player = m_players.get(i);
            if (!(player == null) && player.isPlaying() && !player.isFolded() && !player.isShowingCard())
            {
                player.showCards();
                notifyObserver(IHoldEmObserver.PLAYER_SHOW_CARD, this, player);
            }
        }
    }
    
    /**
     * Check the winners and distribute the pots
     */
    private void showdown()
    {
        m_gameState = TypeGameState.SHOWDOWN;
        
        showAllHands();
        
        final Vector<IPlayer> bestPlayers = new Vector<IPlayer>();
        long bestHand = 0;
        
        while (!m_pots.empty())
        {
            final Pot pot = m_pots.pop();
            if (pot.getParticipant().size() > 0)
            {
                final Iterator<IPlayer> it = pot.getParticipant().iterator();
                while (it.hasNext())
                {
                    final IPlayer player = it.next();
                    final long handValue = player.handValue(m_board);
                    
                    if (handValue > bestHand)
                    {
                        bestPlayers.removeAllElements();
                        bestPlayers.add(player);
                        bestHand = handValue;
                    }
                    else if (handValue == bestHand)
                    {
                        bestPlayers.add(player);
                    }
                }
                
                final int amountPerWinner = pot.getAmount() / bestPlayers.size();
                for (int i = 0; i < bestPlayers.size(); i++)
                {
                    bestPlayers.elementAt(i).winPot(amountPerWinner);
                    notifyObserver(IHoldEmObserver.POT_WON, this, bestPlayers.elementAt(i), pot, amountPerWinner);
                    
                    try
                    {
                        Thread.sleep(Constants.SERVER_WAIT_TIME_SHOWDOWN);
                    }
                    catch (final InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    /**
     * Start the table.
     * This method creates a new thread.
     */
    public void start()
    {
        new Thread(this).start();
    }
    
    /**
     * Start a betting round.
     * 
     * @return
     *         True if the game can continue.
     */
    private boolean startBettingRound()
    {
        m_firstTurn = true;
        int lastPlayer = m_dealer;
        if ((m_nbPlaying == 2) && (m_gameState == TypeGameState.PREFLOP))
        {
            lastPlayer = nextPlayingPlayer(m_dealer);
        }
        else if ((m_nbPlaying > 2) && (m_gameState == TypeGameState.PREFLOP))
        {
            lastPlayer = getNoSeatBigBlind();
        }
        m_playerTurn = nextPlayingPlayer(lastPlayer);
        m_bettingPlayer = m_playerTurn;
        final TreeSet<Integer> bets = new TreeSet<Integer>();
        
        // Check if the blinds are all-in
        if (m_gameState == TypeGameState.PREFLOP)
        {
            IPlayer blind = m_players.get(m_smallBlindPlayer);
            if (blind.isAllIn() && !bets.contains(blind.getBet()))
            {
                bets.add(blind.getBet());
            }
            blind = m_players.get(m_bigBlindPlayer);
            if (blind.isAllIn() && !bets.contains(blind.getBet()))
            {
                bets.add(blind.getBet());
            }
        }
        
        // Ask each player to take a decision until they can't bet.
        IPlayer player;
        Action action;
        while (continueBetting())
        {
            player = m_players.get(m_playerTurn);
            // make sure the player can take an action
            if (!(player.isFolded() || player.isAllIn()))
            {
                final int oldBet = player.getBet();
                notifyObserver(IHoldEmObserver.PLAYER_TURN_STARTED, this, player);
                
                // Ask an action to the player
                action = player.takeAction(m_currentBet, getMinimumRaise(player), getMaximumRaise(player));
                
                m_totalPot += (player.getBet() - oldBet);
                
                if (player.isAllIn())
                {
                    m_nbAllIn++;
                    m_nbBetting--;
                }
                else if (player.isFolded())
                {
                    m_nbFolded++;
                    m_nbBetting--;
                }
                
                if (action.getType() == TypePlayerAction.RAISE)
                {
                    m_currentBet = player.getBet();
                    m_lastRaiser = m_playerTurn;
                }
                
                if (player.isAllIn() && !bets.contains(player.getBet()))
                {
                    bets.add(player.getBet());
                }
                
                if ((m_lastRaiser < 0) && !player.isFolded())
                {
                    m_lastRaiser = m_playerTurn;
                }
                
                notifyObserver(IHoldEmObserver.PLAYER_END_TURN, this, player, action);
                
                try
                {
                    Thread.sleep(Constants.SERVER_WAIT_TIME);
                }
                catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
                
            }
            
            if (m_firstTurn && (m_playerTurn == lastPlayer))
            {
                m_firstTurn = false;
            }
            
            m_playerTurn = nextPlayingPlayer(m_playerTurn);
            m_bettingPlayer = m_playerTurn;
        }
        
        m_bettingPlayer = -1;
        
        // Modify the pots
        boolean addAPot = false;
        Pot lastPot = null;
        if (m_currentBet != 0)
        {
            if (bets.contains(m_currentBet))
            {
                addAPot = true;
                lastPot = new Pot(m_pots.peek().getId() + bets.size() + 1);
            }
            bets.add(m_currentBet);
            final int nbPots = bets.size();
            final Pot[] newPots = new Pot[nbPots];
            for (int i = 1; i < nbPots; i++)
            {
                newPots[i] = new Pot(m_pots.peek().getId() + i);
            }
            newPots[0] = m_pots.peek();
            newPots[0].removeAllParticipant();
            
            final int firstPlayer = nextPlayer(m_dealer);
            m_playerTurn = firstPlayer;
            int bet = 0;
            do
            {
                bet = m_players.get(m_playerTurn).getBet();
                
                if (bet > 0)
                {
                    int lastPotBet = 0;
                    final Iterator<Integer> it = bets.iterator();
                    boolean betPlaced = false;
                    int potIndex = 0;
                    
                    while (it.hasNext() && !betPlaced)
                    {
                        final int nextPotBet = it.next();
                        final Pot nextPot = newPots[potIndex];
                        if (bet >= nextPotBet)
                        {
                            nextPot.addAmount(nextPotBet - lastPotBet);
                            if (bet == nextPotBet)
                            {
                                
                                betPlaced = true;
                                if (addAPot && !m_players.get(m_playerTurn).isAllIn())
                                {
                                    lastPot.addParticipant(m_players.get(m_playerTurn));
                                }
                                else
                                {
                                    nextPot.addParticipant(m_players.get(m_playerTurn));
                                }
                            }
                        }
                        else
                        {
                            nextPot.addAmount(bet - lastPotBet);
                            betPlaced = true;
                        }
                        
                        ++potIndex;
                        lastPotBet = nextPotBet;
                    }
                }
                
                m_playerTurn = nextPlayer(m_playerTurn);
            }
            while (m_playerTurn != firstPlayer);
            
            for (int i = 1; i < nbPots; i++)
            {
                m_pots.push(newPots[i]);
            }
            if (addAPot && (lastPot.getParticipant().size() > 0))
            {
                m_pots.push(lastPot);
            }
        }
        
        m_currentBet = 0;
        
        // notify the players that the turn ended
        final int start = m_dealer;
        int i = start;
        do
        {
            m_players.get(i).endTurn();
            i = nextPlayer(i);
        }
        while (i != start);
        
        notifyObserver(IHoldEmObserver.END_BETTING_TURN, this);
        
        if (m_nbBetting < 2)
        {
            // if there is only one player left
            if ((m_nbAllIn <= 0) || ((m_nbBetting <= 0) && (m_nbAllIn == 1)))
            {
                onePlayerStanding();
                return false;
            }
            else
            {
                // If all the players are all-in but one or if they are all all-in
                showAllHands();
            }
        }
        
        // Make sure that no pot can be won only by one player
        if (!m_pots.isEmpty() && (m_pots.peek().getParticipant().size() == 1))
        {
            final Pot uselessPot = m_pots.pop();
            final IPlayer playerInPot = uselessPot.getParticipant().firstElement();
            
            playerInPot.addMoney(uselessPot.getAmount());
            m_pots.peek().addParticipant(playerInPot);
            
            notifyObserver(IHoldEmObserver.PLAYER_MONEY_CHANGED, this, playerInPot);
        }
        
        return true;
    }
    
    /**
     * Stop the table.
     * The table will finish the current game before closing.
     */
    public void stop()
    {
        m_stopTable = true;
    }
}

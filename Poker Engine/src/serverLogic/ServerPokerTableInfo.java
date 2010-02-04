package serverLogic;

import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

import pokerLogic.Card;
import pokerLogic.Deck;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.PokerTableInfo;
import pokerLogic.Pot;
import pokerLogic.TypePokerGame;
import pokerLogic.TypePokerRound;
import utility.Constants;

public class ServerPokerTableInfo extends PokerTableInfo
{
    // Players variables
    public int m_nbFolded;
    public int m_nbAllIn;
    public int m_nbBetting;
    
    // Game variables
    public Deck m_deck;
    public int m_noSeatLastRaiser;
    public int m_playerTurn;
    public int m_bettingPlayer;
    public boolean m_firstTurn;
    public Stack<Pot> m_pots = new Stack<Pot>();
    public TypePokerGame m_gameType;
    
    public ServerPokerTableInfo()
    {
        this(9);
    }
    
    public ServerPokerTableInfo(int nbSeats)
    {
        this("Anonymous", TypePokerGame.NO_LIMIT, 5, nbSeats);
    }
    
    public ServerPokerTableInfo(String pName, TypePokerGame pGameType, int pBigBlind, int nbSeats)
    {
        super(pName, pBigBlind, nbSeats);
        m_gameType = pGameType;
        m_bettingPlayer = -1;
        m_pots = new Stack<Pot>();
    }
    
    /**
     * End a game
     */
    public void endGame()
    {
        m_gameState = TypePokerRound.END;
        
        final int start = m_noSeatDealer;
        int i = start;
        do
        {
            getPlayer(i).endGame();
            i = nextPlayer(i);
        }
        while (i != start);
    }
    
    @Override
    public void initializeGame()
    {
        super.initializeGame();
        
        // Reset the games variables
        m_deck = new Deck();
        m_noSeatLastRaiser = -1;
        m_pots = new Stack<Pot>();
        m_pots.push(new Pot(0));
        m_nbFolded = 0;
        m_nbAllIn = 0;
    }
    
    /**
     * Find the minimum raise amount of a player who have to take an action.
     * 
     * @param p_player
     *            The player that is taking an action
     * @return
     *         The minimum raise amount
     */
    public int getMinimumRaise(ServerPokerPlayerInfo p_player)
    {
        int minimumRaise = m_currentBet + m_bigBlindAmount;
        if (m_gameType == TypePokerGame.FIXED_LIMIT)
        {
            if (!((m_gameState == TypePokerRound.PREFLOP) || (m_gameState == TypePokerRound.FLOP)))
            {
                minimumRaise += m_bigBlindAmount;
            }
        }
        
        if ((minimumRaise > p_player.getMoney() + p_player.getBet()) && (p_player.getMoney() + p_player.getBet() > m_currentBet))
        {
            minimumRaise = p_player.getMoney() + p_player.getBet();
        }
        
        return minimumRaise;
    }
    
    /**
     * Find the maximum raise amount of a player who have to take an action.
     * 
     * @param p_player
     *            The player that is taking an action
     * @return
     *         The maximum raise amount
     */
    public int getMaximumRaise(ServerPokerPlayerInfo p_player)
    {
        int maximumRaise = Integer.MAX_VALUE;
        if (m_gameType == TypePokerGame.FIXED_LIMIT)
        {
            if ((m_gameState == TypePokerRound.PREFLOP) || (m_gameState == TypePokerRound.FLOP))
            {
                maximumRaise = m_bigBlindAmount + m_currentBet;
            }
            else
            {
                maximumRaise = m_bigBlindAmount * 2 + m_currentBet;
            }
        }
        else if (m_gameType == TypePokerGame.POT_LIMIT)
        {
            maximumRaise = m_totalPotAmount + 2 * (m_currentBet - p_player.getBet()) + p_player.getBet();
        }
        
        if (maximumRaise > p_player.getMoney() + p_player.getBet())
        {
            maximumRaise = p_player.getMoney() + p_player.getBet();
        }
        
        return maximumRaise;
    }
    
    /**
     * Check if we can continue betting
     * 
     * @return
     *         True if we can continue betting
     */
    public boolean continueBetting()
    {
        boolean canContinue = false;
        if ((m_firstTurn && (m_nbBetting > 1)) || (getPlayer(m_playerTurn).getBet() < m_currentBet))
        {
            canContinue = true;
        }
        
        return canContinue;
    }
    
    /**
     * Deal the flop cards
     */
    public void dealFlop()
    {
        m_gameState = TypePokerRound.FLOP;
        for (int i = 0; i < 3; ++i)
        {
            m_boardCards.add(m_deck.pop());
        }
        
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
    public void dealRiver()
    {
        m_gameState = TypePokerRound.RIVER;
        m_boardCards.add(m_deck.pop());
        
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
    public void dealTurn()
    {
        m_gameState = TypePokerRound.TURN;
        m_boardCards.add(m_deck.pop());
        
        try
        {
            Thread.sleep(Constants.SERVER_WAIT_TIME);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void startGame()
    {
        super.startGame();
        m_nbBetting = m_nbPlayingPlayers;
    }
    
    public String marshal(ServerPokerPlayerInfo pPlayer)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(m_totalPotAmount);
        sb.append(Constants.DELIMITER);
        
        sb.append(getNbSeats());
        sb.append(Constants.DELIMITER);
        
        for (final Pot pot : m_pots)
        {
            sb.append(pot.getAmount() + Constants.DELIMITER);
        }
        
        for (int i = m_pots.size(); i < getNbSeats(); i++)
        {
            sb.append(0 + Constants.DELIMITER);
        }
        
        for (int i = 0; i < 5; ++i)
        {
            if (i >= m_boardCards.size())
            {
                sb.append(Card.NO_CARD + Constants.DELIMITER);
            }
            else
            {
                sb.append(m_boardCards.get(i).getId() + Constants.DELIMITER);
            }
        }
        
        sb.append(getNbPlayers() + Constants.DELIMITER);
        
        for (int i = 0; i != getNbPlayers(); ++i)
        {
            sb.append(i + Constants.DELIMITER); // noSeat
            final ServerPokerPlayerInfo player = (ServerPokerPlayerInfo) getPlayer(i);
            sb.append((player == null) + Constants.DELIMITER); // isEmpty
            
            if (player == null)
            {
                continue;
            }
            
            sb.append(player.getName() + Constants.DELIMITER); // playerName
            sb.append(player.getMoney() + Constants.DELIMITER); // playerMoney
            
            final boolean showCard = (player.getTablePosition() == pPlayer.getTablePosition()) || player.isShowingCard();
            
            // Player cards
            int j = 0;
            for (; j != player.getHand().length; ++j)
            {
                if (showCard && !player.isFolded())
                {
                    sb.append(player.getHand()[j].getId() + Constants.DELIMITER);
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
            
            for (; j < Constants.NB_HOLE_CARDS; ++j)
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
    
    public void placeSmallBlind()
    {
        // Place the small blind
        final PokerPlayerInfo player = getPlayer(m_noSeatSmallBlind);
        player.placeSmallBlind(m_smallBlindAmount);
        m_currentBet = player.getBet();
        m_totalPotAmount += m_currentBet;
        if (player.isAllIn())
        {
            m_nbBetting--;
            m_nbAllIn++;
        }
    }
    
    public void placeBigBlind()
    {
        
        // place the Big Blind
        final PokerPlayerInfo player = getPlayer(m_noSeatBigBlind);
        player.placeBigBlind(m_bigBlindAmount);
        final int bet = player.getBet();
        m_currentBet = Math.max(m_currentBet, bet);
        m_totalPotAmount += bet;
        if (player.isAllIn())
        {
            m_nbBetting--;
            m_nbAllIn++;
        }
    }
    
    public void endBettingRound(TreeSet<Integer> bets)
    {
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
            
            final int firstPlayer = nextPlayer(m_noSeatDealer);
            m_playerTurn = firstPlayer;
            int bet = 0;
            do
            {
                bet = getPlayer(m_playerTurn).getBet();
                
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
                                if (addAPot && !getPlayer(m_playerTurn).isAllIn())
                                {
                                    lastPot.addParticipant(getPlayer(m_playerTurn));
                                }
                                else
                                {
                                    nextPot.addParticipant(getPlayer(m_playerTurn));
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
        final int start = m_noSeatDealer;
        int i = start;
        do
        {
            getPlayer(i).endTurn();
            i = nextPlayer(i);
        }
        while (i != start);
    }
    
}

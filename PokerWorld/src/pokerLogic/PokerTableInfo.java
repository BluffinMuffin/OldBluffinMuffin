package pokerLogic;

import gameLogic.GameCard;

import java.util.ArrayList;
import java.util.List;


public class PokerTableInfo
{
    
    private int m_nbSeats;
    private PokerPlayerInfo[] m_players;
    private int m_nbPlayers;
    
    public int m_nbPlayingPlayers;
    
    public int m_totalPotAmount = 0;
    
    public int m_noSeatDealer = -1;
    public int m_noSeatSmallBlind = -1;
    public int m_noSeatBigBlind = -1;
    
    public int m_smallBlindAmount;
    public int m_bigBlindAmount;
    public ArrayList<GameCard> m_boardCards = new ArrayList<GameCard>();
    
    public TypePokerRound m_gameState = TypePokerRound.BEGINNING;
    public String m_name;
    
    public int m_currentBet;
    
    public PokerTableInfo()
    {
        this(9);
    }
    
    public PokerTableInfo(int nbSeats)
    {
        this("Anonymous", 5, nbSeats);
    }
    
    public PokerTableInfo(String pName, int pBigBlind, int nbSeats)
    {
        m_nbSeats = nbSeats;
        initializePlayers(nbSeats);
        
        m_name = pName;
        m_bigBlindAmount = pBigBlind;
        m_smallBlindAmount = pBigBlind / 2;
        m_boardCards = new ArrayList<GameCard>(5);
    }
    
    public List<PokerPlayerInfo> getPlayers()
    {
        final ArrayList<PokerPlayerInfo> players = new ArrayList<PokerPlayerInfo>();
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] != null)
            {
                players.add(m_players[i]);
            }
        }
        return players;
    }
    
    public List<Integer> getPlayerIds()
    {
        final ArrayList<Integer> ids = new ArrayList<Integer>();
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] != null)
            {
                ids.add(i);
            }
        }
        return ids;
    }
    
    public PokerPlayerInfo getPlayer(int i)
    {
        return m_players[i];
    }
    
    public int getNbSeats()
    {
        return m_nbSeats;
    }
    
    public int getNbPlayers()
    {
        return m_nbPlayers;
    }
    
    public void removePlayer(int i)
    {
        if (m_players[i] != null)
        {
            m_nbPlayers--;
        }
        m_players[i] = null;
    }
    
    public void removePlayer(PokerPlayerInfo p)
    {
        int count = 0;
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] == p)
            {
                m_players[i] = null;
                m_nbPlayers--;
                count++;
            }
        }
    }
    
    public int addPlayer(PokerPlayerInfo p)
    {
        for (int i = 0; i < m_nbSeats; ++i)
        {
            if (m_players[i] == null)
            {
                m_nbPlayers++;
                m_players[i] = p;
                return i;
            }
        }
        return -1;
    }
    
    public void addPlayer(int id, PokerPlayerInfo p)
    {
        if (m_players[id] == null)
        {
            m_nbPlayers++;
        }
        m_players[id] = p;
    }
    
    public void clearPlayers()
    {
        m_nbPlayers = 0;
        for (int i = 0; i < m_nbSeats; ++i)
        {
            m_players[i] = null;
        }
    }
    
    public void initializePlayers(int nbSeat)
    {
        m_nbPlayers = 0;
        m_nbSeats = nbSeat;
        m_players = new PokerPlayerInfo[nbSeat];
    }
    
    /**
     * Return the cards of the boards.
     * This is an array of five card, a null value indicate that no card was dealt.
     * 
     * @return
     *         The cards of the boards
     */
    public GameCard[] getBoard()
    {
        final GameCard[] cards = new GameCard[m_boardCards.size()];
        
        return m_boardCards.toArray(cards);
    }
    
    /**
     * Return the seat number of the player to the left of the specified seat.
     * 
     * @param p_player
     *            A seat number
     * @return
     *         The seat of the player to the left of the seat.
     */
    public int nextPlayer(int p_player)
    {
        int player = p_player;
        do
        {
            player = (player + 1) % getNbSeats();
        }
        while (getPlayer(player) == null);
        return player;
    }
    
    public void initializeGame()
    {
        m_boardCards = new ArrayList<GameCard>(5);
        m_currentBet = 0;
        m_totalPotAmount = 0;
        m_gameState = TypePokerRound.PREFLOP;
        m_nbPlayingPlayers = 0;
    }
    
    public void startGame()
    {
        // notify each player that can play that a new game is starting
        final int start = nextPlayer(m_noSeatDealer);
        int i = start;
        do
        {
            if (getPlayer(i).canStartGame())
            {
                getPlayer(i).startGame();
                ++m_nbPlayingPlayers;
            }
            i = nextPlayer(i);
        }
        while (i != start);
        
        // Reset the player position variables
        m_noSeatDealer = nextPlayingPlayer(m_noSeatDealer);
        getPlayer(m_noSeatDealer).setIsDealer(true);
        if (m_nbPlayingPlayers == 2)
        {
            m_noSeatBigBlind = nextPlayingPlayer(m_noSeatDealer);
            getPlayer(m_noSeatBigBlind).setIsBigBlind(true);
            m_noSeatSmallBlind = nextPlayingPlayer(m_noSeatBigBlind);
            getPlayer(m_noSeatSmallBlind).setIsSmallBlind(true);
        }
        else
        {
            m_noSeatSmallBlind = nextPlayingPlayer(m_noSeatDealer);
            getPlayer(m_noSeatSmallBlind).setIsSmallBlind(true);
            m_noSeatBigBlind = nextPlayingPlayer(m_noSeatSmallBlind);
            getPlayer(m_noSeatBigBlind).setIsBigBlind(true);
        }
        
    }
    
    /**
     * Return the seat number of the next playing player.
     * 
     * @param p_player
     *            A seat number
     * @return
     *         The nest playing player
     */
    public int nextPlayingPlayer(int p_player)
    {
        int player = p_player;
        do
        {
            player = nextPlayer(player);
        }
        while (!getPlayer(player).isPlaying());
        return player;
        
    }
}

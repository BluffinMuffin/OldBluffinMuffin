package poker.game;

import game.Card;
import game.CardSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

/**
 * @author Eric
 * 
 */
public class TableInfo
{
    // INFO
    private final String m_name; // Nom de la table
    private TypeBet m_betLimit; // Type de betLimit (NO_LIMIT, POT_LIMIT, etc.)
    
    // CARDS
    private final CardSet m_cards = new CardSet(5); // Cartes du board
    
    // SEATS
    private final int m_nbMaxSeats; // Nombe de siege total autour de la table
    private int m_nbUsedSeats; // Nombre de siege utilises autour de la table
    private final Stack<Integer> m_RemainingSeats = new Stack<Integer>(); // LIFO contenant les sieges non utilises
    
    // PLAYERS
    private final PlayerInfo[] m_players; // Joueurs autour de la table
    
    // POTS
    private final List<MoneyPot> m_pots = new ArrayList<MoneyPot>(); // La liste des POTS
    private int m_totalPotAmnt; // Le montant total en jeu (Tous les pots + l'argent en jeu)
    private final SortedSet<Integer> m_allInCaps = new TreeSet<Integer>(); // Les differents CAPS ALL_IN de la ROUND
    
    // BLINDS
    private final int m_smallBlindAmnt; // Montant a donner lorsqu'on est small blind
    private final int m_bigBlindAmnt; // Montant a donner lorsqu'on est big blind
    private final Map<PlayerInfo, Integer> m_blindNeeded = new HashMap<PlayerInfo, Integer>(); // Hashmap contenant les blinds necessaire pour chaque player
    private int m_totalBlindNeeded; // Montant total necessaire pour les blinds
    
    // STATES
    private int m_nbPlayed; // Nombre de joueur ayant joues de cette ROUND
    private int m_nbAllIn; // Nombre de joueurs ALL-IN
    private int m_higherBet; // Le bet actuel qu'il faut egaler
    private TypeRound m_round; // La round actuelle
    private int m_noSeatDealer; // La position actuelle du Dealer
    private int m_noSeatSmallBlind; // La position actuelle du SmallBlind
    private int m_noSeatBigBlind; // La position actuelle du BigBlind
    private int m_noSeatCurrPlayer; // La position du joueur actuel
    private int m_currPotId; // L'id du pot qu'on travail actuellement avec
    private int m_noSeatLastRaise; // L'id du dernier player qui a raiser ou du premier a jouer
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // CONSTRUCTOR // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Tuple Table d'un jeu de poker
     * 
     */
    public TableInfo()
    {
        this(10);
    }
    
    /**
     * Tuple Table d'un jeu de poker
     * 
     * @param nbSeats
     *            Nombre de sieges total
     */
    public TableInfo(int nbSeats)
    {
        this("Anonymous Table", 10, nbSeats, TypeBet.NO_LIMIT);
    }
    
    /**
     * Tuple Table d'un jeu de poker
     * 
     * @param pName
     *            Nom de la table
     * @param pBigBlind
     *            Amount du big blind
     * @param nbSeats
     *            Nombre total de sieges
     * @param limit
     *            Type de Bet Limit
     */
    public TableInfo(String pName, int pBigBlind, int nbSeats, TypeBet limit)
    {
        m_nbMaxSeats = nbSeats;
        m_nbUsedSeats = 0;
        m_players = new PlayerInfo[m_nbMaxSeats];
        m_name = pName;
        m_bigBlindAmnt = pBigBlind;
        m_smallBlindAmnt = pBigBlind / 2;
        m_noSeatDealer = -1;
        m_noSeatSmallBlind = -1;
        m_noSeatBigBlind = -1;
        m_betLimit = limit;
        for (int i = 1; i <= m_nbMaxSeats; ++i)
        {
            m_RemainingSeats.push(m_nbMaxSeats - i);
        }
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // INFO /// // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Nom de la table
     * 
     * @return
     */
    public String getName()
    {
        return m_name;
    }
    
    /**
     * Type de betLimit (NO_LIMIT, POT_LIMIT, etc.)
     * 
     * @return
     */
    public TypeBet getBetLimit()
    {
        return m_betLimit;
    }
    
    /**
     * Type de betLimit (NO_LIMIT, POT_LIMIT, etc.)
     * 
     * @param limit
     */
    public void setBetLimit(TypeBet limit)
    {
        m_betLimit = limit;
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // CARDS // // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Cartes du board
     * 
     * @return
     */
    public CardSet getCards()
    {
        return m_cards;
    }
    
    /**
     * Defini les cartes du board
     * 
     * @param c1
     * @param c2
     * @param c3
     * @param c4
     * @param c5
     */
    public void setCards(Card c1, Card c2, Card c3, Card c4, Card c5)
    {
        m_cards.clear();
        addCard(c1);
        addCard(c2);
        addCard(c3);
        addCard(c4);
        addCard(c5);
    }
    
    /**
     * Defini les cartes du board
     * 
     * @param set
     */
    public void setCards(CardSet set)
    {
        m_cards.clear();
        addCards(set);
    }
    
    /**
     * Ajoute un set de cartes au board
     * 
     * @param set
     */
    public void addCards(CardSet set)
    {
        while (!set.isEmpty())
        {
            addCard(set.pop());
        }
    }
    
    /**
     * Ajoute une seule carte au board
     * 
     * @param c
     */
    public void addCard(Card c)
    {
        m_cards.add(c);
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // SEATS // // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    /**
     * Nombe de siege total autour de la table
     * 
     * @return
     */
    public int getNbMaxSeats()
    {
        return m_nbMaxSeats;
    }
    
    /**
     * Nombre de siege utilises autour de la table
     * 
     * @return
     */
    public int getNbUsedSeats()
    {
        return m_nbUsedSeats;
    }
    
    /**
     * Nombre de siege utilises autour de la table
     * 
     * @param nb
     */
    public void setNbUsedSeats(int nb)
    {
        m_nbUsedSeats = nb;
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // PLAYERS /// // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * La liste des joueurs autout de la table
     * 
     * @return
     */
    public List<PlayerInfo> getPlayers()
    {
        final List<PlayerInfo> list = new ArrayList<PlayerInfo>();
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            if (m_players[i] != null)
            {
                list.add(m_players[i]);
            }
        }
        return list;
    }
    
    /**
     * La liste des joeurs qui sont en train de jouer dans l'etat PLAYING
     * 
     * @return
     */
    public List<PlayerInfo> getPlayingPlayers()
    {
        return getPlayingPlayersFromFirst();
    }
    
    public List<PlayerInfo> getPlayingPlayers(int seat)
    {
        final List<PlayerInfo> head = new ArrayList<PlayerInfo>();
        final List<PlayerInfo> tail = new ArrayList<PlayerInfo>();
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            if (m_players[i] != null && m_players[i].isPlaying())
            {
                if (i < seat)
                {
                    tail.add(m_players[i]);
                }
                else
                {
                    head.add(m_players[i]);
                }
            }
        }
        head.addAll(tail);
        return head;
    }
    
    public List<PlayerInfo> getPlayingPlayersFromCurrent()
    {
        return getPlayingPlayers(m_noSeatCurrPlayer);
    }
    
    public List<PlayerInfo> getPlayingPlayersFromLastRaise()
    {
        return getPlayingPlayers(m_noSeatLastRaise);
    }
    
    public List<PlayerInfo> getPlayingPlayersFromFirst()
    {
        if (m_round == TypeRound.PREFLOP)
        {
            return getPlayingPlayers(nextPlayingPlayer(m_noSeatBigBlind).getNoSeat());
        }
        return getPlayingPlayers(nextPlayingPlayer(m_noSeatDealer).getNoSeat());
    }
    
    /**
     * Le joueur a une position precise de la table
     * 
     * @param seat
     * @return
     */
    public PlayerInfo getPlayer(int seat)
    {
        return m_players[seat];
    }
    
    /**
     * Le joueur NextTo une position donnee
     * 
     * @param seat
     *            Position sur laquelle on se base
     * @return
     */
    public PlayerInfo nextPlayer(int seat)
    {
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            final int j = (seat + 1 + i) % m_nbMaxSeats;
            if (m_players[j] != null)
            {
                return m_players[j];
            }
        }
        return m_players[seat];
    }
    
    /**
     * Le joueur en train de jouer NextTo une position donnee
     * 
     * @param seat
     *            position sur laquelle on se base
     * @return
     */
    public PlayerInfo nextPlayingPlayer(int seat)
    {
        for (int i = 0; i < m_nbMaxSeats; ++i)
        {
            final int j = (seat + 1 + i) % m_nbMaxSeats;
            if (m_players[j] != null && m_players[j].isPlaying())
            {
                return m_players[j];
            }
        }
        return m_players[seat];
    }
    
    /**
     * Vrai si le joueur est assis a la table
     * 
     * @param p
     *            Objet player
     * @return
     */
    private boolean containsPlayer(PlayerInfo p)
    {
        return getPlayers().contains(p);
    }
    
    /**
     * Vrai si le joueur est assis a la table
     * 
     * @param name
     *            Nom du player
     * @return
     */
    public boolean containsPlayer(String name)
    {
        for (final PlayerInfo p : getPlayers())
        {
            if (p.getName().equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // POTS /// // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Liste des POTS
     * 
     * @return
     */
    public List<MoneyPot> getPots()
    {
        return m_pots;
    }
    
    /**
     * Argent total en jeux (Pots + argent de la round en cours)
     * 
     * @return
     */
    public int getTotalPotAmnt()
    {
        return m_totalPotAmnt;
    }
    
    /**
     * Argent total en jeux (Pots + argent de la round en cours)
     * 
     * @param amnt
     */
    public void setTotalPotAmnt(int amnt)
    {
        m_totalPotAmnt = amnt;
    }
    
    /**
     * Incrementer l'argent total en jeux (Pots + argent de la round en cours)
     * 
     * @param amnt
     */
    public void incTotalPotAmnt(int amnt)
    {
        m_totalPotAmnt += amnt;
    }
    
    /**
     * Ajoute un cap pour les AllIns de la round actuelle, afin de bien diviser les POTS
     * 
     * @param val
     */
    public void addAllInCap(Integer val)
    {
        m_allInCaps.add(val);
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // BLINDS / // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Montant a donner lorsqu'on est small blind
     * 
     * @return
     */
    public int getSmallBlindAmnt()
    {
        return m_smallBlindAmnt;
    }
    
    /**
     * Montant a donner lorsqu'on est big blind
     * 
     * @return
     */
    public int getBigBlindAmnt()
    {
        return m_bigBlindAmnt;
    }
    
    /**
     * Montant total necessaire pour les blinds
     * 
     * @return
     */
    public int getTotalBlindNeeded()
    {
        return m_totalBlindNeeded;
    }
    
    /**
     * Montant necessaire pour les blinds d'un player precis
     * 
     * @param p
     * @return
     */
    public int getBlindNeeded(PlayerInfo p)
    {
        if (m_blindNeeded.containsKey(p))
        {
            return m_blindNeeded.get(p);
        }
        return 0;
    }
    
    /**
     * Montant necessaire pour les blinds d'un player precis
     * 
     * @param p
     * @param amnt
     */
    public void setBlindNeeded(PlayerInfo p, int amnt)
    {
        m_blindNeeded.put(p, amnt);
    }
    
    /**
     * Montant total necessaire pour les blinds
     * 
     * @param totalBlindNeeded
     */
    public void setTotalBlindNeeded(int totalBlindNeeded)
    {
        m_totalBlindNeeded = totalBlindNeeded;
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // STATES / // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Position du Dealer
     * 
     * @return
     */
    public int getNoSeatDealer()
    {
        return m_noSeatDealer;
    }
    
    /**
     * Position du Small Blind
     * 
     * @return
     */
    public int getNoSeatSmallBlind()
    {
        return m_noSeatSmallBlind;
    }
    
    /**
     * Position du Big Blind
     * 
     * @return
     */
    public int getNoSeatBigBlind()
    {
        return m_noSeatBigBlind;
    }
    
    /**
     * Position du player en train de jouer
     * 
     * @return
     */
    public int getNoSeatCurrPlayer()
    {
        return m_noSeatCurrPlayer;
    }
    
    /**
     * Nombre de player ayant jouees au tour de table actuel
     * 
     * @return
     */
    public int getNbPlayed()
    {
        return m_nbPlayed;
    }
    
    /**
     * Nombre de joueur en train de jouer
     * 
     * @return
     */
    public int getNbPlaying()
    {
        return getPlayingPlayers().size();
    }
    
    /**
     * Nombre de joueur en train de jouer
     * 
     * @return
     */
    public int getNbPlayingAndAllIn()
    {
        return getNbPlaying() + getNbAllIn();
    }
    
    /**
     * Montant actuel a accoter pour continuer a jouer
     * 
     * @return
     */
    public int getHigherBet()
    {
        return m_higherBet;
    }
    
    /**
     * Nombre de joueur AllIn
     * 
     * @return
     */
    public int getNbAllIn()
    {
        return m_nbAllIn;
    }
    
    /**
     * Round actuelle
     * 
     * @return
     */
    public TypeRound getRound()
    {
        return m_round;
    }
    
    /**
     * Round actuelle
     * 
     * @return
     */
    public int getNoSeatLastRaise()
    {
        return m_noSeatLastRaise;
    }
    
    /**
     * Position actuelle du Dealer
     * 
     * @param seat
     */
    public void setNoSeatDealer(int seat)
    {
        m_noSeatDealer = seat;
    }
    
    /**
     * Position actuelle du SmallBlind
     * 
     * @param seat
     */
    public void setNoSeatSmallBlind(int seat)
    {
        m_noSeatSmallBlind = seat;
    }
    
    /**
     * Position actuelle du Big Blind
     * 
     * @param seat
     */
    public void setNoSeatBigBlind(int seat)
    {
        m_noSeatBigBlind = seat;
    }
    
    /**
     * Position du joueur en train de jouer
     * 
     * @param seat
     */
    public void setNoSeatCurrPlayer(int seat)
    {
        m_noSeatCurrPlayer = seat;
    }
    
    /**
     * Nombre de joueurs ayant jouer au tour de table en cours
     * 
     * @param nb
     */
    public void setNbPlayed(int nb)
    {
        m_nbPlayed = nb;
    }
    
    /**
     * Increment de 1 au nombre de joueurs ayant jouer au tour de table en cours
     * 
     */
    public void incNbPlayed()
    {
        m_nbPlayed++;
    }
    
    /**
     * Montant a accoter pour continuer a jouer
     * 
     * @param amnt
     */
    public void setHigherBet(int amnt)
    {
        m_higherBet = amnt;
    }
    
    /**
     * Nombre de joueurs AllIn
     * 
     * @param nb
     */
    public void setNbAllIn(int nb)
    {
        m_nbAllIn = nb;
    }
    
    /**
     * Increment de 1 au nombre de joueur AllIn
     * 
     */
    public void incNbAllIn()
    {
        m_nbAllIn++;
    }
    
    /**
     * Round Actuelle
     * 
     * @param round
     */
    public void setRound(TypeRound round)
    {
        m_round = round;
    }
    
    public void setNoSeatLastRaise(int seat)
    {
        m_noSeatLastRaise = seat;
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // METHODS /// // // // // // // //
    // // // // // // // // // // // // // // // // // //
    /**
     * Initialise la table pour une nouvelle partie
     * 
     */
    public void initTable()
    {
        m_cards.clear();
        setNbPlayed(0);
        placeButtons();
        initPots();
    }
    
    /**
     * Ajoute le joueur a la table a une position precise peu importe si il y avais quelqu'un
     * 
     * @param p
     * @param seat
     * @return
     */
    public boolean forceJoinTable(PlayerInfo p, int seat)
    {
        p.setNotPlaying();
        p.setNoSeat(seat);
        m_players[seat] = p;
        return true;
    }
    
    /**
     * Ajoute le joueur a la table au premier siege disponible
     * 
     * @param p
     * @return
     */
    public boolean joinTable(PlayerInfo p)
    {
        if (m_RemainingSeats.size() == 0)
        {
            System.err.println("Too bad: m_RemainingSeats.size() == 0");
            return false;
        }
        
        if (containsPlayer(p))
        {
            System.err.println("Too bad: containsPlayer(p)");
            return false;
        }
        
        final int seat = m_RemainingSeats.pop();
        p.setNotPlaying();
        p.setNoSeat(seat);
        m_players[seat] = p;
        return true;
    }
    
    /**
     * Enleve le joueur de la table
     * 
     * @param p
     * @return
     */
    public boolean leaveTable(PlayerInfo p)
    {
        
        if (!containsPlayer(p))
        {
            return false;
        }
        
        final int seat = p.getNoSeat();
        p.setNotPlaying();
        // TODO WTF IL FAIT CA!!!!!!!!!!!!!!
        // p.setNoSeat(-1);
        m_players[seat] = null;
        
        return true;
    }
    
    /**
     * Initialise les joueurs et determine lesquels joueront la prochaine partie
     * 
     */
    public void decidePlayingPlayers()
    {
        for (final PlayerInfo p : getPlayers())
        {
            if (p.canPlay())
            {
                p.setPlaying();
            }
            else
            {
                p.setNotPlaying();
            }
        }
    }
    
    /**
     * Initialise les Pots pour une nouvelle partie
     * 
     */
    public void initPots()
    {
        setTotalPotAmnt(0);
        m_pots.clear();
        m_allInCaps.clear();
        m_pots.add(new MoneyPot(0));
        m_currPotId = 0;
        setNbAllIn(0);
    }
    
    /**
     * Decide les positions des Dealer, smallBlind et BigBlind et note les blinds requis
     * 
     */
    public void placeButtons()
    {
        m_noSeatDealer = nextPlayingPlayer(m_noSeatDealer).getNoSeat();
        m_noSeatSmallBlind = getNbPlaying() == 2 ? m_noSeatDealer : nextPlayingPlayer(m_noSeatDealer).getNoSeat();
        m_noSeatBigBlind = nextPlayingPlayer(m_noSeatSmallBlind).getNoSeat();
        m_blindNeeded.clear();
        m_blindNeeded.put(getPlayer(m_noSeatSmallBlind), getSmallBlindAmnt());
        m_blindNeeded.put(getPlayer(m_noSeatBigBlind), getBigBlindAmnt());
        m_totalBlindNeeded = getSmallBlindAmnt() + getBigBlindAmnt();
    }
    
    /**
     * Ajoute un certain montant d'un joueur au pot et attache le joueur au pot si necessaire
     * 
     * @param p
     * @param pot
     * @param bet
     */
    private void AddBet(PlayerInfo p, MoneyPot pot, int bet)
    {
        p.setMoneyBetAmnt(p.getMoneyBetAmnt() - bet);
        pot.addAmount(bet);
        if (bet >= 0 && (p.isPlaying() || p.isAllIn()))
        {
            pot.attachPlayer(p);
        }
    }
    
    /**
     * Une fois la round terminé, prend tous l'argent en jeu et bati les POTS
     */
    public void managePotsRoundEnd()
    {
        int currentTaken = 0;
        while (m_allInCaps.size() > 0)
        {
            final MoneyPot pot = m_pots.get(m_currPotId);
            pot.detachAll();
            final int aicf = m_allInCaps.first();
            m_allInCaps.remove(aicf);
            final int cap = aicf - currentTaken;
            for (final PlayerInfo p : getPlayers())
            {
                AddBet(p, pot, Math.min(p.getMoneyBetAmnt(), cap));
            }
            currentTaken += cap;
            m_currPotId++;
            m_pots.add(new MoneyPot(m_currPotId));
        }
        
        final MoneyPot curPot = m_pots.get(m_currPotId);
        curPot.detachAll();
        for (final PlayerInfo p : getPlayers())
        {
            AddBet(p, curPot, p.getMoneyBetAmnt());
            
        }
        m_higherBet = 0;
    }
    
    /**
     * Ne garde que les gagnants sur chacuns des POTS
     * 
     */
    public void cleanPotsForWinning()
    {
        for (int i = 0; i <= m_currPotId; ++i)
        {
            final MoneyPot pot = m_pots.get(i);
            long bestHand = 0;
            final List<PlayerInfo> info = new ArrayList<PlayerInfo>(pot.getAttachedPlayers());
            for (final PlayerInfo p : info)
            {
                final long handValue = p.evaluateCards(m_cards);
                if (handValue > bestHand)
                {
                    pot.detachAll();
                    pot.attachPlayer(p);
                    bestHand = handValue;
                }
                else if (handValue == bestHand)
                {
                    pot.attachPlayer(p);
                }
            }
        }
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // UTILITY /// // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Le player a-t-il assez d'argent pour RAISER ?
     * 
     * @param p
     * @return
     */
    public boolean canRaise(PlayerInfo p)
    {
        return getHigherBet() < p.getMoneyAmnt();
    }
    
    /**
     * Le player peux-t-il continuer sans mettre d'avantage d'argent en jeu ?
     * 
     * @param p
     * @return
     */
    public boolean canCheck(PlayerInfo p)
    {
        return getHigherBet() <= p.getMoneyBetAmnt();
    }
    
    /**
     * Si le player veut RAISE, voici le minimum requis
     * 
     * @param p
     * @return
     */
    public int getMinRaiseAmnt(PlayerInfo p)
    {
        return Math.min(getCallAmnt(p) + getBigBlindAmnt(), getMaxRaiseAmnt(p));
    }
    
    /**
     * Si le player veut RAISE, voici le maximum possible
     * 
     * @param p
     * @return
     */
    public int getMaxRaiseAmnt(PlayerInfo p)
    {
        return p.getMoneySafeAmnt();
    }
    
    /**
     * Si le player veut continuer a jouer, voici le minimum requis
     * 
     * @param p
     * @return
     */
    public int getCallAmnt(PlayerInfo p)
    {
        return getHigherBet() - p.getMoneyBetAmnt();
    }
    
}

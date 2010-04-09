package poker.game;

import java.util.List;

import poker.game.dealer.AbstractDealer;
import poker.game.dealer.RandomDealer;
import poker.game.observer.IPokerGameListener;
import poker.game.observer.PokerGameObserver;

public class PokerGame implements IPokerGame
{
    // Global States of the Game
    public enum TypeState
    {
        INIT, PLAYERS_WAITING, BLIND_WAITING, PLAYING, SHOWDOWN, DECIDE_WINNERS, MONEY_DISTRIBUTION, END
    }
    
    // States of the Game in each Round
    public enum TypeRoundState
    {
        CARDS, BETTING, CUMUL
    }
    
    // INFO
    private final PokerGameObserver m_gameObserver; // L'observer qu'on notify
    private final TableInfo m_table; // La table
    
    // WAITING TIME
    private final int m_WaitingTimeAfterPlayerAction; // Attente apres chaque player action (ms)
    private final int m_WaitingTimeAfterBoardDealed; // Attente apres chaque board dealed (ms)
    private final int m_WaitingTimeAfterPotWon; // Attente apres chaque pot won ! (ms)
    
    // STATES
    private TypeState m_state; // L'etat global de la game
    private TypeRoundState m_roundState; // L'etat de la game pour chaque round
    
    private final AbstractDealer m_dealer; // Dealer
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // CONSTRUCTOR // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Automate representant un jeu de poker
     */
    public PokerGame()
    {
        this(new RandomDealer());
    }
    
    /**
     * Automate representant un jeu de poker
     * 
     * @param table
     *            La table associee
     * @param wtaPlayerAction
     *            Attente apres chaque player action (ms)
     * @param wtaBoardDealed
     *            Attente apres chaque board dealed (ms)
     * @param wtaPotWon
     *            Attente apres chaque pot won ! (ms)
     */
    public PokerGame(TableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
    {
        this(new RandomDealer(), table, wtaPlayerAction, wtaBoardDealed, wtaPotWon);
    }
    
    /**
     * Automate representant un jeu de poker
     * 
     * @param dealer
     *            Le dealer
     */
    public PokerGame(AbstractDealer dealer)
    {
        this(new RandomDealer(), new TableInfo(), 0, 0, 0);
    }
    
    /**
     * Automate representant un jeu de poker
     * 
     * @param dealer
     *            Le dealer
     * @param table
     *            La table associee
     * @param wtaPlayerAction
     *            Attente apres chaque player action (ms)
     * @param wtaBoardDealed
     *            Attente apres chaque board dealed (ms)
     * @param wtaPotWon
     *            Attente apres chaque pot won ! (ms)
     */
    public PokerGame(AbstractDealer dealer, TableInfo table, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
    {
        m_dealer = dealer;
        m_gameObserver = new PokerGameObserver();
        m_table = table;
        m_state = TypeState.INIT;
        m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
        m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
        m_WaitingTimeAfterPotWon = wtaPotWon;
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // INFO /// // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /*
     * (non-Javadoc)
     * 
     * @see poker.game.IPokerGame#getTable()
     */
    @Override
    public TableInfo getTable()
    {
        return m_table;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see poker.game.IPokerGame#attach(poker.game.observer.IPokerGameListener)
     */
    @Override
    public void attach(IPokerGameListener listener)
    {
        m_gameObserver.subscribe(listener);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see poker.game.IPokerGame#detach(poker.game.observer.IPokerGameListener)
     */
    @Override
    public void detach(IPokerGameListener listener)
    {
        m_gameObserver.unsubscribe(listener);
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // // STATES / // // // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * L'etat global actuel de la Game
     * 
     * @return
     */
    public TypeState getState()
    {
        return m_state;
    }
    
    /**
     * La round actuelle. Utile lorsque l'etat global est a PLAYING
     * 
     * @return
     */
    public TypeRound getRound()
    {
        return m_table.getRound();
    }
    
    /**
     * L'etat de la round. Utile lorsque l'etat global est a PLAYING
     * 
     * @return
     */
    public TypeRoundState getRoundState()
    {
        return m_roundState;
    }
    
    /**
     * Change l'etat de la Game pour le prochain.
     * Ne peux prendre en parametre que le prochain etat consecutif
     * 
     * @param state
     *            le prochain etat consecutif
     */
    private void nextState(TypeState state)
    {
        final TypeState oldState = m_state;
        
        if (m_state == TypeState.END)
        {
            return;
        }
        
        if (state.ordinal() - oldState.ordinal() != 1)
        {
            return;
        }
        
        m_state = state;
        
        switch (m_state)
        {
            case INIT:
                break;
            case PLAYERS_WAITING:
                TryToBegin();
                break;
            case BLIND_WAITING:
                m_table.setHigherBet(0);
                break;
            case PLAYING:
                m_table.setRound(TypeRound.PREFLOP);
                m_roundState = TypeRoundState.CARDS;
                startRound();
                break;
            case SHOWDOWN:
                showAllCards();
                break;
            case DECIDE_WINNERS:
                decideWinners();
                break;
            case MONEY_DISTRIBUTION:
                distributeMoney();
                break;
            case END:
                m_gameObserver.everythingEnded();
                break;
            
        }
    }
    
    /**
     * Change la round pour la prochaine.
     * Ne peux prendre en parametre que la prochaine round consecutive
     * 
     * @param round
     *            La prochaine round consecutive
     */
    private void nextRound(TypeRound round)
    {
        
        final TypeRound oldRound = m_table.getRound();
        
        if (m_state != TypeState.PLAYING)
        {
            return;
        }
        
        if (round.ordinal() - oldRound.ordinal() != 1)
        {
            return;
        }
        
        m_roundState = TypeRoundState.CARDS;
        m_table.setNoSeatCurrPlayer(m_table.getNoSeatDealer());
        m_table.setRound(round);
        startRound();
    }
    
    /**
     * Change l'etat de la round pour le prochain.
     * Ne peux prendre en parametre que le prochain etat consecutif
     * 
     * @param roundState
     *            le prochain etat consecutif
     */
    private void setRoundState(TypeRoundState roundState)
    {
        
        final TypeRoundState oldRoundState = m_roundState;
        
        if (m_state != TypeState.PLAYING)
        {
            return;
        }
        
        if (roundState.ordinal() - oldRoundState.ordinal() != 1)
        {
            return;
        }
        
        m_roundState = roundState;
        startRound();
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // PUBLIC COMMUNICATIONS / // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Demarre la nouvelle partie
     * 
     */
    public void start()
    {
        nextState(TypeState.PLAYERS_WAITING);
    }
    
    /**
     * La game est-elle en cours ?
     * 
     * @return Vrai si l'etat est different de END
     */
    public boolean isRunning()
    {
        return m_state != TypeState.END;
    }
    
    /**
     * Un player precis tente de joindre la game
     * 
     * @param p
     * @return Faux si la game n'est pas en cours
     */
    public boolean joinGame(PlayerInfo p)
    {
        if (m_state == TypeState.INIT || m_state == TypeState.END)
        {
            System.err.println("Bad timing:" + m_state);
            return false;
        }
        
        return m_table.joinTable(p);
    }
    
    /**
     * Assois un joueur a la table
     * 
     * @param p
     */
    public void sitInGame(PlayerInfo p)
    {
        m_gameObserver.playerJoined(p);
        if (m_state == TypeState.PLAYERS_WAITING)
        {
            TryToBegin();
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see poker.game.IPokerGame#leaveGame(poker.game.PlayerInfo)
     */
    @Override
    public boolean leaveGame(PlayerInfo p)
    {
        if (m_table.leaveTable(p))
        {
            m_gameObserver.playerLeaved(p);
            if (m_table.getNbUsedSeats() == 0)
            {
                m_state = TypeState.END;
            }
            return true;
        }
        return false;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see poker.game.IPokerGame#playMoney(poker.game.PlayerInfo, int)
     */
    @Override
    public boolean playMoney(PlayerInfo p, int amount)
    {
        final int amnt = Math.min(amount, p.getMoneySafeAmnt());
        System.out.println(p.getName() + " is playing " + amnt + " money on state: " + m_state);
        if (m_state == TypeState.BLIND_WAITING)
        {
            System.out.println("Total blinds needed is " + m_table.getTotalBlindNeeded());
            System.out.println(p.getName() + " is putting blind of " + amnt);
            final int needed = m_table.getBlindNeeded(p);
            if (amnt != needed)
            {
                if (p.canBet(amnt + 1))
                {
                    System.err.println(p.getName() + " needed to put " + needed);
                    return false;
                }
                else
                {
                    System.out.println("So ... All-In !");
                    p.setAllIn();
                    m_table.incNbAllIn();
                    m_table.addAllInCap(p.getMoneyBetAmnt() + amnt);
                }
            }
            if (!p.tryBet(amnt))
            {
                System.err.println(p.getName() + " just .. can't !! ");
                return false;
            }
            m_table.incTotalPotAmnt(amnt);
            m_gameObserver.playerMoneyChanged(p);
            m_table.setBlindNeeded(p, 0);
            if (amnt == m_table.getSmallBlindAmnt())
            {
                m_gameObserver.playerActionTaken(p, TypeAction.SMALL_BLIND_POSTED, amnt);
            }
            else
            {
                m_gameObserver.playerActionTaken(p, TypeAction.BIG_BLIND_POSTED, amnt);
            }
            m_table.setTotalBlindNeeded(m_table.getTotalBlindNeeded() - needed);
            if (m_table.getTotalBlindNeeded() == 0)
            {
                nextState(TypeState.PLAYING);
            }
            if (amnt > m_table.getHigherBet())
            {
                m_table.setHigherBet(amnt);
            }
            System.out.println("Total blinds still needed is " + m_table.getTotalBlindNeeded());
            return true;
        }
        
        else if (m_state == TypeState.PLAYING && m_roundState == TypeRoundState.BETTING)
        {
            System.out.println("Currently, we need " + m_table.getCallAmnt(p) + " minimum money from this player");
            if (p.getNoSeat() != m_table.getNoSeatCurrPlayer())
            {
                System.err.println("BUT SCREW YOU, IT'S NOT YOUR TURN !!!!!");
                return false;
            }
            
            if (amnt == -1)
            {
                System.out.println("So ... the little girl folds !");
                foldPlayer(p);
                continueBettingRound();
                return true;
            }
            int amntNeeded = m_table.getCallAmnt(p);
            if (amnt < amntNeeded)
            {
                if (p.canBet(amnt + 1))
                {
                    System.err.println("BUT SCREW YOU, IT'S NOT ENOUGH !!!!!");
                    return false;
                }
                amntNeeded = amnt;
            }
            if (!p.tryBet(amnt))
            {
                System.err.println("BUT SCREW YOU, YOU JUST CAN'T !!!!!");
                return false;
            }
            m_gameObserver.playerMoneyChanged(p);
            if (p.getMoneySafeAmnt() == 0)
            {
                System.out.println("So ... All-In ! getCurrentBetMoneyAmount: " + p.getMoneyBetAmnt());
                p.setAllIn();
                m_table.incNbAllIn();
                m_table.addAllInCap(p.getMoneyBetAmnt());
            }
            if (amnt == amntNeeded)
            {
                System.out.println("Will call with $" + amnt);
                m_table.incTotalPotAmnt(amnt);
                callPlayer(p, amnt);
                continueBettingRound();
                return true;
            }
            System.out.println("Will raise with $" + amnt);
            m_table.incTotalPotAmnt(amnt);
            raisePlayer(p, amnt);
            continueBettingRound();
            return true;
        }
        System.err.println("BUT WE DON'T CARE !!!!!");
        return false;
    }
    
    // // // // // // // // // // // // // // // // // //
    // // // // // // PRIVATE METHODS / // // // // // //
    // // // // // // // // // // // // // // // // // //
    
    /**
     * Demarre une round dependant de son etat
     */
    private void startRound()
    {
        switch (m_roundState)
        {
            case CARDS:
                startCardRound();
                break;
            case BETTING:
                startBettingRound();
                break;
            case CUMUL:
                startCumulRound();
                break;
        }
    }
    
    /**
     * C'est le moment de cumuler tout l'argent de la round
     */
    private void startCumulRound()
    {
        m_table.managePotsRoundEnd();
        m_gameObserver.gameBettingRoundEnded(m_table.getRound());
        if (m_table.getNbPlayingAndAllIn() <= 1)
        {
            nextState(TypeState.SHOWDOWN);
        }
        else
        {
            switch (m_table.getRound())
            {
                case PREFLOP:
                    nextRound(TypeRound.FLOP);
                    break;
                case FLOP:
                    nextRound(TypeRound.TURN);
                    break;
                case TURN:
                    nextRound(TypeRound.RIVER);
                    break;
                case RIVER:
                    nextState(TypeState.SHOWDOWN);
                    break;
            }
        }
    }
    
    /**
     * C'est le moment ou chaque joueur Bet dans la round
     */
    private void startBettingRound()
    {
        m_gameObserver.gameBettingRoundStarted();
        m_table.setNbPlayed(0);
        waitALittle(m_WaitingTimeAfterBoardDealed);
        
        if (m_table.getNbPlaying() <= 1)
        {
            endBettingRound();
        }
        else
        {
            continueBettingRound();
        }
    }
    
    /**
     * C'est le moment ou les cartes de la round sont decouvertes
     */
    private void startCardRound()
    {
        switch (m_table.getRound())
        {
            case PREFLOP:
                m_table.setNoSeatCurrPlayer(m_table.getNoSeatBigBlind());
                dealHole();
                break;
            case FLOP:
                dealFlop();
                break;
            case TURN:
                dealTurn();
                break;
            case RIVER:
                dealRiver();
                break;
        }
        setRoundState(TypeRoundState.BETTING);
    }
    
    /**
     * On tourne la River: ccc c C
     */
    private void dealRiver()
    {
        m_table.addCard(m_dealer.dealRiver());
    }
    
    /**
     * On tourne la Turn: ccc C c
     */
    private void dealTurn()
    {
        m_table.addCard(m_dealer.dealTurn());
    }
    
    /**
     * On tourne le flop: CCC c c
     */
    private void dealFlop()
    {
        m_table.addCards(m_dealer.dealFlop());
    }
    
    /**
     * On donne 2 cartes a chaque joueur
     */
    private void dealHole()
    {
        for (final PlayerInfo p : m_table.getPlayingPlayers())
        {
            p.setCards(m_dealer.dealHoles(p));
            m_gameObserver.playerHoleCardsChanged(p);
        }
    }
    
    /**
     * C'est le moment ou les joueurs restants montrent leurs cartes
     */
    private void showAllCards()
    {
        for (final PlayerInfo p : m_table.getPlayers())
        {
            if (p.isPlaying() || p.isAllIn())
            {
                p.setShowingCards(true);
                m_gameObserver.playerHoleCardsChanged(p);
            }
        }
        nextState(TypeState.DECIDE_WINNERS);
    }
    
    /**
     * Ce joueur fold !
     * 
     * @param p
     */
    private void foldPlayer(PlayerInfo p)
    {
        p.setNotPlaying();
        waitALittle(m_WaitingTimeAfterPlayerAction);
        m_gameObserver.playerActionTaken(p, TypeAction.FOLDED, -1);
    }
    
    /**
     * Ce joueur call !
     * 
     * @param p
     * @param played
     */
    private void callPlayer(PlayerInfo p, int played)
    {
        m_table.incNbPlayed();
        waitALittle(m_WaitingTimeAfterPlayerAction);
        m_gameObserver.playerActionTaken(p, TypeAction.CALLED, played);
    }
    
    /**
     * Ce joueur raise !
     * 
     * @param p
     * @param played
     */
    private void raisePlayer(PlayerInfo p, int played)
    {
        int count = m_table.getNbAllIn();
        System.out.println(">>> Count1 = " + count);
        if (!p.isAllIn())
        {
            count++;
        }
        System.out.println(">>> Count2 = " + count);
        m_table.setNbPlayed(count);
        m_table.setHigherBet(p.getMoneyBetAmnt());
        waitALittle(m_WaitingTimeAfterPlayerAction);
        m_gameObserver.playerActionTaken(p, TypeAction.RAISED, played);
    }
    
    /**
     * C'est le moment de continuer la BettingRound
     */
    private void continueBettingRound()
    {
        System.out.println("Le minimum pour jouer pour le prochain player: $" + m_table.getHigherBet());
        
        if (m_table.getNbPlayingAndAllIn() == 1 || m_table.getNbPlayed() >= m_table.getNbPlayingAndAllIn())
        {
            endBettingRound();
        }
        else
        {
            playNext();
        }
    }
    
    /**
     * Tout le monde a eu la chance de Better
     */
    private void endBettingRound()
    {
        setRoundState(TypeRoundState.CUMUL);
    }
    
    /**
     * Choisi le prochain joueur a Better
     */
    private void playNext()
    {
        
        final PlayerInfo player = m_table.nextPlayingPlayer(m_table.getNoSeatCurrPlayer());
        final PlayerInfo old = m_table.getPlayer(m_table.getNoSeatCurrPlayer());
        m_table.setNoSeatCurrPlayer(player.getNoSeat());
        m_gameObserver.playerActionNeeded(player, old);
    }
    
    /**
     * Distribue l'argent aux gagnants
     */
    private void distributeMoney()
    {
        for (final MoneyPot pot : m_table.getPots())
        {
            final List<PlayerInfo> players = pot.getAttachedPlayers();
            if (players.size() > 0)
            {
                final int wonAmount = pot.getAmount() / players.size();
                if (wonAmount > 0)
                {
                    for (final PlayerInfo p : players)
                    {
                        p.incMoneySafeAmnt(wonAmount);
                        m_gameObserver.playerMoneyChanged(p);
                        m_gameObserver.playerWonPot(p, pot, wonAmount);
                        
                        waitALittle(m_WaitingTimeAfterPotWon);
                    }
                }
            }
        }
        m_gameObserver.gameEnded();
        m_state = TypeState.PLAYERS_WAITING;
        TryToBegin();
    }
    
    /**
     * Decide des gagnants
     */
    private void decideWinners()
    {
        m_table.cleanPotsForWinning();
        nextState(TypeState.MONEY_DISTRIBUTION);
    }
    
    /**
     * Fait une pause d'un temps determine
     * 
     * @param waitingTime
     *            Temps de la pause (ms)
     */
    private void waitALittle(int waitingTime)
    {
        try
        {
            Thread.sleep(waitingTime);
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Essai de commencer la partie
     */
    private void TryToBegin()
    {
        m_table.decidePlayingPlayers();
        if (m_table.getNbPlaying() > 1)
        {
            m_table.initTable();
            m_dealer.freshDeck();
            nextState(TypeState.BLIND_WAITING);
            m_gameObserver.gameBlindsNeeded();
        }
        else if (m_table.getPlayers().size() > 1)
        {
            m_state = TypeState.END;
            m_gameObserver.everythingEnded();
        }
        else
        {
            m_table.setNoSeatDealer(-1);
            m_table.setNoSeatSmallBlind(-1);
            m_table.setNoSeatSmallBlind(-1);
        }
    }
    
    @Override
    public String encode()
    {
        String encode = "";
        
        // Assume que les game sont en real money
        encode += "1";
        
        // Assume que c'est tlt du Texas Hold'em
        encode += 0;
        
        // Assume que c'Est tlt des Ring game
        encode += 0;
        
        // Assume que c'Est tlt du NoLimit
        encode += 0;
        
        encode += m_table.getRound().ordinal();
        
        return encode;
    }
}
package poker;

import java.util.List;

import poker.observer.PokerGameObserver;

public class PokerGame implements IPokerGame
{
    private final PokerGameObserver m_gameObserver;
    private final PokerTableInfo m_pokerTable;
    private TypePokerGameState m_currentGameState;
    private TypePokerGameRoundState m_currentGameRoundState;
    private final int m_WaitingTimeAfterPlayerAction;
    private final int m_WaitingTimeAfterBoardDealed;
    private final int m_WaitingTimeAfterPotWon;
    
    private final AbstractPokerDealer m_pokerDealer;
    
    public PokerGame()
    {
        this(new RandomPokerDealer());
    }
    
    public PokerGame(PokerTableInfo info, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
    {
        this(new RandomPokerDealer(), info, wtaPlayerAction, wtaBoardDealed, wtaPotWon);
    }
    
    public PokerGame(AbstractPokerDealer dealer)
    {
        this(new RandomPokerDealer(), new PokerTableInfo(), 0, 0, 0);
    }
    
    public PokerGame(AbstractPokerDealer dealer, PokerTableInfo info, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
    {
        m_pokerDealer = dealer;
        m_gameObserver = new PokerGameObserver();
        m_pokerTable = info;
        m_currentGameState = TypePokerGameState.INIT;
        m_WaitingTimeAfterPlayerAction = wtaPlayerAction;
        m_WaitingTimeAfterBoardDealed = wtaBoardDealed;
        m_WaitingTimeAfterPotWon = wtaPotWon;
    }
    
    public void start()
    {
        setCurrentGameState(TypePokerGameState.PLAYERS_WAITING);
    }
    
    public TypePokerGameState getCurrentGameState()
    {
        return m_currentGameState;
    }
    
    public TypePokerGameRound getCurrentGameRound()
    {
        return m_pokerTable.getCurrentGameRound();
    }
    
    public TypePokerGameRoundState getCurrentGameRoundState()
    {
        return m_currentGameRoundState;
    }
    
    private void setCurrentGameState(TypePokerGameState newGS)
    {
        
        final TypePokerGameState oldGS = m_currentGameState;
        
        if (m_currentGameState == TypePokerGameState.END)
        {
            return;
        }
        
        if (newGS.ordinal() - oldGS.ordinal() != 1)
        {
            return;
        }
        
        m_currentGameState = newGS;
        
        switch (m_currentGameState)
        {
            case INIT:
                break;
            case PLAYERS_WAITING:
                TryToBegin();
                break;
            case BLIND_WAITING:
                m_pokerTable.setCurrentHigherBet(0);
                break;
            case PLAYING:
                m_pokerTable.setCurrentGameRound(TypePokerGameRound.PREFLOP);
                m_currentGameRoundState = TypePokerGameRoundState.CARDS;
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
    
    private void setCurrentGameRound(TypePokerGameRound newGR)
    {
        
        final TypePokerGameRound oldGR = m_pokerTable.getCurrentGameRound();
        
        if (m_currentGameState != TypePokerGameState.PLAYING)
        {
            return;
        }
        
        if (newGR.ordinal() - oldGR.ordinal() != 1)
        {
            return;
        }
        
        m_currentGameRoundState = TypePokerGameRoundState.CARDS;
        m_pokerTable.setCurrentPlayerNoSeat(m_pokerTable.getCurrentDealerNoSeat());
        m_pokerTable.setCurrentGameRound(newGR);
        startRound();
    }
    
    private void setCurrentGameRoundState(TypePokerGameRoundState newGRS)
    {
        
        final TypePokerGameRoundState oldGRS = m_currentGameRoundState;
        
        if (m_currentGameState != TypePokerGameState.PLAYING)
        {
            return;
        }
        
        if (newGRS.ordinal() - oldGRS.ordinal() != 1)
        {
            return;
        }
        
        m_currentGameRoundState = newGRS;
        startRound();
    }
    
    public PokerGameObserver getGameObserver()
    {
        return m_gameObserver;
    }
    
    public PokerTableInfo getPokerTable()
    {
        return m_pokerTable;
    }
    
    public boolean isRunning()
    {
        return m_currentGameState != TypePokerGameState.END;
    }
    
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // ///////////////// COMMUNICATION ///////////////////////////
    // /////////////////////////////////
    public boolean joinGame(PokerPlayerInfo p)
    {
        if (m_currentGameState == TypePokerGameState.INIT || m_currentGameState == TypePokerGameState.END)
        {
            System.err.println("Bad timing:" + m_currentGameState);
            return false;
        }
        
        return m_pokerTable.joinTable(p);
    }
    
    public void sitInGame(PokerPlayerInfo p)
    {
        m_gameObserver.playerJoined(p);
        if (m_currentGameState == TypePokerGameState.PLAYERS_WAITING)
        {
            TryToBegin();
        }
    }
    
    public boolean leaveGame(PokerPlayerInfo p)
    {
        if (m_pokerTable.leaveTable(p))
        {
            m_gameObserver.playerLeaved(p);
            return true;
        }
        return false;
    }
    
    public boolean playMoney(PokerPlayerInfo p, int amount)
    {
        final int amnt = Math.min(amount, p.getCurrentSafeMoneyAmount());
        System.out.println(p.getPlayerName() + " is playing " + amnt + " money on state: " + m_currentGameState);
        if (m_currentGameState == TypePokerGameState.BLIND_WAITING)
        {
            System.out.println(p.getPlayerName() + " is putting blind of " + amnt);
            System.out.println("Total still needed is " + m_pokerTable.getTotalBlindNeeded());
            final int needed = m_pokerTable.blindNeeded(p);
            if (amnt != needed)
            {
                if (p.canBet(amnt + 1))
                {
                    System.err.println(p.getPlayerName() + " needed to put " + needed);
                    return false;
                }
                else
                {
                    System.out.println("So ... All-In !");
                    p.setAllIn();
                    m_pokerTable.incNbAllIn();
                    m_pokerTable.addAllInCap(p.getCurrentBetMoneyAmount() + amnt);
                }
            }
            if (!p.tryBet(amnt))
            {
                System.err.println(p.getPlayerName() + " just .. can't !! ");
                return false;
            }
            m_pokerTable.incTotalPotAmount(amnt);
            m_gameObserver.playerMoneyChanged(p);
            m_pokerTable.setBlindNeeded(p, 0);
            if (amnt == m_pokerTable.getSmallBlindAmount())
            {
                m_gameObserver.playerActionTaken(p, TypePokerGameAction.SMALL_BLIND_POSTED, amnt);
            }
            else
            {
                m_gameObserver.playerActionTaken(p, TypePokerGameAction.BIG_BLIND_POSTED, amnt);
            }
            m_pokerTable.setTotalBlindNeeded(m_pokerTable.getTotalBlindNeeded() - needed);
            if (m_pokerTable.getTotalBlindNeeded() == 0)
            {
                setCurrentGameState(TypePokerGameState.PLAYING);
            }
            if (amnt > m_pokerTable.getCurrentHigherBet())
            {
                m_pokerTable.setCurrentHigherBet(amnt);
            }
            return true;
        }
        
        else if (m_currentGameState == TypePokerGameState.PLAYING && m_currentGameRoundState == TypePokerGameRoundState.BETTING)
        {
            System.out.println("Currently, we need " + m_pokerTable.getCallAmount(p) + " minimum money from this player");
            if (p.getCurrentTablePosition() != m_pokerTable.getCurrentPlayerNoSeat())
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
            int amntNeeded = m_pokerTable.getCallAmount(p);
            if (amnt < amntNeeded)
            {
                if (p.canBet(amnt + 1))
                {
                    System.err.println("BUT SCREW YOU, IT'S NOT ENOUGH !!!!!");
                    return false;
                }
                System.out.println("So ... All-In ! getCurrentBetMoneyAmount: " + p.getCurrentBetMoneyAmount());
                amntNeeded = amnt;
                p.setAllIn();
                m_pokerTable.incNbAllIn();
                m_pokerTable.addAllInCap(p.getCurrentBetMoneyAmount() + amnt);
            }
            if (!p.tryBet(amnt))
            {
                System.err.println("BUT SCREW YOU, YOU JUST CAN'T !!!!!");
                return false;
            }
            m_gameObserver.playerMoneyChanged(p);
            if (amnt == amntNeeded)
            {
                System.out.println("Will call with $" + amnt);
                m_pokerTable.incTotalPotAmount(amnt);
                callPlayer(p, amnt);
                continueBettingRound();
                return true;
            }
            System.out.println("Will raise with $" + amnt);
            m_pokerTable.incTotalPotAmount(amnt);
            raisePlayer(p, amnt);
            playNext();
            return true;
        }
        System.err.println("BUT WE DON'T CARE !!!!!");
        return false;
    }
    
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // /////////////////////////////////
    // ////// PRIV /////////////////////
    // /////////////////////////////////
    
    private void startRound()
    {
        switch (m_currentGameRoundState)
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
    
    private void showAllCards()
    {
        for (final PokerPlayerInfo p : m_pokerTable.getPlayers())
        {
            if (p.isPlaying() || p.isAllIn())
            {
                p.setShowingCards(true);
                m_gameObserver.playerHoleCardsChanged(p);
            }
        }
        setCurrentGameState(TypePokerGameState.DECIDE_WINNERS);
    }
    
    private void startCumulRound()
    {
        m_pokerTable.managePotsRoundEnd();
        m_gameObserver.gameBettingRoundEnded(m_pokerTable.getCurrentGameRound());
        if (m_pokerTable.getNbPlaying() == 1 && m_pokerTable.getNbAllIn() == 0)
        {
            setCurrentGameState(TypePokerGameState.SHOWDOWN);
        }
        else
        {
            switch (m_pokerTable.getCurrentGameRound())
            {
                case PREFLOP:
                    setCurrentGameRound(TypePokerGameRound.FLOP);
                    break;
                case FLOP:
                    setCurrentGameRound(TypePokerGameRound.TURN);
                    break;
                case TURN:
                    setCurrentGameRound(TypePokerGameRound.RIVER);
                    break;
                case RIVER:
                    setCurrentGameState(TypePokerGameState.SHOWDOWN);
                    break;
            }
        }
    }
    
    private void startBettingRound()
    {
        m_gameObserver.gameBettingRoundStarted();
        m_pokerTable.setNbPlayed(0);
        waitALittle(m_WaitingTimeAfterBoardDealed);
        continueBettingRound();
    }
    
    private void startCardRound()
    {
        switch (m_pokerTable.getCurrentGameRound())
        {
            case PREFLOP:
                m_pokerTable.setCurrentPlayerNoSeat(m_pokerTable.getCurrentBigBlindNoSeat());
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
        setCurrentGameRoundState(TypePokerGameRoundState.BETTING);
    }
    
    private void dealRiver()
    {
        m_pokerTable.addBoardCard(m_pokerDealer.dealRiver());
    }
    
    private void dealTurn()
    {
        m_pokerTable.addBoardCard(m_pokerDealer.dealTurn());
    }
    
    private void dealFlop()
    {
        m_pokerTable.addBoardCards(m_pokerDealer.dealFlop());
    }
    
    private void dealHole()
    {
        for (final PokerPlayerInfo p : m_pokerTable.getPlayingPlayers())
        {
            p.setHand(m_pokerDealer.dealHoles(p));
            m_gameObserver.playerHoleCardsChanged(p);
        }
    }
    
    private void foldPlayer(PokerPlayerInfo p)
    {
        p.setFolded();
        m_gameObserver.playerActionTaken(p, TypePokerGameAction.FOLDED, -1);
    }
    
    private void TryToBegin()
    {
        System.out.print("Trying to begin ...");
        m_pokerTable.getAndSetNbPlayingPlayers();
        if (m_pokerTable.getNbPlaying() > 1)
        {
            System.out.println(" yep ! do it !");
            m_pokerTable.initTable();
            m_pokerDealer.freshDeck();
            setCurrentGameState(TypePokerGameState.BLIND_WAITING);
            m_gameObserver.gameBlindsNeeded();
        }
        else
        {
            System.out.println(" just too bad :(");
            m_pokerTable.setCurrentDealerNoSeat(-1);
            m_pokerTable.setCurrentSmallBlindNoSeat(-1);
            m_pokerTable.setCurrentSmallBlindNoSeat(-1);
        }
    }
    
    private void callPlayer(PokerPlayerInfo p, int played)
    {
        m_pokerTable.incNbPlayed();
        m_gameObserver.playerActionTaken(p, TypePokerGameAction.CALLED, played);
    }
    
    private void raisePlayer(PokerPlayerInfo p, int played)
    {
        m_pokerTable.setNbPlayed(1);
        m_pokerTable.setCurrentHigherBet(p.getCurrentBetMoneyAmount());
        m_gameObserver.playerActionTaken(p, TypePokerGameAction.RAISED, played);
    }
    
    private void continueBettingRound()
    {
        // TODO: RICK: Semble que 2/2 all-in du meme montant au preflop chie: continue a jouer
        waitALittle(m_WaitingTimeAfterPlayerAction);
        if (m_pokerTable.getNbPlaying() == 1 || m_pokerTable.getNbPlayed() >= m_pokerTable.getNbPlaying())
        {
            endBettingRound();
        }
        else
        {
            playNext();
        }
    }
    
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
    
    private void endBettingRound()
    {
        setCurrentGameRoundState(TypePokerGameRoundState.CUMUL);
    }
    
    private void playNext()
    {
        final PokerPlayerInfo player = m_pokerTable.nextPlayingPlayer(m_pokerTable.getCurrentPlayerNoSeat());
        m_pokerTable.setCurrentPlayerNoSeat(player.getCurrentTablePosition());
        m_gameObserver.playerActionNeeded(player);
    }
    
    private void distributeMoney()
    {
        for (final PokerMoneyPot pot : m_pokerTable.getPots())
        {
            final List<PokerPlayerInfo> players = pot.getAttachedPlayers();
            if (players.size() > 0)
            {
                final int wonAmount = pot.getAmount() / players.size();
                for (final PokerPlayerInfo p : players)
                {
                    p.incCurrentSafeMoneyAmount(wonAmount);
                    m_gameObserver.playerMoneyChanged(p);
                    m_gameObserver.playerWonPot(p, pot, wonAmount);
                    
                    waitALittle(m_WaitingTimeAfterPotWon);
                }
            }
            else
            {
                System.err.println(">> POT SANS PLAYER: ANORMAL !!");
            }
        }
        m_gameObserver.gameEnded();
        m_currentGameState = TypePokerGameState.PLAYERS_WAITING;
        TryToBegin();
    }
    
    private void decideWinners()
    {
        m_pokerTable.cleanPotsForWinning();
        setCurrentGameState(TypePokerGameState.MONEY_DISTRIBUTION);
    }
}
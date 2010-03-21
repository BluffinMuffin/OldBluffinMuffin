package poker.game;

import java.util.List;

import poker.game.dealer.AbstractDealer;
import poker.game.dealer.RandomDealer;
import poker.game.observer.PokerGameObserver;

public class PokerGame implements IPokerGame
{
    public enum TypePokerGameState
    {
        INIT, PLAYERS_WAITING, BLIND_WAITING, PLAYING, SHOWDOWN, DECIDE_WINNERS, MONEY_DISTRIBUTION, END
    }
    
    public enum TypePokerGameRoundState
    {
        CARDS, BETTING, CUMUL
    }
    
    private final PokerGameObserver m_gameObserver;
    private final TableInfo m_pokerTable;
    private TypePokerGameState m_currentGameState;
    private TypePokerGameRoundState m_currentGameRoundState;
    private final int m_WaitingTimeAfterPlayerAction;
    private final int m_WaitingTimeAfterBoardDealed;
    private final int m_WaitingTimeAfterPotWon;
    
    private final AbstractDealer m_pokerDealer;
    
    public PokerGame()
    {
        this(new RandomDealer());
    }
    
    public PokerGame(TableInfo info, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
    {
        this(new RandomDealer(), info, wtaPlayerAction, wtaBoardDealed, wtaPotWon);
    }
    
    public PokerGame(AbstractDealer dealer)
    {
        this(new RandomDealer(), new TableInfo(), 0, 0, 0);
    }
    
    public PokerGame(AbstractDealer dealer, TableInfo info, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon)
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
    
    public TypeRound getCurrentGameRound()
    {
        return m_pokerTable.getRound();
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
                m_pokerTable.setHigherBet(0);
                break;
            case PLAYING:
                m_pokerTable.setRound(TypeRound.PREFLOP);
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
    
    private void setCurrentGameRound(TypeRound newGR)
    {
        
        final TypeRound oldGR = m_pokerTable.getRound();
        
        if (m_currentGameState != TypePokerGameState.PLAYING)
        {
            return;
        }
        
        if (newGR.ordinal() - oldGR.ordinal() != 1)
        {
            return;
        }
        
        m_currentGameRoundState = TypePokerGameRoundState.CARDS;
        m_pokerTable.setNoSeatCurrPlayer(m_pokerTable.getNoSeatDealer());
        m_pokerTable.setRound(newGR);
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
    
    public TableInfo getPokerTable()
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
    public boolean joinGame(PlayerInfo p)
    {
        if (m_currentGameState == TypePokerGameState.INIT || m_currentGameState == TypePokerGameState.END)
        {
            System.err.println("Bad timing:" + m_currentGameState);
            return false;
        }
        
        return m_pokerTable.joinTable(p);
    }
    
    public void sitInGame(PlayerInfo p)
    {
        m_gameObserver.playerJoined(p);
        if (m_currentGameState == TypePokerGameState.PLAYERS_WAITING)
        {
            TryToBegin();
        }
    }
    
    public boolean leaveGame(PlayerInfo p)
    {
        if (m_pokerTable.leaveTable(p))
        {
            m_gameObserver.playerLeaved(p);
            return true;
        }
        return false;
    }
    
    public boolean playMoney(PlayerInfo p, int amount)
    {
        final int amnt = Math.min(amount, p.getMoneySafeAmnt());
        System.out.println(p.getName() + " is playing " + amnt + " money on state: " + m_currentGameState);
        if (m_currentGameState == TypePokerGameState.BLIND_WAITING)
        {
            System.out.println(p.getName() + " is putting blind of " + amnt);
            System.out.println("Total still needed is " + m_pokerTable.getTotalBlindNeeded());
            final int needed = m_pokerTable.getBlindNeeded(p);
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
                    m_pokerTable.incNbAllIn();
                    m_pokerTable.addAllInCap(p.getMoneyBetAmnt() + amnt);
                }
            }
            if (!p.tryBet(amnt))
            {
                System.err.println(p.getName() + " just .. can't !! ");
                return false;
            }
            m_pokerTable.incTotalPotAmnt(amnt);
            m_gameObserver.playerMoneyChanged(p);
            m_pokerTable.setBlindNeeded(p, 0);
            if (amnt == m_pokerTable.getSmallBlindAmnt())
            {
                m_gameObserver.playerActionTaken(p, TypeAction.SMALL_BLIND_POSTED, amnt);
            }
            else
            {
                m_gameObserver.playerActionTaken(p, TypeAction.BIG_BLIND_POSTED, amnt);
            }
            m_pokerTable.setTotalBlindNeeded(m_pokerTable.getTotalBlindNeeded() - needed);
            if (m_pokerTable.getTotalBlindNeeded() == 0)
            {
                setCurrentGameState(TypePokerGameState.PLAYING);
            }
            if (amnt > m_pokerTable.getHigherBet())
            {
                m_pokerTable.setHigherBet(amnt);
            }
            return true;
        }
        
        else if (m_currentGameState == TypePokerGameState.PLAYING && m_currentGameRoundState == TypePokerGameRoundState.BETTING)
        {
            System.out.println("Currently, we need " + m_pokerTable.getCallAmnt(p) + " minimum money from this player");
            if (p.getNoSeat() != m_pokerTable.getNoSeatCurrPlayer())
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
            int amntNeeded = m_pokerTable.getCallAmnt(p);
            if (amnt < amntNeeded)
            {
                if (p.canBet(amnt + 1))
                {
                    System.err.println("BUT SCREW YOU, IT'S NOT ENOUGH !!!!!");
                    return false;
                }
                System.out.println("So ... All-In ! getCurrentBetMoneyAmount: " + p.getMoneyBetAmnt());
                amntNeeded = amnt;
                p.setAllIn();
                m_pokerTable.incNbAllIn();
                m_pokerTable.addAllInCap(p.getMoneyBetAmnt() + amnt);
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
                m_pokerTable.incTotalPotAmnt(amnt);
                callPlayer(p, amnt);
                continueBettingRound();
                return true;
            }
            System.out.println("Will raise with $" + amnt);
            m_pokerTable.incTotalPotAmnt(amnt);
            raisePlayer(p, amnt);
            continueBettingRound();
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
        for (final PlayerInfo p : m_pokerTable.getPlayers())
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
        m_gameObserver.gameBettingRoundEnded(m_pokerTable.getRound());
        if (m_pokerTable.getNbPlaying() == 1 && m_pokerTable.getNbAllIn() == 0)
        {
            setCurrentGameState(TypePokerGameState.SHOWDOWN);
        }
        else
        {
            switch (m_pokerTable.getRound())
            {
                case PREFLOP:
                    setCurrentGameRound(TypeRound.FLOP);
                    break;
                case FLOP:
                    setCurrentGameRound(TypeRound.TURN);
                    break;
                case TURN:
                    setCurrentGameRound(TypeRound.RIVER);
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
        switch (m_pokerTable.getRound())
        {
            case PREFLOP:
                m_pokerTable.setNoSeatCurrPlayer(m_pokerTable.getNoSeatBigBlind());
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
        m_pokerTable.addCard(m_pokerDealer.dealRiver());
    }
    
    private void dealTurn()
    {
        m_pokerTable.addCard(m_pokerDealer.dealTurn());
    }
    
    private void dealFlop()
    {
        m_pokerTable.addCards(m_pokerDealer.dealFlop());
    }
    
    private void dealHole()
    {
        for (final PlayerInfo p : m_pokerTable.getPlayingPlayers())
        {
            p.setCards(m_pokerDealer.dealHoles(p));
            m_gameObserver.playerHoleCardsChanged(p);
        }
    }
    
    private void foldPlayer(PlayerInfo p)
    {
        p.setNotPlaying();
        m_gameObserver.playerActionTaken(p, TypeAction.FOLDED, -1);
    }
    
    private void TryToBegin()
    {
        System.out.print("Trying to begin ...");
        m_pokerTable.decidePlayingPlayers();
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
            m_pokerTable.setNoSeatDealer(-1);
            m_pokerTable.setNoSeatSmallBlind(-1);
            m_pokerTable.setNoSeatSmallBlind(-1);
        }
    }
    
    private void callPlayer(PlayerInfo p, int played)
    {
        m_pokerTable.incNbPlayed();
        m_gameObserver.playerActionTaken(p, TypeAction.CALLED, played);
    }
    
    private void raisePlayer(PlayerInfo p, int played)
    {
        m_pokerTable.setNbPlayed(1);
        m_pokerTable.setHigherBet(p.getMoneyBetAmnt());
        m_gameObserver.playerActionTaken(p, TypeAction.RAISED, played);
    }
    
    private void continueBettingRound()
    {
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
        final PlayerInfo player = m_pokerTable.nextPlayingPlayer(m_pokerTable.getNoSeatCurrPlayer());
        m_pokerTable.setNoSeatCurrPlayer(player.getNoSeat());
        m_gameObserver.playerActionNeeded(player);
    }
    
    private void distributeMoney()
    {
        for (final MoneyPot pot : m_pokerTable.getPots())
        {
            final List<PlayerInfo> players = pot.getAttachedPlayers();
            if (players.size() > 0)
            {
                final int wonAmount = pot.getAmount() / players.size();
                for (final PlayerInfo p : players)
                {
                    p.incMoneySafeAmnt(wonAmount);
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
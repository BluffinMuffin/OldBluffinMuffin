package newPokerLogic;

import java.util.HashMap;
import java.util.Map;

import newPokerLogicTools.PokerGameObserver;

public class PokerGame
{
    private final PokerGameObserver m_gameObserver;
    private final PokerTableInfo m_pokerTable;
    
    private int m_currentDealerNoSeat;
    private int m_currentSmallBlindNoSeat;
    private int m_currentBigBlindNoSeat;
    private TypePokerGameState m_currentGameState;
    private TypePokerGameRound m_currentGameRound;
    private TypePokerGameRoundState m_currentGameRoundState;
    private int m_currentPlayerNoSeat;
    
    private final Map<PokerPlayerInfo, Integer> m_blindNeeded = new HashMap<PokerPlayerInfo, Integer>();
    private int m_totalBlindNeeded;
    
    private int m_nbPlayed;
    private int m_nbPlaying;
    private int m_currentHigherBet;
    
    private final AbstractPokerDealer m_pokerDealer;
    
    public PokerGame()
    {
        this(new RandomPokerDealer());
    }
    
    public PokerGame(AbstractPokerDealer dealer)
    {
        m_pokerDealer = dealer;
        m_gameObserver = new PokerGameObserver();
        m_pokerTable = new PokerTableInfo();
        m_currentGameState = TypePokerGameState.INIT;
        m_currentDealerNoSeat = -1;
        m_currentSmallBlindNoSeat = -1;
        m_currentBigBlindNoSeat = -1;
    }
    
    public int getCurrentDealerNoSeat()
    {
        return m_currentDealerNoSeat;
    }
    
    public void setCurrentDealerNoSeat(int currentDealerNoSeat)
    {
        m_currentDealerNoSeat = currentDealerNoSeat;
    }
    
    public int getCurrentSmallBlindNoSeat()
    {
        return m_currentSmallBlindNoSeat;
    }
    
    public void setCurrentSmallBlindNoSeat(int currentSmallBlindNoSeat)
    {
        m_currentSmallBlindNoSeat = currentSmallBlindNoSeat;
    }
    
    public int getCurrentBigBlindNoSeat()
    {
        return m_currentBigBlindNoSeat;
    }
    
    public void setCurrentBigBlindNoSeat(int currentBigBlindNoSeat)
    {
        m_currentBigBlindNoSeat = currentBigBlindNoSeat;
    }
    
    public TypePokerGameState getCurrentGameState()
    {
        return m_currentGameState;
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
        
        if (m_currentGameState == TypePokerGameState.PLAYING)
        {
            m_currentGameRound = TypePokerGameRound.PREFLOP;
            m_currentGameRoundState = TypePokerGameRoundState.CARDS;
        }
        
        m_gameObserver.gameStateChanged(oldGS, newGS);
        
        if (m_currentGameState == TypePokerGameState.PLAYERS_WAITING)
        {
            TryToBegin();
        }
        else if (m_currentGameState == TypePokerGameState.PLAYING)
        {
            startRound();
        }
    }
    
    public TypePokerGameRound getCurrentGameRound()
    {
        return m_currentGameRound;
    }
    
    private void setCurrentGameRound(TypePokerGameRound newGR)
    {
        
        final TypePokerGameRound oldGR = m_currentGameRound;
        
        if (m_currentGameState != TypePokerGameState.PLAYING)
        {
            return;
        }
        
        if (newGR.ordinal() - oldGR.ordinal() != 1)
        {
            return;
        }
        
        m_currentGameRoundState = TypePokerGameRoundState.CARDS;
        m_currentPlayerNoSeat = m_currentDealerNoSeat;
        m_currentGameRound = newGR;
        m_gameObserver.roundChanged(m_currentGameState, oldGR, newGR);
        startRound();
    }
    
    public TypePokerGameRoundState getCurrentGameRoundState()
    {
        return m_currentGameRoundState;
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
        m_gameObserver.roundStateChanged(m_currentGameState, m_currentGameRound, oldGRS, newGRS);
        startRound();
    }
    
    public int getCurrentPlayerNoSeat()
    {
        return m_currentPlayerNoSeat;
    }
    
    public void setCurrentPlayerNoSeat(int currentPlayerNoSeat)
    {
        m_currentPlayerNoSeat = currentPlayerNoSeat;
    }
    
    public PokerGameObserver getGameObserver()
    {
        return m_gameObserver;
    }
    
    public PokerTableInfo getPokerTable()
    {
        return m_pokerTable;
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
            return false;
        }
        
        if (m_pokerTable.joinTable(p))
        {
            m_gameObserver.playerJoined(p);
            if (m_currentGameState == TypePokerGameState.PLAYERS_WAITING)
            {
                TryToBegin();
            }
            return true;
        }
        
        return false;
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
    
    public boolean playMoney(PokerPlayerInfo p, int amnt)
    {
        
        if (m_currentGameState == TypePokerGameState.BLIND_WAITING)
        {
            if (amnt != blindNeeded(p))
            {
                return false;
            }
            m_blindNeeded.put(p, 0);
            if (amnt == m_pokerTable.getSmallBlindAmount())
            {
                m_gameObserver.smallBlindPosted(p, amnt);
            }
            else
            {
                m_gameObserver.bigBlindPosted(p, amnt);
            }
            m_totalBlindNeeded -= amnt;
            if (m_totalBlindNeeded == 0)
            {
                setCurrentGameState(TypePokerGameState.PLAYING);
            }
            return true;
        }
        
        else if (m_currentGameState == TypePokerGameState.PLAYING && m_currentGameRoundState == TypePokerGameRoundState.BETTING)
        {
            if (p.getCurrentTablePosition() != m_currentPlayerNoSeat)
            {
                return false;
            }
            
            if (amnt == -1)
            {
                foldPlayer(p);
                continueBettingRound();
                return true;
            }
            int amntNeeded = amountNeeded(p);
            if (amnt < amntNeeded)
            {
                if (!p.canBet(amnt + 1))
                {
                    return false;
                }
                amntNeeded = amnt;
                p.setAllIn();
                m_nbPlaying--;
            }
            if (!p.tryBet(amnt))
            {
                return false;
            }
            if (amnt == amntNeeded)
            {
                callPlayer(p, amnt);
                continueBettingRound();
                return true;
            }
            raisePlayer(p, amnt);
            playNext();
            return true;
        }
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
        }
    }
    
    private void startCumulRound()
    {
        if (m_nbPlaying == 1)
        {
            setCurrentGameState(TypePokerGameState.SHOWDOWN);
        }
        else
        {
            switch (m_currentGameRound)
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
        playNext();
    }
    
    private void startCardRound()
    {
        switch (m_currentGameRound)
        {
            case PREFLOP:
                m_currentPlayerNoSeat = m_currentBigBlindNoSeat;
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
        }
    }
    
    private int amountNeeded(PokerPlayerInfo p)
    {
        return m_currentHigherBet - p.getCurrentBetMoneyAmount();
    }
    
    private void foldPlayer(PokerPlayerInfo p)
    {
        p.setFolded();
        
        m_nbPlaying--;
        
        m_gameObserver.playerFolded(p);
    }
    
    private void TryToBegin()
    {
        m_nbPlaying = m_pokerTable.getAndSetNbPlayingPlayers();
        if (m_nbPlaying > 1)
        {
            m_nbPlayed = 0;
            placeButtons();
            setCurrentGameState(TypePokerGameState.BLIND_WAITING);
        }
        else
        {
            m_currentDealerNoSeat = -1;
            m_currentSmallBlindNoSeat = -1;
            m_currentBigBlindNoSeat = -1;
        }
    }
    
    private void placeButtons()
    {
        m_currentDealerNoSeat = m_pokerTable.nextPlayingPlayer(m_currentDealerNoSeat).getCurrentTablePosition();
        m_currentSmallBlindNoSeat = m_pokerTable.nextPlayingPlayer(m_currentDealerNoSeat).getCurrentTablePosition();
        m_currentBigBlindNoSeat = m_pokerTable.nextPlayingPlayer(m_currentSmallBlindNoSeat).getCurrentTablePosition();
        m_blindNeeded.clear();
        m_blindNeeded.put(m_pokerTable.getPlayer(m_currentSmallBlindNoSeat), m_pokerTable.getSmallBlindAmount());
        m_blindNeeded.put(m_pokerTable.getPlayer(m_currentBigBlindNoSeat), m_pokerTable.getBigBlindAmount());
        m_gameObserver.blindsNeeded(m_pokerTable.getPlayer(m_currentSmallBlindNoSeat), m_pokerTable.getPlayer(m_currentBigBlindNoSeat), m_pokerTable.getSmallBlindAmount(), m_pokerTable.getBigBlindAmount());
    }
    
    private void callPlayer(PokerPlayerInfo p, int played)
    {
        m_nbPlayed++;
        m_gameObserver.playerCalled(p, played, m_currentHigherBet);
    }
    
    private void raisePlayer(PokerPlayerInfo p, int played)
    {
        m_nbPlayed = 1;
        m_currentHigherBet = p.getCurrentBetMoneyAmount();
        m_gameObserver.playerRaised(p, played, m_currentHigherBet);
    }
    
    private void continueBettingRound()
    {
        if (m_nbPlayed == m_nbPlaying)
        {
            endBettingRound();
        }
        else
        {
            playNext();
        }
    }
    
    private void endBettingRound()
    {
        setCurrentGameRoundState(TypePokerGameRoundState.CUMUL);
    }
    
    private int blindNeeded(PokerPlayerInfo p)
    {
        if (m_blindNeeded.containsKey(p))
        {
            return m_blindNeeded.get(p);
        }
        return 0;
    }
    
    private void playNext()
    {
        final PokerPlayerInfo player = m_pokerTable.nextPlayingPlayer(m_currentPlayerNoSeat);
        m_currentPlayerNoSeat = player.getCurrentTablePosition();
    }
}
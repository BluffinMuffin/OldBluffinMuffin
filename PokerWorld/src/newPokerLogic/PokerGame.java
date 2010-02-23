package newPokerLogic;

import newPokerLogicTools.PokerGameObserver;

public class PokerGame
{
    private final PokerGameObserver m_gameObserver;
    private final PokerTableInfo m_pokerTable;
    private TypePokerGameState m_currentGameState;
    private TypePokerGameRound m_currentGameRound;
    private TypePokerGameRoundState m_currentGameRoundState;
    
    private final AbstractPokerDealer m_pokerDealer;
    
    public PokerGame()
    {
        this(new RandomPokerDealer());
    }
    
    public PokerGame(PokerTableInfo info)
    {
        this(new RandomPokerDealer(), info);
    }
    
    public PokerGame(AbstractPokerDealer dealer)
    {
        this(new RandomPokerDealer(), new PokerTableInfo());
    }
    
    public PokerGame(AbstractPokerDealer dealer, PokerTableInfo info)
    {
        m_pokerDealer = dealer;
        m_gameObserver = new PokerGameObserver();
        m_pokerTable = info;
        m_currentGameState = TypePokerGameState.INIT;
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
        return m_currentGameRound;
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
                break;
            case PLAYING:
                m_currentGameRound = TypePokerGameRound.PREFLOP;
                m_currentGameRoundState = TypePokerGameRoundState.CARDS;
                startRound();
                break;
            case SHOWDOWN:
                break;
            case DECIDE_WINNERS:
                break;
            case MONEY_DISTRIBUTION:
                break;
            case END:
                m_gameObserver.everythingEnded(m_pokerTable);
                break;
            
        }
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
        m_pokerTable.setCurrentPlayerNoSeat(m_pokerTable.getCurrentDealerNoSeat());
        m_currentGameRound = newGR;
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
        m_gameObserver.playerJoined(m_pokerTable, p);
        if (m_currentGameState == TypePokerGameState.PLAYERS_WAITING)
        {
            TryToBegin();
        }
    }
    
    public boolean leaveGame(PokerPlayerInfo p)
    {
        if (m_pokerTable.leaveTable(p))
        {
            m_gameObserver.playerLeaved(m_pokerTable, p);
            return true;
        }
        return false;
    }
    
    public boolean playMoney(PokerPlayerInfo p, int amnt)
    {
        System.out.println(p.getPlayerName() + " is playing " + amnt + " money on state: " + m_currentGameState);
        if (m_currentGameState == TypePokerGameState.BLIND_WAITING)
        {
            System.out.println(p.getPlayerName() + " is putting blind of " + amnt);
            System.out.println("Total still needed is " + m_pokerTable.getTotalBlindNeeded());
            if (amnt != m_pokerTable.blindNeeded(p))
            {
                System.err.println(p.getPlayerName() + " needed to put " + m_pokerTable.blindNeeded(p));
                return false;
            }
            if (!p.tryBet(amnt))
            {
                // TODO: authorise si ALL IN
                System.err.println(p.getPlayerName() + " just .. can't !! ");
                return false;
            }
            m_gameObserver.playerMoneyChanged(m_pokerTable, p);
            m_pokerTable.setBlindNeeded(p, 0);
            if (amnt == m_pokerTable.getSmallBlindAmount())
            {
                m_gameObserver.playerActionTaken(m_pokerTable, p, TypePokerGameAction.SMALL_BLIND_POSTED, amnt);
            }
            else
            {
                m_gameObserver.playerActionTaken(m_pokerTable, p, TypePokerGameAction.BIG_BLIND_POSTED, amnt);
            }
            m_pokerTable.setTotalBlindNeeded(m_pokerTable.getTotalBlindNeeded() - amnt);
            if (m_pokerTable.getTotalBlindNeeded() == 0)
            {
                setCurrentGameState(TypePokerGameState.PLAYING);
            }
            return true;
        }
        
        else if (m_currentGameState == TypePokerGameState.PLAYING && m_currentGameRoundState == TypePokerGameRoundState.BETTING)
        {
            if (p.getCurrentTablePosition() != m_pokerTable.getCurrentPlayerNoSeat())
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
                m_pokerTable.decNbPlaying();
            }
            if (!p.tryBet(amnt))
            {
                return false;
            }
            m_gameObserver.playerMoneyChanged(m_pokerTable, p);
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
                break;
        }
    }
    
    private void startCumulRound()
    {
        // TODO: lots of things
        // m_bettingPlayer = -1;
        //            
        // // Modify the pots
        // boolean addAPot = false;
        // Pot lastPot = null;
        // if (m_currentBet != 0)
        // {
        // if (bets.contains(m_currentBet))
        // {
        // addAPot = true;
        // lastPot = new Pot(m_pots.peek().getId() + bets.size() + 1);
        // }
        // bets.add(m_currentBet);
        // final int nbPots = bets.size();
        // final Pot[] newPots = new Pot[nbPots];
        // for (int i = 1; i < nbPots; i++)
        // {
        // newPots[i] = new Pot(m_pots.peek().getId() + i);
        // }
        // newPots[0] = m_pots.peek();
        // newPots[0].removeAllParticipant();
        //                
        // final int firstPlayer = nextPlayer(m_noSeatDealer);
        // m_playerTurn = firstPlayer;
        // int bet = 0;
        // do
        // {
        // bet = getPlayer(m_playerTurn).getBet();
        //                    
        // if (bet > 0)
        // {
        // int lastPotBet = 0;
        // final Iterator<Integer> it = bets.iterator();
        // boolean betPlaced = false;
        // int potIndex = 0;
        //                        
        // while (it.hasNext() && !betPlaced)
        // {
        // final int nextPotBet = it.next();
        // final Pot nextPot = newPots[potIndex];
        // if (bet >= nextPotBet)
        // {
        // nextPot.addAmount(nextPotBet - lastPotBet);
        // if (bet == nextPotBet)
        // {
        //                                    
        // betPlaced = true;
        // if (addAPot && !getPlayer(m_playerTurn).isAllIn())
        // {
        // lastPot.addParticipant(getPlayer(m_playerTurn));
        // }
        // else
        // {
        // nextPot.addParticipant(getPlayer(m_playerTurn));
        // }
        // }
        // }
        // else
        // {
        // nextPot.addAmount(bet - lastPotBet);
        // betPlaced = true;
        // }
        //                            
        // ++potIndex;
        // lastPotBet = nextPotBet;
        // }
        // }
        //                    
        // m_playerTurn = nextPlayer(m_playerTurn);
        // }
        // while (m_playerTurn != firstPlayer);
        //                
        // for (int i = 1; i < nbPots; i++)
        // {
        // m_pots.push(newPots[i]);
        // }
        // if (addAPot && (lastPot.getParticipant().size() > 0))
        // {
        // m_pots.push(lastPot);
        // }
        // }
        //            
        // m_currentBet = 0;
        //            
        // // notify the players that the turn ended
        // final int start = m_noSeatDealer;
        // int i = start;
        // do
        // {
        // getPlayer(i).endTurn();
        // i = nextPlayer(i);
        // }
        // while (i != start);
        m_gameObserver.gameBettingRoundEnded(m_pokerTable, m_currentGameRound);
        if (m_pokerTable.getNbPlaying() == 1)
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
        m_pokerTable.setNbPlayed(0);
        playNext();
    }
    
    private void startCardRound()
    {
        switch (m_currentGameRound)
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
        m_gameObserver.gameBoardCardsChanged(m_pokerTable);
    }
    
    private void dealTurn()
    {
        m_pokerTable.addBoardCard(m_pokerDealer.dealTurn());
        m_gameObserver.gameBoardCardsChanged(m_pokerTable);
    }
    
    private void dealFlop()
    {
        m_pokerTable.addBoardCards(m_pokerDealer.dealFlop());
        m_gameObserver.gameBoardCardsChanged(m_pokerTable);
    }
    
    private void dealHole()
    {
        for (final PokerPlayerInfo p : m_pokerTable.getPlayingPlayers())
        {
            p.setHand(m_pokerDealer.dealHoles(p));
            m_gameObserver.playerHoleCardsChanged(m_pokerTable, p);
        }
    }
    
    private int amountNeeded(PokerPlayerInfo p)
    {
        return m_pokerTable.getCurrentHigherBet() - p.getCurrentBetMoneyAmount();
    }
    
    private void foldPlayer(PokerPlayerInfo p)
    {
        p.setFolded();
        m_pokerTable.decNbPlaying();
        m_gameObserver.playerActionTaken(m_pokerTable, p, TypePokerGameAction.FOLDED, -1);
    }
    
    private void TryToBegin()
    {
        System.out.print("Trying to begin ...");
        m_pokerTable.setNbPlaying(m_pokerTable.getAndSetNbPlayingPlayers());
        if (m_pokerTable.getNbPlaying() > 1)
        {
            System.out.println(" yep ! do it !");
            m_pokerTable.setNbPlayed(0);
            m_pokerTable.placeButtons();
            m_pokerTable.initPots();
            setCurrentGameState(TypePokerGameState.BLIND_WAITING);
            m_gameObserver.gameBlindsNeeded(m_pokerTable);
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
        m_gameObserver.playerActionTaken(m_pokerTable, p, TypePokerGameAction.CALLED, played);
    }
    
    private void raisePlayer(PokerPlayerInfo p, int played)
    {
        m_pokerTable.setNbPlayed(1);
        m_pokerTable.setCurrentHigherBet(p.getCurrentBetMoneyAmount());
        m_gameObserver.playerActionTaken(m_pokerTable, p, TypePokerGameAction.RAISED, played);
    }
    
    private void continueBettingRound()
    {
        if (m_pokerTable.getNbPlayed() == m_pokerTable.getNbPlaying())
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
    
    private void playNext()
    {
        final PokerPlayerInfo player = m_pokerTable.nextPlayingPlayer(m_pokerTable.getCurrentPlayerNoSeat());
        m_pokerTable.setCurrentPlayerNoSeat(player.getCurrentTablePosition());
        m_gameObserver.playerActionNeeded(m_pokerTable, player);
    }
}
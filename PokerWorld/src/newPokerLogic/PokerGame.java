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
    
    private final Map<PokerPlayerInfo,Integer> m_blindNeeded = new HashMap<PokerPlayerInfo,Integer>();
    private int m_totalBlindNeeded;
    
    public PokerGame()
    {
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

    	TypePokerGameState oldGS = m_currentGameState;
    	
    	if( m_currentGameState == TypePokerGameState.END)
    		return;
    	
    	if(newGS.ordinal()-oldGS.ordinal() != 1)
    		return;
        
    	if(newGS == TypePokerGameState.PLAYING){
    		m_currentGameRound = TypePokerGameRound.PREFLOP;
    		m_currentGameRoundState = TypePokerGameRoundState.CARDS;
    	}
    	
        m_currentGameState = newGS;
    	
    	m_gameObserver.gameStateChanged(oldGS, newGS);
    }
    
    public TypePokerGameRound getCurrentGameRound()
    {
        return m_currentGameRound;
    }
    
    private void setCurrentGameRound(TypePokerGameRound newGR)
    {

    	TypePokerGameRound oldGR = m_currentGameRound;
    	
    	if( m_currentGameState != TypePokerGameState.PLAYING)
    		return;
    	
    	if(newGR.ordinal()-oldGR.ordinal() != 1)
    		return;
    	
    	m_currentGameRoundState = TypePokerGameRoundState.CARDS;
    	
        m_currentGameRound = newGR;
    	
    	m_gameObserver.roundChanged(m_currentGameState,oldGR, newGR);
    }
    
    public TypePokerGameRoundState getCurrentGameRoundState()
    {
        return m_currentGameRoundState;
    }
    private void setCurrentGameRoundState(TypePokerGameRoundState newGRS)
    {

    	TypePokerGameRoundState oldGRS = m_currentGameRoundState;
 
    	if( m_currentGameState != TypePokerGameState.PLAYING)
    		return;
    	
    	if(newGRS.ordinal()-oldGRS.ordinal() != 1)
    		return;
    	
        m_currentGameRoundState = newGRS;
    	
    	m_gameObserver.roundStateChanged(m_currentGameState,m_currentGameRound,oldGRS, newGRS);
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
    
    /////////////////// COMMUNICATION ///////////////////////////
    public boolean joinGame(PokerPlayerInfo p)
    {
    	if( m_currentGameState == TypePokerGameState.INIT || m_currentGameState == TypePokerGameState.END)
    		return false;

    	playNext();
    	if(m_pokerTable.joinTable(p))
    	{
    		m_gameObserver.playerJoined(p);
    		return true;
    	}    	    	
    	
    	return false;
    }
    public boolean leaveGame(PokerPlayerInfo p)
    {
    	if( m_pokerTable.leaveTable(p))
    	{
    		m_gameObserver.playerLeaved(p);
    		return true;
    	}
    	return false;
    }
    public boolean playMoney(PokerPlayerInfo p, int amnt)
    {

    	if( m_currentGameState == TypePokerGameState.BLIND_WAITING)
    	{
    		if( amnt != m_blindNeeded.get(p))
    			return false;
    		m_blindNeeded.put(p,0);
    		if( amnt == m_pokerTable.getSmallBlindAmount())
    			m_gameObserver.smallBlindPosted(p, amnt);
    		else
    			m_gameObserver.bigBlindPosted(p, amnt);
    		m_totalBlindNeeded -= amnt;
    		if( m_totalBlindNeeded == 0 )
    			setCurrentGameState(TypePokerGameState.PLAYING);
    		return true;
    	}

    	else if( m_currentGameState == TypePokerGameState.PLAYING && m_currentGameRoundState == TypePokerGameRoundState.BETTING)
    	{
    		//TODO: C'est bel et bien a ton tour de jouer ??!
    		
    		if(amnt == -1)
    		{
    			foldPlayer(p);
    			continueBettingRound();
    			return true;
    		}
    		if(amnt < amountNeeded(p))
    			return false;
    		if(amnt == amountNeeded(p))
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

	private int amountNeeded(PokerPlayerInfo p) {
		// TODO Auto-generated method stub
		return 0;
	}

	//////// PRIV /////////////////////
    private void foldPlayer( PokerPlayerInfo p )
    {
    	//TODO: p Ne jouera plus
    	
    	m_gameObserver.playerFolded(p);
    	
    	//TODO: nbPlaying--
    }
    private void callPlayer( PokerPlayerInfo p, int played )
    {
    	//TODO: m_gameObserver.playerCalled(p, played, /*totalCallValue*/);
    	
    	//TODO: nbPlayed++
    }
    private void raisePlayer( PokerPlayerInfo p, int played )
    {
    	//TODO: m_gameObserver.playerRaised(p, playedAmount, /*newMax*/)
    	
    	//TODO: nbPlayed = 1
    }
    
    private void continueBettingRound()
    {
    	//TODO: nbPlaying = nbPlayed ?
    	//TODO: yes -> 
    	endBettingRound();
    	//TODO: no ->
    	playNext();
    }
    
    private void endBettingRound()
    {
    	//TODO: endBettingRound
    }
    
    private void playNext()
    {
    	//TODO: playNext
    }
}
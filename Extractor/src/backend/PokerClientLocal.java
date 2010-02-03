package backend;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingQueue;

import miscUtil.Constants;
import miscUtil.IClosingListener;
import utilGUI.AutoListModel;
import utilGUI.ListEvent;
import utilGUI.ListListener;
import basePoker.Card;
import basePoker.PokerPlayerAction;
import basePoker.PokerPlayerInfo;
import basePoker.TypePlayerAction;
import basePoker.TypePokerRound;
import basePokerAI.IPokerAgent;
import basePokerAI.IPokerAgentActionner;
import basePokerAI.IPokerAgentListener;
import baseProtocol.TypeMessageTableToClient;

/**
 * This class is the representation of a generic poker client.
 * 
 * @author Hokus
 */
public class PokerClientLocal extends Thread implements ListListener<IPokerAgentListener>, IClosingListener<IPokerAgent>
{
    public static final int NB_CARDS_BOARDS = 5;
    public static final int NB_SEATS = 9;
    
    IPokerAgentActionner m_agent = null;
    AutoListModel<IPokerAgentListener> m_observers = null;
    
    ClientPokerTableInfo m_table;
    
    LinkedBlockingQueue<String> m_fromServer = null;
    LinkedBlockingQueue<String> m_toServer = null;
    List<IClosingListener<PokerClientLocal>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<PokerClientLocal>>());
    
    public PokerClientLocal(IPokerAgentActionner p_agent, AutoListModel<IPokerAgentListener> p_observers, ClientPokerPlayerInfo p_localPlayer, ClientPokerTableInfo p_table, LinkedBlockingQueue<String> p_fromServer, LinkedBlockingQueue<String> p_toServer)
    {
        m_table = p_table;
        m_agent = p_agent;
        m_observers = p_observers;
        m_observers.addListListener(this);
        
        m_table.m_localPlayer = p_localPlayer;
        m_table.m_dealer = p_localPlayer;
        m_table.m_smallBlind = p_localPlayer;
        m_table.m_bigBlind = p_localPlayer;
        // m_table.m_players.put(p_localPlayer.m_noSeat, p_localPlayer);
        
        /* Hard Coding - Needed Rules */
        for (int i = 0; i != PokerClientLocal.NB_CARDS_BOARDS; ++i)
        {
            m_table.m_boardCards.add(Card.getInstance().get(-1));
        }
        
        for (int i = 0; i != PokerClientLocal.NB_SEATS; ++i)
        {
            m_table.m_pots.add(0);
        }
        
        m_fromServer = p_fromServer;
        m_toServer = p_toServer;
    }
    
    public void addClosingListener(IClosingListener<PokerClientLocal> p_listener)
    {
        m_closingListeners.add(p_listener);
    }
    
    /**
     * Happens when a betting turn ends.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [BETTING_TURN_ENDED;pot[0-nbSeats]Amount;TypePokerRound]
     */
    private void betTurnEnded(StringTokenizer p_token)
    {
        final ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i != m_table.m_pots.size(); ++i)
        {
            final int potAmount = Integer.parseInt(p_token.nextToken());
            if (potAmount != m_table.m_pots.get(i))
            {
                m_table.m_pots.set(i, potAmount);
                indices.add(i);
            }
        }
        
        for (final PokerPlayerInfo player : m_table.getPlayers())
        {
            player.m_betAmount = 0;
        }
        
        final TypePokerRound gameState = TypePokerRound.valueOf(p_token.nextToken());
        m_table.m_gameState = gameState;
        
        m_table.m_currentBet = 0;
        
        notifyObserver(IPokerAgentListener.BET_TURN_ENDED, indices, gameState);
    }
    
    /**
     * Happens when cards on the board changes.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [BOARD_CHANGED;idCard1;idCard2;idCard3;idCard4;idCard5;]
     */
    private void boardChanged(StringTokenizer p_token)
    {
        final ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i != m_table.m_boardCards.size(); ++i)
        {
            final Card card = Card.getInstance().get(Integer.parseInt(p_token.nextToken()));
            if (card != m_table.m_boardCards.get(i))
            {
                m_table.m_boardCards.set(i, card);
                indices.add(i);
            }
        }
        
        notifyObserver(IPokerAgentListener.BOARD_CHANGED, indices);
    }
    
    @Override
    public void closing(IPokerAgent e)
    {
        if (e == m_agent)
        {
            disconnect();
        }
        else
        {
            m_observers.remove(e);
        }
    }
    
    public void disconnect()
    {
        if (isConnected())
        {
            m_toServer = null;
            m_fromServer = null;
            m_observers.clear();
            stopAgent(m_agent);
            
            synchronized (m_closingListeners)
            {
                for (final IClosingListener<PokerClientLocal> listener : m_closingListeners)
                {
                    listener.closing(this);
                }
            }
        }
    }
    
    /**
     * Happens when a game ends.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [GAME_ENDED;]
     */
    private void gameEnded(StringTokenizer p_token)
    {
        m_table.m_gameState = TypePokerRound.END;
        notifyObserver(IPokerAgentListener.GAME_ENDED);
    }
    
    /**
     * Happens when a game starts.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [GAME_STARTED;noSeatDealer;noSeatSmallBlind;noSeatBigBlind;]
     */
    private void gameStarted(StringTokenizer p_token)
    {
        m_table.m_noSeatDealer = Integer.parseInt(p_token.nextToken());
        m_table.m_noSeatSmallBlind = Integer.parseInt(p_token.nextToken());
        m_table.m_noSeatBigBlind = Integer.parseInt(p_token.nextToken());
        
        // Player oldDealer = m_table.m_dealer;
        // Player oldSmallBlind = m_table.m_smallBlind;
        // Player oldBigBlind = m_table.m_bigBlind;
        
        // Set dealer, small blind and big blind.
        final PokerPlayerInfo dealer = m_table.getPlayer(m_table.m_noSeatDealer);
        final PokerPlayerInfo smallBlind = m_table.getPlayer(m_table.m_noSeatSmallBlind);
        final PokerPlayerInfo bigBlind = m_table.getPlayer(m_table.m_noSeatBigBlind);
        
        m_table.m_dealer = dealer;
        if (m_table.m_dealer != null)
        {
            m_table.m_dealer.m_isDealer = true;
        }
        m_table.m_smallBlind = smallBlind;
        if (m_table.m_smallBlind != null)
        {
            m_table.m_smallBlind.m_isSmallBlind = true;
        }
        m_table.m_bigBlind = bigBlind;
        if (m_table.m_bigBlind != null)
        {
            m_table.m_bigBlind.m_isBigBlind = true;
        }
        
        // Remove buttons from old dealer, old small blind and old big blind.
        // if (oldDealer != null) oldDealer.m_isDealer = false;
        // if (oldSmallBlind != null) oldSmallBlind.m_isSmallBlind = false;
        // if (oldBigBlind != null) oldBigBlind.m_isBigBlind = false;
        
        m_table.m_totalPotAmount = 0;
        resetPots();
        resetBoardCards();
        
        m_table.setPlayerPositions();
        m_table.m_gameState = TypePokerRound.PREFLOP;
        m_table.m_currentBet = 0;
        
        notifyObserver(IPokerAgentListener.GAME_STARTED, null, null, null);// , oldSmallBlind, oldBigBlind);
    }
    
    public IPokerAgentActionner getAgent()
    {
        return m_agent;
    }
    
    public AutoListModel<IPokerAgentListener> getObservers()
    {
        return m_observers;
    }
    
    public ClientPokerTableInfo getTable()
    {
        return m_table;
    }
    
    public boolean isConnected()
    {
        return (m_toServer != null) && (m_fromServer != null);
    }
    
    @Override
    public void itemsAdded(ListEvent<IPokerAgentListener> e)
    {
        startListeners(e.getItems());
    }
    
    @Override
    public void itemsChanged(ListEvent<IPokerAgentListener> e)
    {
        final IPokerAgentListener oldListener = e.getItems().get(0);
        final IPokerAgentListener newListener = e.getItems().get(1);
        
        if (oldListener != newListener)
        {
            stopAgent(oldListener);
            startAgent(newListener);
            newListener.tableInfos();
        }
    }
    
    @Override
    public void itemsRemoved(ListEvent<IPokerAgentListener> e)
    {
        stopListeners(e.getItems());
    }
    
    private void msgReceived(String p_msg)
    {
        // System.out.println(m_table.m_localPlayer.m_name + " RECV [" + p_msg + "]");
        final StringTokenizer token = new StringTokenizer(p_msg, Constants.DELIMITER);
        
        synchronized (m_table)
        {
            try
            {
                switch (TypeMessageTableToClient.valueOf(token.nextToken()))
                {
                    case BETTING_TURN_ENDED:
                        betTurnEnded(token);
                        break;
                    case BOARD_CHANGED:
                        boardChanged(token);
                        break;
                    case GAME_ENDED:
                        gameEnded(token);
                        break;
                    case GAME_STARTED:
                        gameStarted(token);
                        break;
                    case PLAYER_CARD_CHANGED:
                        playerCardChanged(token);
                        break;
                    case PLAYER_JOINED:
                        playerJoined(token);
                        break;
                    case PLAYER_LEFT:
                        playerLeft(token);
                        break;
                    case PLAYER_MONEY_CHANGED:
                        playerMoneyChanged(token);
                        break;
                    case PLAYER_TURN_BEGAN:
                        playerTurnBegan(token);
                        break;
                    case PLAYER_TURN_ENDED:
                        playerTurnEnded(token);
                        break;
                    case POT_WON:
                        potWon(token);
                        break;
                    case TABLE_CLOSED:
                        tableClosed(token);
                        break;
                    case TABLE_INFOS:
                        tableInfos(token);
                        break;
                    case TAKE_ACTION:
                        takeAction(token);
                        break;
                    case WAITING_FOR_PLAYERS:
                        waitingForPlayers(token);
                        break;
                    case PING:
                        send(TypePlayerAction.PONG.name());
                        break;
                }
            }
            catch (final Exception e)
            {
                System.out.println("****CLIENT****");
                e.printStackTrace();
            }
            catch (final Throwable t)
            {
                System.out.println("****CLIENT****");
                t.printStackTrace();
            }
        }
    }
    
    private void notifyObserver(Method p_method, Object... p_objects)
    {
        synchronized (m_observers)
        {
            for (final IPokerAgentListener observer : m_observers)
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
     * Happens when the cards of a player changes.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [PLAYER_CARD_CHANGED;noSeat;idCard1;idCard2;]
     */
    private void playerCardChanged(StringTokenizer p_token)
    {
        final int noSeat = Integer.parseInt(p_token.nextToken());
        final Card card1 = Card.getInstance().get(Integer.parseInt(p_token.nextToken()));
        final Card card2 = Card.getInstance().get(Integer.parseInt(p_token.nextToken()));
        
        final PokerPlayerInfo player = m_table.getPlayer(noSeat);
        player.setHand(card1, card2);
        
        notifyObserver(IPokerAgentListener.PLAYER_CARD_CHANGED, player);
    }
    
    /**
     * Happens when a player joined the table.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [PLAYER_JOINED;noSeat;playerName;moneyAmount;]
     */
    private void playerJoined(StringTokenizer p_token)
    {
        final int noSeat = Integer.parseInt(p_token.nextToken());
        final String playerName = p_token.nextToken();
        final int moneyAmount = Integer.parseInt(p_token.nextToken());
        
        final ClientPokerPlayerInfo player = new ClientPokerPlayerInfo(noSeat, playerName, moneyAmount);
        m_table.addPlayer(noSeat, player);
        
        notifyObserver(IPokerAgentListener.PLAYER_JOINED, player);
    }
    
    /**
     * Happens when a player left the table.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [PLAYER_LEFT;noSeat;]
     */
    private void playerLeft(StringTokenizer p_token)
    {
        final int noSeat = Integer.parseInt(p_token.nextToken());
        
        final PokerPlayerInfo player = m_table.getPlayer(noSeat);
        m_table.removePlayer(noSeat);
        
        notifyObserver(IPokerAgentListener.PLAYER_LEFT, player);
    }
    
    /**
     * Happens when the money amount of a player changes.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [PLAYER_MONEY_CHANGED;noSeat;moneyAmount;]
     */
    private void playerMoneyChanged(StringTokenizer p_token)
    {
        final int noSeat = Integer.parseInt(p_token.nextToken());
        final int moneyAmount = Integer.parseInt(p_token.nextToken());
        
        final PokerPlayerInfo player = m_table.getPlayer(noSeat);
        final int oldMoneyAmount = player.m_money;
        player.m_money = moneyAmount;
        
        notifyObserver(IPokerAgentListener.PLAYER_MONEY_CHANGED, player, oldMoneyAmount);
    }
    
    /**
     * Happens when the turn of a player begins.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [PLAYER_TURN_BEGAN;noSeat;]
     */
    private void playerTurnBegan(StringTokenizer p_token)
    {
        final int noSeat = Integer.parseInt(p_token.nextToken());
        
        final PokerPlayerInfo oldCurrentPlayer = m_table.m_currentPlayer;
        m_table.m_currentPlayer = m_table.getPlayer(noSeat);
        
        notifyObserver(IPokerAgentListener.PLAYER_TURN_BEGAN, oldCurrentPlayer);
    }
    
    /**
     * Happens when the turn of a player ends.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [PLAYER_TURN_ENDED;noSeat;betAmount;moneyAmount;totalPotAmount;action;actionAmount;]
     */
    private void playerTurnEnded(StringTokenizer p_token)
    {
        final int noSeat = Integer.parseInt(p_token.nextToken());
        final int betAmount = Integer.parseInt(p_token.nextToken());
        final int moneyAmount = Integer.parseInt(p_token.nextToken());
        final int totalPotAmount = Integer.parseInt(p_token.nextToken());
        final TypePlayerAction action = TypePlayerAction.valueOf(p_token.nextToken());
        final int actionAmount = Integer.parseInt(p_token.nextToken());
        
        final ClientPokerPlayerInfo player = (ClientPokerPlayerInfo) m_table.getPlayer(noSeat);
        player.m_betAmount = betAmount;
        player.m_money = moneyAmount;
        m_table.m_totalPotAmount = totalPotAmount;
        m_table.m_currentBet = Math.max(m_table.m_currentBet, betAmount);
        
        if (action.equals(TypePlayerAction.FOLD))
        {
            m_table.m_nbRemainingPlayers--;
        }
        
        player.did(action, m_table.m_gameState);
        
        notifyObserver(IPokerAgentListener.PLAYER_TURN_ENDED, player, action, actionAmount);
    }
    
    /**
     * Happens when a player wins and receives his share.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [POT_WON;noSeat;idPot;potAmountWon;moneyAmount;]
     */
    private void potWon(StringTokenizer p_token)
    {
        final int noSeat = Integer.parseInt(p_token.nextToken());
        final int idPot = Integer.parseInt(p_token.nextToken());
        final int potAmountWon = Integer.parseInt(p_token.nextToken());
        final int moneyAmount = Integer.parseInt(p_token.nextToken());
        
        final PokerPlayerInfo player = m_table.getPlayer(noSeat);
        player.m_money = moneyAmount;
        
        notifyObserver(IPokerAgentListener.POT_WON, player, potAmountWon, idPot);
    }
    
    public void removeClosingListener(IClosingListener<PokerClientLocal> p_listener)
    {
        m_closingListeners.remove(p_listener);
    }
    
    private void resetBoardCards()
    {
        for (int i = 0; i != PokerClientLocal.NB_CARDS_BOARDS; ++i)
        {
            m_table.m_boardCards.set(i, Card.getInstance().get(-1));
        }
    }
    
    private void resetPots()
    {
        for (int i = 0; i != m_table.m_pots.size(); ++i)
        {
            m_table.m_pots.set(i, 0);
        }
    }
    
    @Override
    public void run()
    {
        if (m_agent == null)
        {
            startListeners(m_observers);
            startListening();
        }
        else
        {
            this.setName(m_agent.getClass().getSimpleName() + " - " + m_table.m_localPlayer.m_name);
            
            startListeners(m_observers);
            
            if (!(m_agent instanceof IPokerAgentListener))
            {
                startAgent(m_agent);
            }
            
            startListening();
            
            while (isConnected())
            {
                final PokerPlayerAction action = m_agent.getAction();
                send(action);
                
                if (action.getType() == TypePlayerAction.DISCONNECT)
                {
                    disconnect();
                }
            }
        }
    }
    
    protected void send(PokerPlayerAction p_action)
    {
        final StringBuilder sb = new StringBuilder();
        
        sb.append(p_action.getType().toString());
        sb.append(Constants.DELIMITER);
        
        if (p_action.getType() == TypePlayerAction.RAISE)
        {
            sb.append(p_action.getAmount());
        }
        
        send(sb.toString());
    }
    
    protected void send(String p_msg)
    {
        System.out.println(m_table.m_localPlayer.m_name + " SEND [" + p_msg + "]");
        m_toServer.add(p_msg);
    }
    
    private void startAgent(IPokerAgent p_agent)
    {
        p_agent.setTable(m_table);
        p_agent.addClosingListener(this);
        p_agent.start();
    }
    
    private void startListeners(List<IPokerAgentListener> p_listeners)
    {
        for (final IPokerAgentListener listener : p_listeners)
        {
            startAgent(listener);
            listener.tableInfos();
        }
    }
    
    public void startListening()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    while (isConnected())
                    {
                        final String msg = m_fromServer.take();
                        
                        if (msg == null)
                        {
                            break;
                        }
                        
                        try
                        {
                            msgReceived(msg);
                        }
                        catch (final Exception e)
                        {
                            System.out.println("****CLIENT****");
                            e.printStackTrace();
                        }
                        catch (final Throwable t)
                        {
                            System.out.println("****CLIENT****");
                            t.printStackTrace();
                        }
                    }
                }
                catch (final InterruptedException e)
                {/* e.printStackTrace(); */
                }
                
                disconnect();
            }
        }.start();
    }
    
    private void stopAgent(IPokerAgent p_agent)
    {
        p_agent.stop();
    }
    
    private void stopListeners(List<IPokerAgentListener> p_listeners)
    {
        for (final IPokerAgentListener listener : p_listeners)
        {
            stopAgent(listener);
        }
    }
    
    /**
     * Happens when the table closes.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [TABLE_CLOSED;]
     */
    private void tableClosed(StringTokenizer p_token)
    {
        notifyObserver(IPokerAgentListener.TABLE_CLOSED);
        disconnect();
    }
    
    /**
     * Happens when all infos of a table is received.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [TABLE_INFOS;totalPotAmount;
     *      nbPots;{potAmount};
     *      nbPlayers;{noSeat;playerName;isWinner;moneyAmount;card1;card2;
     *      isDealer;isSmallBlind;isBigBlind;isPlaying;timeRemaining;betAmount}
     */
    private void tableInfos(StringTokenizer p_token)
    {
        m_table.m_totalPotAmount = Integer.parseInt(p_token.nextToken());
        
        final int nbPots = Integer.parseInt(p_token.nextToken());
        m_table.m_pots.clear();
        
        for (int i = 0; i != nbPots; ++i)
        {
            m_table.m_pots.add(Integer.parseInt(p_token.nextToken()));
        }
        
        for (int i = 0; i != m_table.m_boardCards.size(); ++i)
        {
            m_table.m_boardCards.set(i, Card.getInstance().get(Integer.parseInt(p_token.nextToken())));
        }
        
        m_table.m_nbPlayers = Integer.parseInt(p_token.nextToken());
        
        m_table.clearPlayers();
        // m_table.m_players.put(m_table.m_localPlayer.m_noSeat, m_table.m_localPlayer);
        
        for (int i = 0; i != m_table.m_nbPlayers; ++i)
        {
            final int noSeat = Integer.parseInt(p_token.nextToken());
            
            final boolean isEmpty = Boolean.parseBoolean(p_token.nextToken());
            if (isEmpty)
            {
                continue;
            }
            
            if (m_table.getPlayer(noSeat) == null)
            {
                m_table.addPlayer(noSeat, new ClientPokerPlayerInfo(noSeat));
            }
            
            final PokerPlayerInfo player = m_table.getPlayer(noSeat);
            
            player.m_name = p_token.nextToken();
            player.m_money = Integer.parseInt(p_token.nextToken());
            final Card card1 = Card.getInstance().get(Integer.parseInt(p_token.nextToken()));
            final Card card2 = Card.getInstance().get(Integer.parseInt(p_token.nextToken()));
            player.setHand(card1, card2);
            player.m_isDealer = Boolean.parseBoolean(p_token.nextToken());
            player.m_isSmallBlind = Boolean.parseBoolean(p_token.nextToken());
            player.m_isBigBlind = Boolean.parseBoolean(p_token.nextToken());
            
            if (player.m_isDealer)
            {
                m_table.m_dealer = player;
                m_table.m_noSeatDealer = player.m_noSeat;
            }
            
            if (player.m_isSmallBlind)
            {
                m_table.m_smallBlind = player;
                m_table.m_noSeatSmallBlind = player.m_noSeat;
            }
            
            if (player.m_isBigBlind)
            {
                m_table.m_bigBlind = player;
                m_table.m_noSeatBigBlind = player.m_noSeat;
            }
            
            final boolean isPlaying = Boolean.parseBoolean(p_token.nextToken());
            if (isPlaying)
            {
                m_table.m_currentPlayer = player;
            }
            
            player.m_timeRemaining = Integer.parseInt(p_token.nextToken());
            player.m_betAmount = Integer.parseInt(p_token.nextToken());
        }
        
        notifyObserver(IPokerAgentListener.TABLE_INFOS);
    }
    
    /**
     * Happens when it is to the client to make a move.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [TAKE_ACTION;checkAllowed;foldAllowed;callAllowed;callAmount;raiseAllowed;minRaiseAmount;maxRaiseAmount]
     */
    private void takeAction(StringTokenizer p_token)
    {
        final boolean checkAllowed = Boolean.parseBoolean(p_token.nextToken());
        final boolean foldAllowed = Boolean.parseBoolean(p_token.nextToken());
        final boolean callAllowed = Boolean.parseBoolean(p_token.nextToken());
        final int callAmount = Integer.parseInt(p_token.nextToken());
        final boolean raiseAllowed = Boolean.parseBoolean(p_token.nextToken());
        final int minRaiseAmount = Integer.parseInt(p_token.nextToken());
        final int maxRaiseAmount = Integer.parseInt(p_token.nextToken());
        
        final ArrayList<TypePlayerAction> m_allowedActions = new ArrayList<TypePlayerAction>();
        
        if (checkAllowed)
        {
            m_allowedActions.add(TypePlayerAction.CHECK);
        }
        
        if (foldAllowed)
        {
            m_allowedActions.add(TypePlayerAction.FOLD);
        }
        
        if (callAllowed)
        {
            m_allowedActions.add(TypePlayerAction.CALL);
        }
        
        if (raiseAllowed)
        {
            m_allowedActions.add(TypePlayerAction.RAISE);
        }
        
        m_agent.takeAction(m_allowedActions, callAmount, minRaiseAmount, maxRaiseAmount);
    }
    
    /**
     * Happens when waiting for other players to join.
     * 
     * @param p_token
     *            Message received from the server.
     * @see [WAIT_FOR_PLAYER;]
     */
    private void waitingForPlayers(StringTokenizer p_token)
    {
        notifyObserver(IPokerAgentListener.WAITING_FOR_PLAYERS);
    }
}

package clientGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pokerAI.IPokerAgent;
import pokerLogic.Card;
import pokerLogic.PokerPlayerAction;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import protocolGame.GameAskActionCommand;
import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBoardChangedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GameHoleCardsChangedCommand;
import protocolGame.GamePINGCommand;
import protocolGame.GamePlayerJoinedCommand;
import protocolGame.GamePlayerLeftCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GamePlayerTurnBeganCommand;
import protocolGame.GamePlayerTurnEndedCommand;
import protocolGame.GamePlayerWonPotCommand;
import protocolGame.GameSendActionCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableClosedCommand;
import protocolGame.GameTableInfoCommand;
import protocolGame.GameWaitingCommand;
import protocolGameTools.GameClientSideAdapter;
import protocolGameTools.GameClientSideObserver;
import protocolGameTools.SummarySeatInfo;
import protocolTools.IBluffinCommand;
import utilGUI.AutoListModel;
import utilGUI.ListEvent;
import utilGUI.ListListener;
import utility.IClosingListener;

/**
 * @author Hokus
 *         This class is the representation of a generic poker client.
 *         It first parse message received from the server before calling
 *         corresponding methods in IPokerAgentListener.
 */
public class PokerClient extends Thread implements ListListener<IPokerAgentListener>, IClosingListener<IPokerAgent>
{
    public static final int NB_CARDS_BOARDS = 5;
    public static final int NB_SEATS = 9;
    private final GameClientSideObserver m_commandObserver = new GameClientSideObserver();
    
    IPokerAgentActionner m_agent = null;
    AutoListModel<IPokerAgentListener> m_observers = null;
    
    /** Poker table **/
    ClientPokerTableInfo m_table;
    
    // Communication with the server
    Socket m_server = null;
    BufferedReader m_fromServer = null;
    PrintWriter m_toServer = null;
    
    /** Array containing listener to be notify when PokerClient is closing. **/
    List<IClosingListener<PokerClient>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<PokerClient>>());
    
    public PokerClient(IPokerAgentActionner p_agent, AutoListModel<IPokerAgentListener> p_observers, ClientPokerPlayerInfo p_localPlayer, Socket p_server, ClientPokerTableInfo p_table, BufferedReader p_fromServer)
    {
        m_table = p_table;
        m_agent = p_agent;
        m_observers = p_observers;
        m_observers.addListListener(this);
        
        m_table.m_localPlayer = p_localPlayer;
        m_table.m_dealer = null;
        m_table.m_smallBlind = null;
        m_table.m_bigBlind = null;
        m_table.addPlayer(p_localPlayer.m_noSeat, p_localPlayer);
        m_server = p_server;
        
        for (int i = 0; i != PokerClient.NB_CARDS_BOARDS; ++i)
        {
            m_table.m_boardCards.add(Card.getInstance().get(-1));
        }
        
        for (int i = 0; i != PokerClient.NB_SEATS; ++i)
        {
            m_table.m_pots.add(0);
        }
        
        try
        {
            m_fromServer = p_fromServer;
            m_toServer = new PrintWriter(m_server.getOutputStream(), true);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
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
    
    /**
     * Dicsonnect from the server (quit the table) and
     * stop all agents.
     */
    public void disconnect()
    {
        try
        {
            if (isConnected())
            {
                m_server.close();
                m_server = null;
                m_observers.clear();
                stopAgent(m_agent);
                
                synchronized (m_closingListeners)
                {
                    for (final IClosingListener<PokerClient> listener : m_closingListeners)
                    {
                        listener.closing(this);
                    }
                }
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public IPokerAgentActionner getAgent()
    {
        return m_agent;
    }
    
    public int getNoPort()
    {
        return m_server.getPort();
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
        return (m_server != null) && m_server.isConnected() && !m_server.isClosed();
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
    
    /*
     * *************************
     * UPDATE TABLE INFORMATIONS
     * *************************
     */

    /**
     * Notify all agents (IPokerAgentListener) by calling the right method.
     * 
     * @param p_method
     *            - Method to call on each listener.
     * @param p_objects
     *            - Arguments to use when calling.
     */
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
     * Add closing listener to PokerClient.
     * 
     * @param p_listener
     *            - Listener to be call when PokerClient will be closing.
     */
    public void addClosingListener(IClosingListener<PokerClient> p_listener)
    {
        m_closingListeners.add(p_listener);
    }
    
    /**
     * Add closing listener to PokerClient.
     * 
     * @param p_listener
     *            - Listener to remove.
     */
    public void removeClosingListener(IClosingListener<PokerClient> p_listener)
    {
        m_closingListeners.remove(p_listener);
    }
    
    /**
     * Reset boards to no cards. (ID = -1)
     */
    private void resetBoardCards()
    {
        for (int i = 0; i != PokerClient.NB_CARDS_BOARDS; ++i)
        {
            m_table.m_boardCards.set(i, Card.getInstance().get(-1));
        }
    }
    
    /**
     * Reset pots to 0.
     */
    private void resetPots()
    {
        for (int i = 0; i != m_table.m_pots.size(); ++i)
        {
            m_table.m_pots.set(i, 0);
        }
    }
    
    /**
     * Start the agent.
     * 
     * @param p_agent
     *            - Agent to start.
     */
    private void startAgent(IPokerAgent p_agent)
    {
        p_agent.setTable(m_table);
        p_agent.addClosingListener(this);
        p_agent.start();
    }
    
    /**
     * Stop the agent.
     * 
     * @param p_agent
     *            - Agent to stop.
     */
    private void stopAgent(IPokerAgent p_agent)
    {
        p_agent.stop();
    }
    
    /**
     * Start agents and update their table.
     * 
     * @param p_listeners
     *            - Agents to process.
     */
    private void startListeners(List<IPokerAgentListener> p_listeners)
    {
        for (final IPokerAgentListener listener : p_listeners)
        {
            startAgent(listener);
            listener.tableInfos();
        }
    }
    
    /**
     * Stop the agents.
     * 
     * @param p_listeners
     *            - Agents to stop.
     */
    private void stopListeners(List<IPokerAgentListener> p_listeners)
    {
        for (final IPokerAgentListener listener : p_listeners)
        {
            stopAgent(listener);
        }
    }
    
    @Override
    public void run()
    {
        this.setName(m_agent.getClass().getSimpleName() + " - " + m_table.m_localPlayer.m_name);
        
        // Start all agents
        startListeners(m_observers);
        
        if (!(m_agent instanceof IPokerAgentListener))
        {
            startAgent(m_agent);
        }
        
        // Beginning to listen to the server.
        startListening();
        
        // While is connected to the poker table (server)
        // ask the agent (actionner) to take an action.
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
    
    /**
     * Send action to the table (server)
     * 
     * @param p_action
     *            - Action to send to the table.
     */
    
    protected void send(PokerPlayerAction p_action)
    {
        send(new GameSendActionCommand(p_action));
    }
    
    /**
     * Send message to the table (server)
     * 
     * @param p_msg
     *            - Message to send to the table.
     */
    protected void sendMessage(String p_msg)
    {
        System.out.println(m_table.m_localPlayer.m_name + " SEND [" + p_msg + "]");
        m_toServer.println(p_msg);
    }
    
    /**
     * Send message to the table (server)
     * 
     * @param p_msg
     *            - Message to send to the table.
     */
    protected void send(IBluffinCommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    protected String receive() throws IOException
    {
        final String line = m_fromServer.readLine();
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    /**
     * Start a new thread to listen to incoming messages from server.
     */
    public void startListening()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                initializeCommandObserver();
                try
                {
                    while (isConnected())
                    {
                        receive();
                    }
                }
                catch (final IOException e)
                {/* e.printStackTrace(); */
                }
                
                disconnect();
            }
        }.start();
    }
    
    private void initializeCommandObserver()
    {
        final GameClientSideAdapter adapter = new GameClientSideAdapter()
        {
            
            @Override
            public void askActionCommandReceived(GameAskActionCommand command)
            {
                final boolean checkAllowed = command.canCheck();
                final boolean foldAllowed = command.canFold();
                final boolean callAllowed = command.canCall();
                final int callAmount = command.getCallAmnt();
                final boolean raiseAllowed = command.canRaise();
                final int minRaiseAmount = command.getRaiseMin();
                final int maxRaiseAmount = command.getRaiseMax();
                
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
            
            @Override
            public void betTurnEndedCommandReceived(GameBetTurnEndedCommand command)
            {
                final ArrayList<Integer> indices = new ArrayList<Integer>();
                for (int i = 0; i < command.getPotsAmounts().size(); ++i)
                {
                    final int potAmount = command.getPotsAmounts().get(i);
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
                
                final TypePokerRound gameState = command.getRound();
                m_table.m_gameState = gameState;
                
                m_table.m_currentBet = 0;
                
                notifyObserver(IPokerAgentListener.BET_TURN_ENDED, indices, gameState);
            }
            
            @Override
            public void boardChangedCommandReceived(GameBoardChangedCommand command)
            {
                final ArrayList<Integer> indices = new ArrayList<Integer>();
                for (int i = 0; i != m_table.m_boardCards.size(); ++i)
                {
                    final Card card = Card.getInstance().get(command.getCardsId().get(i));
                    if (card != m_table.m_boardCards.get(i))
                    {
                        m_table.m_boardCards.set(i, card);
                        indices.add(i);
                    }
                }
                
                notifyObserver(IPokerAgentListener.BOARD_CHANGED, indices);
            }
            
            @Override
            public void commandReceived(String command)
            {
                System.out.println(m_table.m_localPlayer.m_name + " RECV [" + command + "]");
            }
            
            @Override
            public void gameEndedCommandReceived(GameEndedCommand command)
            {
                m_table.m_gameState = TypePokerRound.END;
                notifyObserver(IPokerAgentListener.GAME_ENDED);
            }
            
            @Override
            public void gameStartedCommandReceived(GameStartedCommand command)
            {
                m_table.m_noSeatDealer = command.GetNoSeatD();
                m_table.m_noSeatSmallBlind = command.GetNoSeatSB();
                m_table.m_noSeatBigBlind = command.GetNoSeatBB();
                
                final PokerPlayerInfo oldDealer = m_table.m_dealer;
                final PokerPlayerInfo oldSmallBlind = m_table.m_smallBlind;
                final PokerPlayerInfo oldBigBlind = m_table.m_bigBlind;
                
                // Set dealer, small blind and big blind.
                final PokerPlayerInfo dealer = m_table.getPlayer(m_table.m_noSeatDealer);
                final PokerPlayerInfo smallBlind = m_table.getPlayer(m_table.m_noSeatSmallBlind);
                final PokerPlayerInfo bigBlind = m_table.getPlayer(m_table.m_noSeatBigBlind);
                
                m_table.m_dealer = dealer;
                m_table.m_dealer.m_isDealer = true;
                m_table.m_smallBlind = smallBlind;
                m_table.m_smallBlind.m_isSmallBlind = true;
                m_table.m_bigBlind = bigBlind;
                m_table.m_bigBlind.m_isBigBlind = true;
                
                // Remove buttons from old dealer, old small blind and old big blind.
                if (oldDealer != null)
                {
                    oldDealer.m_isDealer = false;
                }
                
                if (oldDealer != null)
                {
                    oldSmallBlind.m_isSmallBlind = false;
                }
                
                if (oldDealer != null)
                {
                    oldBigBlind.m_isBigBlind = false;
                }
                
                m_table.m_totalPotAmount = 0;
                resetPots();
                resetBoardCards();
                
                m_table.setPlayerPositions();
                m_table.m_gameState = TypePokerRound.PREFLOP;
                m_table.m_currentBet = 0;
                
                for (final PokerPlayerInfo player : m_table.getPlayers())
                {
                    player.m_initialMoney = player.m_money;
                }
                
                notifyObserver(IPokerAgentListener.GAME_STARTED, oldDealer, oldSmallBlind, oldBigBlind);
            }
            
            @Override
            public void holeCardsChangedCommandReceived(GameHoleCardsChangedCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final Card card1 = Card.getInstance().get(command.getCardsId().get(0));
                final Card card2 = Card.getInstance().get(command.getCardsId().get(1));
                
                final PokerPlayerInfo player = m_table.getPlayer(noSeat);
                player.setHand(card1, card2);
                
                notifyObserver(IPokerAgentListener.PLAYER_CARD_CHANGED, player);
            }
            
            @Override
            public void pingCommandReceived(GamePINGCommand command)
            {
                sendMessage(command.encodeResponse());
            }
            
            @Override
            public void playerJoinedCommandReceived(GamePlayerJoinedCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final String playerName = command.getPlayerName();
                final int moneyAmount = command.getPlayerMoney();
                
                final ClientPokerPlayerInfo player = new ClientPokerPlayerInfo(noSeat, playerName, moneyAmount);
                m_table.addPlayer(noSeat, player);
                
                notifyObserver(IPokerAgentListener.PLAYER_JOINED, player);
            }
            
            @Override
            public void playerLeftCommandReceived(GamePlayerLeftCommand command)
            {
                final int noSeat = command.getPlayerPos();
                
                final PokerPlayerInfo player = m_table.getPlayer(noSeat);
                m_table.removePlayer(noSeat);
                
                notifyObserver(IPokerAgentListener.PLAYER_LEFT, player);
            }
            
            @Override
            public void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final int moneyAmount = command.getPlayerMoney();
                
                final PokerPlayerInfo player = m_table.getPlayer(noSeat);
                final int oldMoneyAmount = player.m_money;
                player.m_money = moneyAmount;
                
                notifyObserver(IPokerAgentListener.PLAYER_MONEY_CHANGED, player, oldMoneyAmount);
            }
            
            @Override
            public void playerTurnBeganCommandReceived(GamePlayerTurnBeganCommand command)
            {
                final int noSeat = command.getPlayerPos();
                
                final PokerPlayerInfo oldCurrentPlayer = m_table.m_currentPlayer;
                m_table.m_currentPlayer = m_table.getPlayer(noSeat);
                
                notifyObserver(IPokerAgentListener.PLAYER_TURN_BEGAN, oldCurrentPlayer);
            }
            
            @Override
            public void playerTurnEndedCommandReceived(GamePlayerTurnEndedCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final int betAmount = command.getPlayerBet();
                final int moneyAmount = command.getPlayerMoney();
                final int totalPotAmount = command.getTotalPot();
                final TypePlayerAction action = command.getActionType();
                final int actionAmount = command.getActionAmount();
                
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
            
            @Override
            public void playerWonPotCommandReceived(GamePlayerWonPotCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final int idPot = command.getPotID();
                final int potAmountWon = command.getShared();
                final int moneyAmount = command.getPlayerMoney();
                
                final PokerPlayerInfo player = m_table.getPlayer(noSeat);
                player.m_money = moneyAmount;
                
                notifyObserver(IPokerAgentListener.POT_WON, player, potAmountWon, idPot);
            }
            
            @Override
            public void tableClosedCommandReceived(GameTableClosedCommand command)
            {
                notifyObserver(IPokerAgentListener.TABLE_CLOSED);
                disconnect();
            }
            
            @Override
            public void tableInfoCommandReceived(GameTableInfoCommand command)
            {
                m_table.m_totalPotAmount = command.getTotalPotAmount();
                
                m_table.m_pots = command.getPotsAmount();
                
                for (int i = 0; i != m_table.m_boardCards.size(); ++i)
                {
                    m_table.m_boardCards.set(i, Card.getInstance().get(command.getBoardCardIDs().get(i)));
                }
                
                final int nb = command.getNbPlayers();
                
                m_table.clearPlayers();
                m_table.addPlayer(m_table.m_localPlayer.m_noSeat, m_table.m_localPlayer);
                
                for (int i = 0; i < nb; ++i)
                {
                    final SummarySeatInfo seat = command.getSeats().get(i);
                    final int noSeat = seat.m_noSeat;
                    if (seat.m_isEmpty)
                    {
                        continue;
                    }
                    
                    if (m_table.getPlayer(noSeat) == null)
                    {
                        m_table.addPlayer(noSeat, new ClientPokerPlayerInfo(noSeat));
                    }
                    
                    final PokerPlayerInfo player = m_table.getPlayer(noSeat);
                    
                    player.m_name = seat.m_playerName;
                    player.m_money = seat.m_money;
                    final Card card1 = Card.getInstance().get(seat.m_holeCardIDs.get(0));
                    final Card card2 = Card.getInstance().get(seat.m_holeCardIDs.get(0));
                    player.setHand(card1, card2);
                    player.m_isDealer = seat.m_isDealer;
                    player.m_isSmallBlind = seat.m_isBigBlind;
                    player.m_isBigBlind = seat.m_isSmallBlind;
                    
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
                    
                    final boolean isPlaying = seat.m_isCurrentPlayer;
                    if (isPlaying)
                    {
                        m_table.m_currentPlayer = player;
                    }
                    
                    player.m_timeRemaining = seat.m_timeRemaining;
                    player.m_betAmount = seat.m_bet;
                }
                
                notifyObserver(IPokerAgentListener.TABLE_INFOS);
            }
            
            @Override
            public void waitingCommandReceived(GameWaitingCommand command)
            {
                notifyObserver(IPokerAgentListener.WAITING_FOR_PLAYERS);
            }
            
        };
        m_commandObserver.subscribe(adapter);
    }
}

package clientGame;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pokerLogic.PokerPlayerAction;
import pokerLogic.OldPokerPlayerInfo;
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
import protocolTools.IPokerCommand;
import utility.IClosingListener;
import clientGameTools.ClientPokerObserver;
import clientGameTools.IClientPoker;
import clientGameTools.IClientPokerActionner;

/**
 * @author Hokus
 *         This class is the representation of a generic poker client.
 *         It first parse message received from the server before calling
 *         corresponding methods in IPokerAgentListener.
 */
public class PokerClient extends Thread implements IClosingListener<IClientPoker>
{
    public static final int NB_CARDS_BOARDS = 5;
    public static final int NB_SEATS = 9;
    private final GameClientSideObserver m_commandObserver = new GameClientSideObserver();
    private final ClientPokerObserver m_pokerObserver = new ClientPokerObserver();
    
    IClientPokerActionner m_agent = null;
    
    /** Poker table **/
    ClientPokerTableInfo m_table;
    
    // Communication with the server
    Socket m_server = null;
    BufferedReader m_fromServer = null;
    PrintWriter m_toServer = null;
    
    /** Array containing listener to be notify when PokerClient is closing. **/
    List<IClosingListener<PokerClient>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<PokerClient>>());
    
    public PokerClient(ClientPokerPlayerInfo p_localPlayer, Socket p_server, ClientPokerTableInfo p_table, BufferedReader p_fromServer)
    {
        m_table = p_table;
        
        m_table.m_localPlayer = p_localPlayer;
        m_table.m_dealer = null;
        m_table.m_smallBlind = null;
        m_table.m_bigBlind = null;
        m_table.addPlayer(p_localPlayer.getNoSeat(), p_localPlayer);
        m_server = p_server;
        
        for (int i = 0; i != PokerClient.NB_CARDS_BOARDS; ++i)
        {
            m_table.m_boardCards.add(GameCard.NO_CARD);
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
    
    public void setActionner(IClientPokerActionner actionner)
    {
        m_agent = actionner;
        attach(actionner);
    }
    
    public void attach(IClientPoker client)
    {
        client.setTable(m_table);
        client.start();
    }
    
    @Override
    public void closing(IClientPoker e)
    {
        if (e == m_agent)
        {
            disconnect();
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
                // stopAgent(m_agent);
                
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
    
    public IClientPokerActionner getAgent()
    {
        return m_agent;
    }
    
    public int getNoPort()
    {
        return m_server.getPort();
    }
    
    public ClientPokerTableInfo getTable()
    {
        return m_table;
    }
    
    public boolean isConnected()
    {
        return (m_server != null) && m_server.isConnected() && !m_server.isClosed();
    }
    
    /*
     * *************************
     * UPDATE TABLE INFORMATIONS
     * *************************
     */

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
            m_table.m_boardCards.set(i, GameCard.NO_CARD);
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
    
    @Override
    public void run()
    {
        this.setName(m_agent.getClass().getSimpleName() + " - " + m_table.m_localPlayer.m_name);
        
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
    protected void send(IPokerCommand p_msg)
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
                
                for (final OldPokerPlayerInfo player : m_table.getPlayers())
                {
                    player.m_betAmount = 0;
                }
                
                final TypePokerRound gameState = command.getRound();
                m_table.m_gameState = gameState;
                
                m_table.m_currentBet = 0;
                m_pokerObserver.betTurnEnded(indices, gameState);
            }
            
            @Override
            public void boardChangedCommandReceived(GameBoardChangedCommand command)
            {
                final ArrayList<Integer> indices = new ArrayList<Integer>();
                for (int i = 0; i != m_table.m_boardCards.size(); ++i)
                {
                    final GameCard card = GameCard.getInstance(command.getCardsId().get(i));
                    if (card != m_table.m_boardCards.get(i))
                    {
                        m_table.m_boardCards.set(i, card);
                        indices.add(i);
                    }
                }
                m_pokerObserver.boardChanged(indices);
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
                m_pokerObserver.gameEnded();
            }
            
            @Override
            public void gameStartedCommandReceived(GameStartedCommand command)
            {
                m_table.m_noSeatDealer = command.GetNoSeatD();
                m_table.m_noSeatSmallBlind = command.GetNoSeatSB();
                m_table.m_noSeatBigBlind = command.GetNoSeatBB();
                
                final OldPokerPlayerInfo oldDealer = m_table.m_dealer;
                final OldPokerPlayerInfo oldSmallBlind = m_table.m_smallBlind;
                final OldPokerPlayerInfo oldBigBlind = m_table.m_bigBlind;
                
                // Set dealer, small blind and big blind.
                final OldPokerPlayerInfo dealer = m_table.getPlayer(m_table.m_noSeatDealer);
                final OldPokerPlayerInfo smallBlind = m_table.getPlayer(m_table.m_noSeatSmallBlind);
                final OldPokerPlayerInfo bigBlind = m_table.getPlayer(m_table.m_noSeatBigBlind);
                
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
                
                for (final OldPokerPlayerInfo player : m_table.getPlayers())
                {
                    player.m_initialMoney = player.getMoney();
                }
                m_pokerObserver.gameStarted(oldDealer, oldSmallBlind, oldBigBlind);
            }
            
            @Override
            public void holeCardsChangedCommandReceived(GameHoleCardsChangedCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final GameCard card1 = GameCard.getInstance(command.getCardsId().get(0));
                final GameCard card2 = GameCard.getInstance(command.getCardsId().get(1));
                
                final OldPokerPlayerInfo player = m_table.getPlayer(noSeat);
                player.setHand(card1, card2);
                m_pokerObserver.playerCardChanged(player);
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
                m_pokerObserver.playerJoined(player);
            }
            
            @Override
            public void playerLeftCommandReceived(GamePlayerLeftCommand command)
            {
                final int noSeat = command.getPlayerPos();
                
                final OldPokerPlayerInfo player = m_table.getPlayer(noSeat);
                m_table.removePlayer(noSeat);
                m_pokerObserver.playerLeft(player);
            }
            
            @Override
            public void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final int moneyAmount = command.getPlayerMoney();
                
                final OldPokerPlayerInfo player = m_table.getPlayer(noSeat);
                final int oldMoneyAmount = player.getMoney();
                player.setMoney(moneyAmount);
                m_pokerObserver.playerMoneyChanged(player, oldMoneyAmount);
            }
            
            @Override
            public void playerTurnBeganCommandReceived(GamePlayerTurnBeganCommand command)
            {
                final int noSeat = command.getPlayerPos();
                
                final OldPokerPlayerInfo oldCurrentPlayer = m_table.m_currentPlayer;
                m_table.m_currentPlayer = m_table.getPlayer(noSeat);
                m_pokerObserver.playerTurnBegan(oldCurrentPlayer);
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
                player.setMoney(moneyAmount);
                m_table.m_totalPotAmount = totalPotAmount;
                m_table.m_currentBet = Math.max(m_table.m_currentBet, betAmount);
                
                if (action.equals(TypePlayerAction.FOLD))
                {
                    m_table.m_nbRemainingPlayers--;
                }
                
                player.did(action, m_table.m_gameState);
                m_pokerObserver.playerTurnEnded(player, action, actionAmount);
            }
            
            @Override
            public void playerWonPotCommandReceived(GamePlayerWonPotCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final int idPot = command.getPotID();
                final int potAmountWon = command.getShared();
                final int moneyAmount = command.getPlayerMoney();
                
                final OldPokerPlayerInfo player = m_table.getPlayer(noSeat);
                player.setMoney(moneyAmount);
                m_pokerObserver.potWon(player, potAmountWon, idPot);
            }
            
            @Override
            public void tableClosedCommandReceived(GameTableClosedCommand command)
            {
                m_pokerObserver.tableClosed();
                disconnect();
            }
            
            @Override
            public void tableInfoCommandReceived(GameTableInfoCommand command)
            {
                m_table.m_totalPotAmount = command.getTotalPotAmount();
                
                m_table.m_pots = command.getPotsAmount();
                
                for (int i = 0; i != m_table.m_boardCards.size(); ++i)
                {
                    m_table.m_boardCards.set(i, GameCard.getInstance(command.getBoardCardIDs().get(i)));
                }
                
                final int nb = command.getNbPlayers();
                
                m_table.clearPlayers();
                m_table.addPlayer(m_table.m_localPlayer.getNoSeat(), m_table.m_localPlayer);
                
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
                    
                    final OldPokerPlayerInfo player = m_table.getPlayer(noSeat);
                    
                    player.m_name = seat.m_playerName;
                    player.setMoney(seat.m_money);
                    final GameCard card1 = GameCard.getInstance(seat.m_holeCardIDs.get(0));
                    final GameCard card2 = GameCard.getInstance(seat.m_holeCardIDs.get(0));
                    player.setHand(card1, card2);
                    player.m_isDealer = seat.m_isDealer;
                    player.m_isSmallBlind = seat.m_isBigBlind;
                    player.m_isBigBlind = seat.m_isSmallBlind;
                    
                    if (player.m_isDealer)
                    {
                        m_table.m_dealer = player;
                        m_table.m_noSeatDealer = player.getNoSeat();
                    }
                    
                    if (player.m_isSmallBlind)
                    {
                        m_table.m_smallBlind = player;
                        m_table.m_noSeatSmallBlind = player.getNoSeat();
                    }
                    
                    if (player.m_isBigBlind)
                    {
                        m_table.m_bigBlind = player;
                        m_table.m_noSeatBigBlind = player.getNoSeat();
                    }
                    
                    final boolean isPlaying = seat.m_isCurrentPlayer;
                    if (isPlaying)
                    {
                        m_table.m_currentPlayer = player;
                    }
                    
                    player.m_timeRemaining = seat.m_timeRemaining;
                    player.m_betAmount = seat.m_bet;
                }
                m_pokerObserver.tableInfos();
            }
            
            @Override
            public void waitingCommandReceived(GameWaitingCommand command)
            {
                m_pokerObserver.waitingForPlayers();
            }
            
        };
        m_commandObserver.subscribe(adapter);
    }
    
    public ClientPokerObserver getPokerObserver()
    {
        return m_pokerObserver;
    }
}

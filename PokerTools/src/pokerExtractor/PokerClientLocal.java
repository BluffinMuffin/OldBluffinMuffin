package pokerExtractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import pokerLogic.Card;
import pokerLogic.PokerPlayerAction;
import pokerLogic.PokerPlayerInfo;
import pokerLogic.TypePlayerAction;
import pokerLogic.TypePokerRound;
import protocolGame.GameBetTurnEndedCommand;
import protocolGame.GameBoardChangedCommand;
import protocolGame.GameEndedCommand;
import protocolGame.GamePlayerMoneyChangedCommand;
import protocolGame.GameSendActionCommand;
import protocolGame.GameStartedCommand;
import protocolGame.GameTableInfoCommand;
import protocolGameTools.GameClientSideAdapter;
import protocolGameTools.GameClientSideObserver;
import protocolGameTools.SummarySeatInfo;
import protocolTools.IBluffinCommand;
import utility.IClosingListener;
import clientGame.ClientPokerPlayerInfo;
import clientGame.ClientPokerTableInfo;
import clientGameTools.ClientPokerObserver;
import clientGameTools.IClientPoker;
import clientGameTools.IClientPokerActionner;

/**
 * This class is the representation of a generic poker client.
 * 
 * @author Hokus
 */
public class PokerClientLocal extends Thread implements IClosingListener<IClientPoker>
{
    public static final int NB_CARDS_BOARDS = 5;
    public static final int NB_SEATS = 9;
    private final GameClientSideObserver m_commandObserver = new GameClientSideObserver();
    private final ClientPokerObserver m_pokerObserver = new ClientPokerObserver();
    
    IClientPokerActionner m_agent = null;
    
    ClientPokerTableInfo m_table;
    
    LinkedBlockingQueue<String> m_fromServer = null;
    LinkedBlockingQueue<String> m_toServer = null;
    List<IClosingListener<PokerClientLocal>> m_closingListeners = Collections.synchronizedList(new ArrayList<IClosingListener<PokerClientLocal>>());
    
    public PokerClientLocal(IClientPokerActionner p_agent, ClientPokerPlayerInfo p_localPlayer, ClientPokerTableInfo p_table, LinkedBlockingQueue<String> p_fromServer, LinkedBlockingQueue<String> p_toServer)
    {
        m_table = p_table;
        m_agent = p_agent;
        
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
    
    @Override
    public void closing(IClientPoker e)
    {
        if (e == m_agent)
        {
            disconnect();
        }
    }
    
    public void disconnect()
    {
        if (isConnected())
        {
            m_toServer = null;
            m_fromServer = null;
            
            synchronized (m_closingListeners)
            {
                for (final IClosingListener<PokerClientLocal> listener : m_closingListeners)
                {
                    listener.closing(this);
                }
            }
        }
    }
    
    public IClientPokerActionner getAgent()
    {
        return m_agent;
    }
    
    public ClientPokerTableInfo getTable()
    {
        return m_table;
    }
    
    public boolean isConnected()
    {
        return (m_toServer != null) && (m_fromServer != null);
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
            startListening();
        }
        else
        {
            this.setName(m_agent.getClass().getSimpleName() + " - " + m_table.m_localPlayer.m_name);
            
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
                catch (final InterruptedException e)
                {
                }
                
                disconnect();
            }
        }.start();
    }
    
    protected void send(PokerPlayerAction p_action)
    {
        send(new GameSendActionCommand(p_action));
    }
    
    protected void sendMessage(String p_msg)
    {
        System.out.println(m_table.m_localPlayer.m_name + " SEND [" + p_msg + "]");
        m_toServer.add(p_msg);
    }
    
    protected void send(IBluffinCommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    protected String receive() throws InterruptedException
    {
        final String line = m_fromServer.take();
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    private void initializeCommandObserver()
    {
        final GameClientSideAdapter adapter = new GameClientSideAdapter()
        {
            
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
                m_pokerObserver.betTurnEnded(indices, gameState);
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
                m_pokerObserver.gameStarted(null, null, null);
            }
            
            @Override
            public void playerMoneyChangedCommandReceived(GamePlayerMoneyChangedCommand command)
            {
                final int noSeat = command.getPlayerPos();
                final int moneyAmount = command.getPlayerMoney();
                
                final PokerPlayerInfo player = m_table.getPlayer(noSeat);
                final int oldMoneyAmount = player.m_money;
                player.m_money = moneyAmount;
                m_pokerObserver.playerMoneyChanged(player, oldMoneyAmount);
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
                // m_table.addPlayer(m_table.m_localPlayer.m_noSeat, m_table.m_localPlayer);
                
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
                m_pokerObserver.tableInfos();
            }
            
        };
        m_commandObserver.subscribe(adapter);
    }
    
    public ClientPokerObserver getPokerObserver()
    {
        return m_pokerObserver;
    }
}

package tempServerGame;

import gameLogic.GameCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

import pokerLogic.PokerPlayerAction;
import pokerLogic.Pot;
import pokerLogic.TypePlayerAction;
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
import protocolGame.GameWaitingCommand;
import protocolGameTools.GameServerSideAdapter;
import protocolGameTools.GameServerSideObserver;
import protocolTools.IPokerCommand;
import tempServerGameTools.TempServerPokerAdapter;
import tempServerGameTools.TempServerPokerObserver;
import utility.Constants;

/**
 * @author HOCUS
 *         This player is a network player that work over the TCP/IP protocol.
 *         A new message is sent to the client when the table change.
 *         This class implements IHoldEmObserver in order to be notify when the table change.
 *         When an action is required, we send a TAKE_ACTION message to the client
 *         and wait for his answer. *
 *         When a connection is lost, this player will automatically sit out at the next hand
 *         and will play Check or Fold actions.
 */
public class TempServerNetworkPokerPlayerInfo extends TempServerPokerPlayerInfo
{
    private final GameServerSideObserver m_commandObserver = new GameServerSideObserver();
    private TempServerPokerObserver m_pokerObserver;
    
    public static final int PING_INTERVAL = 10000;
    
    // TCP socket
    private Socket m_socket;
    
    // Communication channel to the client
    private final PrintWriter m_output;
    
    // Communication channel from the client
    private final BufferedReader m_input;
    
    // if the player is connected to a client
    private boolean m_isConnected;
    
    private long m_lastPing;
    
    /**
     * Create a new player
     * 
     * @param p_name
     *            The name of the player
     * @param p_money
     *            The starting chips of the player
     * @param p_socket
     *            The TCP socket with the client
     * @throws IOException
     *             Error with the TCP connection
     */
    public TempServerNetworkPokerPlayerInfo(String p_name, int p_money, Socket p_socket) throws IOException
    {
        super(p_name, p_money);
        m_socket = p_socket;
        m_output = new PrintWriter(m_socket.getOutputStream(), true);
        m_input = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
        m_isConnected = false;
        initializeCommandObserver();
    }
    
    public void setPokerObserver(TempServerPokerObserver observer)
    {
        m_pokerObserver = observer;
        initializePokerObserver();
    }
    
    protected String receive() throws IOException
    {
        final String line = m_input.readLine();
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    @Override
    public boolean canStartGame()
    {
        return m_isConnected && super.canStartGame();
    }
    
    // ---------------------------------------------
    // Player
    // ---------------------------------------------
    @Override
    protected PokerPlayerAction getActionFromUser(boolean p_canCheck, boolean p_canFold, boolean p_canCall, int p_callOf, boolean p_canRaise, int p_minimumRaise, int p_maximumRaise)
    {
        PokerPlayerAction action = null;
        int nbTry = 0;
        if (m_isConnected)
        {
            try
            {
                while ((action == null) && m_isConnected && (nbTry < 10))
                {
                    ++nbTry;
                    send(new GameAskActionCommand(p_canCheck, p_canFold, p_canCall, p_callOf, p_canRaise, p_minimumRaise, p_maximumRaise));
                    
                    final String line = receive();
                    if (line != null)
                    {
                        final StringTokenizer message = new StringTokenizer(line, Constants.DELIMITER);
                        String token;
                        if (message.hasMoreTokens())
                        {
                            token = message.nextToken();
                            if (token.equals(GameSendActionCommand.COMMAND_NAME))
                            {
                                final GameSendActionCommand command = new GameSendActionCommand(message);
                                final TypePlayerAction actionType = command.getAction().getType();
                                switch (actionType)
                                {
                                    case DISCONNECT:
                                        m_isConnected = false;
                                        m_sitOutNextHand = true;
                                        break;
                                    case CHECK:
                                        if (p_canCheck)
                                        {
                                            action = new PokerPlayerAction(actionType, 0);
                                        }
                                        break;
                                    case CALL:
                                        if (p_canCall)
                                        {
                                            action = new PokerPlayerAction(actionType, p_callOf);
                                        }
                                        break;
                                    case FOLD:
                                        if (p_canFold)
                                        {
                                            action = new PokerPlayerAction(actionType, 0);
                                        }
                                        break;
                                    case RAISE:
                                        try
                                        {
                                            final int amount = command.getAction().getAmount();
                                            if (p_canRaise && (amount >= p_minimumRaise) && (amount <= p_maximumRaise))
                                            {
                                                action = new PokerPlayerAction(actionType, amount);
                                            }
                                        }
                                        catch (final NumberFormatException e)
                                        {
                                            action = null;
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    else
                    {
                        m_isConnected = false;
                        m_sitOutNextHand = true;
                    }
                }
            }
            catch (final IOException e)
            {
                m_isConnected = false;
                m_sitOutNextHand = true;
            }
        }
        
        if (action == null)
        {
            if (p_canCheck)
            {
                action = new PokerPlayerAction(TypePlayerAction.CHECK, 0);
            }
            else
            {
                action = new PokerPlayerAction(TypePlayerAction.FOLD, 0);
            }
            
            if (nbTry >= 10)
            {
                System.out.println("Player kicked out :" + m_name);
                m_isConnected = false;
                m_sitOutNextHand = true;
            }
        }
        if (!m_isConnected && m_socket != null)
        {
            try
            {
                m_socket.close();
            }
            catch (final IOException e)
            {
            }
            m_socket = null;
        }
        
        return action;
    }
    
    /**
     * Send a message to the client
     * 
     * @param p_msg
     *            The message to send
     */
    protected void send(IPokerCommand command)
    {
        // System.out.println("Server SEND TO " + this.m_name + " [" + p_msg + "]");
        m_output.println(command.encodeCommand());
    }
    
    /**
     * Make the player connected
     */
    public void setIsConnected()
    {
        m_isConnected = true;
    }
    
    /**
     * To test if the player is connected, we send a ping to the client and wait his repsonse.
     * We do not send a new ping until the PING_INTERVAL milliseconds have past.
     */
    @Override
    public boolean sitOutNextHand()
    {
        if (m_isConnected && !m_sitOutNextHand && ((m_lastPing + TempServerNetworkPokerPlayerInfo.PING_INTERVAL) <= System.currentTimeMillis()))
        {
            m_lastPing = System.currentTimeMillis();
            try
            {
                send(new GamePINGCommand());
                
                final String line = m_input.readLine();
                if (line != null)
                {
                    final StringTokenizer message = new StringTokenizer(line, Constants.DELIMITER);
                    if (!message.hasMoreTokens())
                    {
                        m_isConnected = false;
                        m_sitOutNextHand = true;
                    }
                }
                else
                {
                    m_isConnected = false;
                    m_sitOutNextHand = true;
                }
            }
            catch (final IOException e)
            {
                m_isConnected = false;
                m_sitOutNextHand = true;
            }
        }
        
        return m_sitOutNextHand;
    }
    
    private void initializeCommandObserver()
    {
        m_commandObserver.subscribe(new GameServerSideAdapter()
        {
            @Override
            public void commandReceived(String command)
            {
                System.out.println("<" + m_table.getName() + "> RECV from " + getName() + " [" + command + "]");
            }
            
            @Override
            public void sendActionCommandReceived(GameSendActionCommand command)
            {
                if (command.getAction().getType() == TypePlayerAction.DISCONNECT)
                {
                    m_isConnected = false;
                    m_sitOutNextHand = true;
                }
            }
        });
        
    }
    
    private void initializePokerObserver()
    {
        m_pokerObserver.subscribe(new TempServerPokerAdapter()
        {
            
            @Override
            public void bigBlindPosted(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, int p_bigBlind)
            {
                // OK
                send(new GamePlayerTurnEndedCommand(p_player.getNoSeat(), p_player.getBet(), p_player.getMoney(), p_table.getTotalPot(), TypePlayerAction.BIG_BLIND, p_bigBlind));
            }
            
            @Override
            public void endBettingTurn(TempServerTableCommunicator p_table)
            {
                
                final Stack<Pot> pots = p_table.getPots();
                final ArrayList<Integer> amounts = new ArrayList<Integer>();
                for (final Pot pot : pots)
                {
                    amounts.add(pot.getAmount());
                }
                
                for (int i = pots.size(); i < p_table.getNbSeats(); i++)
                {
                    amounts.add(0);
                }
                
                send(new GameBetTurnEndedCommand(amounts, p_table.getGameState()));
            }
            
            @Override
            public void flopDealt(TempServerTableCommunicator p_table, GameCard[] p_board)
            {
                // OK
                send(new GameBoardChangedCommand(p_board[0].getId(), p_board[1].getId(), p_board[2].getId(), GameCard.NO_CARD_ID, GameCard.NO_CARD_ID));
            }
            
            @Override
            public void gameEnded(TempServerTableCommunicator p_table)
            {
                send(new GameEndedCommand());
            }
            
            @Override
            public void gameStarted(TempServerTableCommunicator p_table)
            {
                send(new GameStartedCommand(p_table.getNoSeatDealer(), p_table.getNoSeatSmallBlind(), p_table.getNoSeatBigBlind()));
            }
            
            @Override
            public void holeCardDeal(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                if (p_player.getNoSeat() != getNoSeat())
                {
                    send(new GameHoleCardsChangedCommand(p_player.getNoSeat(), GameCard.HIDDEN_CARD_ID, GameCard.HIDDEN_CARD_ID));
                }
                else
                {
                    send(new GameHoleCardsChangedCommand(p_player.getNoSeat(), p_player.getHand()[0].getId(), p_player.getHand()[1].getId()));
                }
                
            }
            
            @Override
            public void playerEndTurn(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, PokerPlayerAction p_action)
            {
                send(new GamePlayerTurnEndedCommand(p_player.getNoSeat(), p_player.getBet(), p_player.getMoney(), p_table.getTotalPot(), p_action.getType(), p_action.getAmount()));
            }
            
            @Override
            public void playerJoinedTable(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                // OK
                send(new GamePlayerJoinedCommand(p_player.getNoSeat(), p_player.getName(), p_player.getMoney()));
            }
            
            @Override
            public void playerLeftTable(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                // OK
                send(new GamePlayerLeftCommand(p_player.getNoSeat()));
            }
            
            @Override
            public void playerMoneyChanged(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                send(new GamePlayerMoneyChangedCommand(p_player.getNoSeat(), p_player.getMoney()));
            }
            
            @Override
            public void playerShowCard(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                send(new GameHoleCardsChangedCommand(p_player.getNoSeat(), p_player.getHand()[0].getId(), p_player.getHand()[1].getId()));
            }
            
            @Override
            public void playerTurnStarted(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player)
            {
                send(new GamePlayerTurnBeganCommand(p_player.getNoSeat()));
            }
            
            @Override
            public void potWon(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, Pot p_pot, int p_share)
            {
                send(new GamePlayerWonPotCommand(p_player.getNoSeat(), p_pot.getId(), p_share, p_player.getMoney()));
            }
            
            @Override
            public void riverDeal(TempServerTableCommunicator p_table, GameCard[] p_board)
            {
                // OK
                send(new GameBoardChangedCommand(p_board[0].getId(), p_board[1].getId(), p_board[2].getId(), p_board[3].getId(), p_board[4].getId()));
            }
            
            @Override
            public void smallBlindPosted(TempServerTableCommunicator p_table, TempServerPokerPlayerInfo p_player, int p_smallBlind)
            {
                // OK
                send(new GamePlayerTurnEndedCommand(p_player.getNoSeat(), p_player.getBet(), p_player.getMoney(), p_table.getTotalPot(), TypePlayerAction.SMALL_BLIND, p_smallBlind));
            }
            
            @Override
            public void tableEnded(TempServerTableCommunicator p_table)
            {
                // OK
                send(new GameTableClosedCommand());
            }
            
            @Override
            public void tableInfos(TempServerTableCommunicator p_table)
            {
                send(m_table.buildCommand(TempServerNetworkPokerPlayerInfo.this));
            }
            
            @Override
            public void turnDeal(TempServerTableCommunicator p_table, GameCard[] p_board)
            {
                // OK
                send(new GameBoardChangedCommand(p_board[0].getId(), p_board[1].getId(), p_board[2].getId(), p_board[3].getId(), GameCard.NO_CARD_ID));
            }
            
            @Override
            public void waitingForPlayers(TempServerTableCommunicator p_table)
            {
                // NOT USED
                send(new GameWaitingCommand());
            }
        });
        
    }
}

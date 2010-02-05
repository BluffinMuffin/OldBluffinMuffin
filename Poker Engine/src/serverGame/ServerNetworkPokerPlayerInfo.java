package serverGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

import pokerLogic.Card;
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
public class ServerNetworkPokerPlayerInfo extends ServerPokerPlayerInfo implements IServerPokerObserver
{
    
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
    public ServerNetworkPokerPlayerInfo(String p_name, int p_money, Socket p_socket) throws IOException
    {
        super(p_name, p_money);
        m_socket = p_socket;
        m_output = new PrintWriter(m_socket.getOutputStream(), true);
        m_input = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
        m_isConnected = false;
    }
    
    @Override
    public void bigBlindPosted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, int p_bigBlind)
    {
        send(new GamePlayerTurnEndedCommand(p_player.getTablePosition(), p_player.getBet(), p_player.getMoney(), p_table.getTotalPot(), TypePlayerAction.BIG_BLIND, p_bigBlind).encodeCommand());
    }
    
    @Override
    public boolean canStartGame()
    {
        return m_isConnected && super.canStartGame();
    }
    
    @Override
    public void endBettingTurn(ServerTableCommunicator p_table)
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
        
        send(new GameBetTurnEndedCommand(amounts, p_table.getGameState()).encodeCommand());
    }
    
    @Override
    public void flopDealt(ServerTableCommunicator p_table, Card[] p_board)
    {
        
        send(new GameBoardChangedCommand(p_board[0].getId(), p_board[1].getId(), p_board[2].getId(), Card.NO_CARD, Card.NO_CARD).encodeCommand());
    }
    
    @Override
    public void gameEnded(ServerTableCommunicator p_table)
    {
        send(new GameEndedCommand().encodeCommand());
    }
    
    @Override
    public void gameStarted(ServerTableCommunicator p_table)
    {
        send(new GameStartedCommand(p_table.getNoSeatDealer(), p_table.getNoSeatSmallBlind(), p_table.getNoSeatBigBlind()).encodeCommand());
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
                    send(new GameAskActionCommand(p_canCheck, p_canFold, p_canCall, p_callOf, p_canRaise, p_minimumRaise, p_maximumRaise).encodeCommand());
                    
                    final String line = m_input.readLine();
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
                                            if (message.hasMoreTokens())
                                            {
                                                final int amount = command.getAction().getAmount();
                                                if (p_canRaise && (amount >= p_minimumRaise) && (amount <= p_maximumRaise))
                                                {
                                                    action = new PokerPlayerAction(actionType, amount);
                                                }
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
                try
                {
                    m_socket.close();
                }
                catch (final IOException e)
                {
                }
                m_socket = null;
            }
        }
        
        return action;
    }
    
    @Override
    public void holeCardDeal(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        if (p_player.getTablePosition() != getTablePosition())
        {
            send(new GameHoleCardsChangedCommand(p_player.getTablePosition(), Card.HIDDEN_CARD, Card.HIDDEN_CARD).encodeCommand());
        }
        else
        {
            send(new GameHoleCardsChangedCommand(p_player.getTablePosition(), p_player.getHand()[0].getId(), p_player.getHand()[1].getId()).encodeCommand());
        }
        
    }
    
    @Override
    public void playerEndTurn(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, PokerPlayerAction p_action)
    {
        send(new GamePlayerTurnEndedCommand(p_player.getTablePosition(), p_player.getBet(), p_player.getMoney(), p_table.getTotalPot(), p_action.getType(), p_action.getAmount()).encodeCommand());
    }
    
    @Override
    public void playerJoinedTable(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(new GamePlayerJoinedCommand(p_player.getTablePosition(), p_player.getName(), p_player.getMoney()).encodeCommand());
    }
    
    @Override
    public void playerLeftTable(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(new GamePlayerLeftCommand(p_player.getTablePosition()).encodeCommand());
    }
    
    @Override
    public void playerMoneyChanged(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(new GamePlayerMoneyChangedCommand(p_player.getTablePosition(), p_player.getMoney()).encodeCommand());
    }
    
    @Override
    public void playerShowCard(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(new GameHoleCardsChangedCommand(p_player.getTablePosition(), p_player.getHand()[0].getId(), p_player.getHand()[1].getId()).encodeCommand());
    }
    
    @Override
    public void playerTurnStarted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player)
    {
        send(new GamePlayerTurnBeganCommand(p_player.getTablePosition()).encodeCommand());
    }
    
    @Override
    public void potWon(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, Pot p_pot, int p_share)
    {
        send(new GamePlayerWonPotCommand(p_player.getTablePosition(), p_pot.getId(), p_share, p_player.getMoney()).encodeCommand());
    }
    
    @Override
    public void riverDeal(ServerTableCommunicator p_table, Card[] p_board)
    {
        
        send(new GameBoardChangedCommand(p_board[0].getId(), p_board[1].getId(), p_board[2].getId(), p_board[3].getId(), p_board[4].getId()).encodeCommand());
    }
    
    /**
     * Send a message to the client
     * 
     * @param p_msg
     *            The message to send
     */
    protected void send(String p_msg)
    {
        // System.out.println("Server SEND TO " + this.m_name + " [" + p_msg + "]");
        m_output.println(p_msg);
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
        if (m_isConnected && !m_sitOutNextHand && ((m_lastPing + ServerNetworkPokerPlayerInfo.PING_INTERVAL) <= System.currentTimeMillis()))
        {
            m_lastPing = System.currentTimeMillis();
            try
            {
                send(new GamePINGCommand().encodeCommand());
                
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
    
    @Override
    public void smallBlindPosted(ServerTableCommunicator p_table, ServerPokerPlayerInfo p_player, int p_smallBlind)
    {
        send(new GamePlayerTurnEndedCommand(p_player.getTablePosition(), p_player.getBet(), p_player.getMoney(), p_table.getTotalPot(), TypePlayerAction.SMALL_BLIND, p_smallBlind).encodeCommand());
    }
    
    @Override
    public void tableEnded(ServerTableCommunicator p_table)
    {
        send(new GameTableClosedCommand().encodeCommand());
    }
    
    @Override
    public void tableInfos(ServerTableCommunicator p_table)
    {
        send(m_table.buildCommand(this).encodeCommand());
    }
    
    // ---------------------------------------------
    // IHoldEmObserver
    // ---------------------------------------------
    @Override
    public void tableStarted(ServerTableCommunicator p_table)
    {
        // do nothing
    }
    
    @Override
    public void turnDeal(ServerTableCommunicator p_table, Card[] p_board)
    {
        send(new GameBoardChangedCommand(p_board[0].getId(), p_board[1].getId(), p_board[2].getId(), p_board[3].getId(), Card.NO_CARD).encodeCommand());
    }
    
    @Override
    public void waitingForPlayers(ServerTableCommunicator p_table)
    {
        send(new GameWaitingCommand().encodeCommand());
    }
    
}
